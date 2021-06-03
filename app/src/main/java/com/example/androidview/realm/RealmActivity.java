package com.example.androidview.realm;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidview.databinding.ActivityRealmBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.FieldAttribute;
import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmModel;
import io.realm.RealmObjectSchema;
import io.realm.RealmResults;
import io.realm.RealmSchema;
import io.realm.Sort;

/**
 * @author lgh on 2021/1/18 16:25
 * @description
 */
public class RealmActivity extends AppCompatActivity {

    ActivityRealmBinding mRealmBinding;

    Realm mRealm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRealmBinding = ActivityRealmBinding.inflate(getLayoutInflater());
        setContentView(mRealmBinding.getRoot());
        mRealm = Realm.getDefaultInstance();//默认实例化Realm数据库
        //配置realm数据库生成
        /**
         * RealmConfiguration支持的方法：
         *
         *         Builder.name : 指定数据库的名称。如不指定默认名为default。
         *         Builder.schemaVersion : 指定数据库的版本号。
         *         Builder.encryptionKey : 指定数据库的密钥。
         *         Builder.migration : 指定迁移操作的迁移类。
         *         Builder.deleteRealmIfMigrationNeeded : 声明版本冲突时自动删除原数据库。
         *         Builder.inMemory : 声明数据库只在内存中持久化。
         *         build : 完成配置构建。
         */

        byte[] key = new byte[64];
        new SecureRandom().nextBytes(key);

        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("my_realm.realm")//数据库名
                .encryptionKey(key)//加密
                .schemaVersion(42)//版本
                .modules(null)
                .migration(new CustomMigration())
                .build();
        mRealm = Realm.getInstance(configuration);
        Dog dog = new Dog();
        dog.setName("REx");
        dog.setAge(1);
        final RealmResults<Dog> dogs = mRealm.where(Dog.class).lessThan("age", 2).findAll();
        dogs.size();
        //插入数据
        mRealm.beginTransaction();//开启事务
        Dog copyToRealm = mRealm.copyToRealm(dog);
        Person person = mRealm.createObject(Person.class);
        person.getDogs().add(copyToRealm);
        mRealm.commitTransaction();//提交事务
        dogs.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<Dog>>() {
            @Override
            public void onChange(RealmResults<Dog> dogs, @javax.annotation.Nullable OrderedCollectionChangeSet changeSet) {
                changeSet.getInsertions();
            }
        });
        //该方法避免UI线程和后台线程同时write 导致ANR
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Dog first = realm.where(Dog.class).equalTo("age", 1).findFirst();
                first.setAge(3);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                dogs.size();
                copyToRealm.getAge();
            }
        });
        /**
         * 增  方法一：executeTransaction
         * 注意：如果在UI线程中插入过多的数据，可能会导致主线程拥塞。
         */
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Dog dog1 = realm.createObject(Dog.class);
                dog1.setAge(1);
                dog1.setName("Gavin");
            }
        });
        /**
         * 方法二
         * 使用copyToRealmOrUpdate或copyToRealm方法插入数据
         * 当Model中存在主键的时候，推荐使用copyToRealmOrUpdate方法插入数据。
         * 如果对象存在，就更新该对象；反之，它会创建一个新的对象。
         * 若该Model没有主键，使用copyToRealm方法，否则将抛出异常。
         *
         */
        final Dog dog2 = new Dog();
        dog2.setName("Jack");
        dog2.setAge(3);
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(dog2);
            }
        });

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Person person1 = realm.createObject(Person.class);
                person.getDogs().add(dog2);
            }
        });

        /**
         * Json
         */
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createObject(Person.class, "{name:\"zan san\",id:1");
            }
        });

        // 使用InputStream插入数据
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try {
                    InputStream is = new FileInputStream(new File("path_to_file"));
                    realm.createAllFromJson(Person.class, is);
                } catch (IOException e) {
                    throw new RuntimeException();
                }
            }
        });

        /**
         * 查
         * sum()：对指定字段求和。
         * average()：对指定字段求平均值。
         * min(): 对指定字段求最小值。
         * max() : 对指定字段求最大值。count : 求结果集的记录数量。
         * findAll(): 返回结果集所有字段，返回值为RealmResults队列
         * findAllSorted() : 排序返回结果集所有字段，返回值为RealmResults队列
         * between(), greaterThan(),lessThan(), greaterThanOrEqualTo() & lessThanOrEqualTo()
         * equalTo() & notEqualTo()
         * contains(), beginsWith() & endsWith()
         * isNull() & isNotNull()
         * isEmpty()& isNotEmpty()
         * .or()避免获取的为空
         */
        RealmResults<Person> all = mRealm.where(Person.class)
                .equalTo("mDogs.name", "")
                .or().equalTo("age", 21)
                .findAll();
        all.sort("age", Sort.DESCENDING);//逆序排序
        RealmResults<Person> allAsync = mRealm.where(Person.class)
                .equalTo("age", 23)
                .findAllAsync();

        if (allAsync.isLoaded()) {
            //完成查询
        }

        //不会立刻获取数据
        allAsync.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<Person>>() {
            @Override
            public void onChange(RealmResults<Person> people, @javax.annotation.Nullable OrderedCollectionChangeSet changeSet) {

            }
        });

        /**
         * 改
         */
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //先查找后得到User对象
                Person user = mRealm.where(Person.class).findFirst();
                user.setName("26");
            }
        });
        /**
         * 删
         * userList.deleteFirstFromRealm(); //删除user表的第一条数据
         * userList.deleteLastFromRealm();//删除user表的最后一条数据
         * results.deleteAllFromRealm();//删除user表的全部数据
         */
        //先查找到数据
        final RealmResults<Person> userList = mRealm.where(Person.class).findAll();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                userList.get(0).deleteFromRealm();
                userList.deleteFromRealm(1);
            }
        });
    }

    public class MySchemaModule implements RealmModel {

    }

    /**
     * 版本升级
     */
    /**
     * 升级数据库
     */
    class CustomMigration implements RealmMigration {
        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
            RealmSchema schema = realm.getSchema();
            if (oldVersion == 0 && newVersion == 1) {
                RealmObjectSchema personSchema = schema.get("User");
                //新增@Required的id
                personSchema.addField("ids", String.class, FieldAttribute.REQUIRED)
                        .transform(new RealmObjectSchema.Function() {
                            @Override
                            public void apply(DynamicRealmObject obj) {
                                obj.set("ids", "1");//为id设置值
                                //为已存在的数据设置dogs数据
                                DynamicRealmObject dog = realm.createObject("Dog");
                                dog.set("name", "二哈");
                                dog.set("age", 2);
                                obj.getList("dogs").add(dog);
                            }
                        })
                        .removeField("age");//移除age属性
                personSchema.setNullable("id", true);//设置id为非必须字段
                personSchema.renameField("id", "userId");//重命名字段
                oldVersion++;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}
