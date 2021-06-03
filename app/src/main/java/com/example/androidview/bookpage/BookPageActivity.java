package com.example.androidview.bookpage;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidview.R;
import com.example.androidview.bookpage.factory.PageFactory;
import com.example.androidview.bookpage.factory.PicturesPageFactory;
import com.example.androidview.bookpage.view.BookPageView;
import com.example.androidview.bookpage.view.CoverPageView;

/**
 * @author lgh on 2021/3/15 16:15
 * @description
 */
public class BookPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_page);
        BookPageView bookPageView = findViewById(R.id.book_page);
        CoverPageView coverPageView = findViewById(R.id.cover_page);

        coverPageView.setPageFactory(new PicturesPageFactory(this,new int[]{R.drawable.bg_ios
                ,R.drawable.bg_android
                ,R.drawable.bg_js
                ,R.drawable.bg_other}));
    }
}
