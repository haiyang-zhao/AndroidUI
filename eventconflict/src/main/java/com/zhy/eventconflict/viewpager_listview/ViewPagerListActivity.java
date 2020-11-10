package com.zhy.eventconflict.viewpager_listview;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zhy.eventconflict.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewPagerListActivity extends AppCompatActivity {

    private int[] iv = new int[]{R.mipmap.iv_0, R.mipmap.iv_1, R.mipmap.iv_2,
            R.mipmap.iv_3, R.mipmap.iv_4, R.mipmap.iv_5,
            R.mipmap.iv_6, R.mipmap.iv_7, R.mipmap.iv_8};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bad_pager);
        BadViewPager pager = findViewById(R.id.viewpager);
        List<Map<String, Integer>> strings = new ArrayList<>();

        Map<String, Integer> map;

        for (int i = 0; i < 20; i++) {
            map = new HashMap<>();
            map.put("key", iv[i % 9]);
            strings.add(map);
        }
        MyPagerAdapter adapter = new MyPagerAdapter(this, strings);
        pager.setAdapter(adapter);
    }
}
