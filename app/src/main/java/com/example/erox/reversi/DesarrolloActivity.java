package com.example.erox.reversi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
        gridview.setAdapter( new ButtonAdapter(this,numCols));
    }

}
