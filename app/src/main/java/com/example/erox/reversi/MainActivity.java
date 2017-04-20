package com.example.erox.reversi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void moveToHelp(View view){
        Intent in = new Intent(this,HelpActivity.class);
        startActivity(in);
        finish();
    }
    public void moveToGame(View view){
        Intent in = new Intent(this,ConfigActivity.class);
        startActivity(in);
        finish();
    }
    public void Exit(View view){
        finish();
    }
}
