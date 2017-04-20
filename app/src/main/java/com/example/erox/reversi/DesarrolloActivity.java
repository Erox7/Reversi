package com.example.erox.reversi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.Arrays;

public class DesarrolloActivity extends AppCompatActivity {
    private int time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desarrollo);
        Intent in = getIntent();
        Integer numCols = Integer.parseInt(in.getStringExtra("key").toString());
        GridView gridview = (GridView) findViewById(R.id.gridView);
        gridview.setNumColumns(numCols);
        ImageAdapter adapter = new ImageAdapter(this);
        Integer [] toSet = new Integer[0];
        for(int i=0; i < (numCols*numCols);i++ ){
                toSet = addElement(toSet, R.drawable.button_normal);
        }
        adapter.setmThumbIds(toSet);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
    static Integer[] addElement(Integer[] a, int e) {
        a  = Arrays.copyOf(a, a.length + 1);
        a[a.length - 1] = e;
        return a;
    }

}
