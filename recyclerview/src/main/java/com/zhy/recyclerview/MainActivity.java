package com.zhy.recyclerview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new StartDecoration());
        recyclerView.setAdapter(new StartAdapter(this, mockStarts()));
    }

    private List<Start> mockStarts() {
        List<Start> starts = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 20; j++) {
                if (i % 2 == 0) {
                    starts.add(new Start(String.format("何炅%s_%s", i, j), "快乐家族" + i));
                } else {
                    starts.add(new Start(String.format("汪涵%s_%s", i, j), "天天兄弟" + i));
                }
            }
        }
        return starts;
    }
}