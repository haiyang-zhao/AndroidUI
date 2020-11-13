package com.zhy.recycler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.zhy.recycler.slidecard.SlideCardActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void onRecycler(View view) {
        startActivity(new Intent(this, TestRecyclerActivity.class));
    }

    public void onSlideCard(View view) {
        startActivity(new Intent(this, SlideCardActivity.class));
    }
}