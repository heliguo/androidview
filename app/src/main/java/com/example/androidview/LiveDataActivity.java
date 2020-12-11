package com.example.androidview;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

/**
 * @author lgh on 2020/12/10 14:41
 * @description
 */
public class LiveDataActivity extends AppCompatActivity {


    static class User {

        public String name;

        public int age;

    }

    MutableLiveData<User> mUserMutableLiveData = new MutableLiveData<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_data);
        /**
         * 静态转换，获取到源数据后转换为目标数据
         */
        MutableLiveData<String> stringLiveData = (MutableLiveData<String>) Transformations.map(mUserMutableLiveData,
                new Function<User, String>() {
                    @Override
                    public String apply(User input) {
                        return input.name + input.age;
                    }
                });


        /**
         * 动态转换，获取到源数据后可再进行网络请求
         */
        LiveData<String> stringLiveData1 = Transformations.switchMap(mUserMutableLiveData,
                new Function<User, LiveData<String>>() {
                    @Override
                    public LiveData<String> apply(User input) {
                        return null;//另一个网络请求
                    }
                });

    }

    public static class Tuple2<S, T> {

        public final S first;
        public final T second;

        public Tuple2(S first, T second) {
            this.second = second;
            this.first = first;
        }

    }

    public static class LiveDataTransformations<S, T> {

        private LiveDataTransformations() {
        }

        public LiveData<Tuple2<S, T>> ifNotNull(LiveData<S> first, LiveData<T> second) {

            MediatorLiveData<Tuple2<S, T>> mediator = new MediatorLiveData<>();

            /**
             * 嵌套使用，数据全部变化时更新
             */
            mediator.addSource(first, s -> {

                if (s != null) {
                    mediator.addSource(second, t -> {
                        if (t != null) {
                            mediator.setValue(new Tuple2<>(s, t));
                        }

                    });
                }

            });


            return mediator;

        }

    }
}
