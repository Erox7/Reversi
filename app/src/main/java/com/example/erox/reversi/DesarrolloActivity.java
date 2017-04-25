package com.example.erox.reversi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.Arrays;

public class DesarrolloActivity extends AppCompatActivity {
    private ButtonAdapter BA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desarrollo);

        GridView gridview = (GridView) findViewById(R.id.gridView);
        Intent in = getIntent();
        Integer numCols = Integer.parseInt(in.getStringExtra("key").toString());
        String Alias = in.getStringExtra("Alias");
        boolean count_timer = in.getBooleanExtra("CheckKey", false);
        gridview.setNumColumns(numCols);

        if(savedInstanceState == null) {

            TextView timeView = (TextView) findViewById(R.id.TimeCounter);
            TextView emptyFields = (TextView) findViewById(R.id.BlocksLeft);
            TextView contadorView = (TextView) findViewById(R.id.Contador);

            this.BA = new ButtonAdapter(this, numCols, timeView, emptyFields, contadorView, (System.currentTimeMillis() / 1000), count_timer);
            gridview.setAdapter(BA);
        }else{
            BA = (ButtonAdapter) savedInstanceState.getSerializable("ButtonAdapter");
            gridview.setAdapter(BA);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("ButtonAdapter",BA);
    }
}
