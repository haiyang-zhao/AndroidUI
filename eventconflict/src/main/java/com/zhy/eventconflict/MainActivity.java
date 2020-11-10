package com.zhy.eventconflict;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.zhy.eventconflict.viewpager_listview.ViewPagerListActivity;
import com.zhy.eventconflict.viewpager_swaprefresh.SRL_VP_main;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void viewPagerListViewConflict(View view) {
        startActivity(new Intent(this, ViewPagerListActivity.class));
    }

    public void srl_vp(View view) {
        startActivity(new Intent(this, SRL_VP_main.class));
    }
}