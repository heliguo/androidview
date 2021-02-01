package com.example.androidview.slideBar;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidview.MainActivity;
import com.example.androidview.R;
import com.example.androidview.databinding.ActivitySlideBarBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author lgh on 2021/1/30 19:59
 * @description
 */
public class SlideBarActivity extends AppCompatActivity {

    ActivitySlideBarBinding mBinding;

    private List<Person> persons = new ArrayList<>();

    private ListView lvMain;
    private TextView tvWord;

    private  IndexAdapter adapter;

    private Handler handler = new Handler();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySlideBarBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        tvWord = findViewById(R.id.tv_word);
        lvMain = findViewById(R.id.lv_main);
        mBinding.recordListSbLetter.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                updateWord(s);
                updateListView(s);
            }
        });
        initData();
        adapter = new IndexAdapter();
        lvMain.setAdapter(adapter);
    }

    private void updateListView(String word) {
        for(int i=0;i<persons.size();i++){
            String listWord = persons.get(i).getPinyin().substring(0,1);//YANGGUANGFU-->Y
            if (word.equals(listWord)) {
                //i是listView中的位置
                lvMain.setSelection(i);//定位到ListVeiw中的某个位置
                return;
            }
        }
    }


    private void updateWord(String word){
        //显示
        tvWord.setVisibility(View.VISIBLE);
        tvWord.setText(word);
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //也是运行在主线程中
                tvWord.setVisibility(View.GONE);
            }
        },500);
    }


    /**
     * 初始化数据
     */
    private void initData() {

        persons = new ArrayList<>();
        persons.add(new Person("张晓飞"));
        persons.add(new Person("杨光福"));
        persons.add(new Person("胡继群"));
        persons.add(new Person("刘畅"));

        persons.add(new Person("钟泽兴"));
        persons.add(new Person("尹革新"));
        persons.add(new Person("安传鑫"));
        persons.add(new Person("张骞壬"));

        persons.add(new Person("温松"));
        persons.add(new Person("李凤秋"));
        persons.add(new Person("刘甫"));
        persons.add(new Person("娄全超"));
        persons.add(new Person("张猛"));

        persons.add(new Person("王英杰"));
        persons.add(new Person("李振南"));
        persons.add(new Person("孙仁政"));
        persons.add(new Person("唐春雷"));
        persons.add(new Person("牛鹏伟"));
        persons.add(new Person("姜宇航"));

        persons.add(new Person("刘挺"));
        persons.add(new Person("张洪瑞"));
        persons.add(new Person("张建忠"));
        persons.add(new Person("侯亚帅"));
        persons.add(new Person("刘帅"));

        persons.add(new Person("乔竞飞"));
        persons.add(new Person("徐雨健"));
        persons.add(new Person("吴亮"));
        persons.add(new Person("王兆霖"));

        persons.add(new Person("阿三"));
        persons.add(new Person("李博俊"));

        //排序
        Collections.sort(persons, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {

                return o1.getPinyin().compareTo(o2.getPinyin());
            }
        });
    }

    class  IndexAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return persons.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView == null){
                convertView = View.inflate(SlideBarActivity.this, R.layout.item_main,null);
                viewHolder = new ViewHolder();
                viewHolder.tv_word =  convertView.findViewById(R.id.tv_word);
                viewHolder.tv_name =  convertView.findViewById(R.id.tv_name);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }

            String name = persons.get(position).getName();//阿福
            String word = persons.get(position).getPinyin().substring(0,1);//AFU->A
            viewHolder.tv_word.setText(word);
            viewHolder.tv_name.setText(name);
            if(position ==0){
                viewHolder.tv_word.setVisibility(View.VISIBLE);
            }else{
                //得到前一个位置对应的字母，如果当前的字母和上一个相同，隐藏；否则就显示
                String preWord = persons.get(position-1).getPinyin().substring(0,1);//A~Z
                if(word.equals(preWord)){
                    viewHolder.tv_word.setVisibility(View.GONE);
                }else{
                    viewHolder.tv_word.setVisibility(View.VISIBLE);
                }
            }
            return convertView;
        }
    }
    static class ViewHolder{
        TextView tv_word;
        TextView tv_name;
    }


}
