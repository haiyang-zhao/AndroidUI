package com.zhy.recycler;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TestRecyclerActivity extends AppCompatActivity {


    private RecyclerView rv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_recycler);

        rv = (RecyclerView) findViewById(R.id.rv);

        rv.setLayoutManager(new GridLayoutManager(this, 3));
        rv.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        final List<String> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add("" + i);
        }

        final CustomAdapter adapter = new CustomAdapter(this, list);
        rv.setAdapter(adapter);
    }
}
