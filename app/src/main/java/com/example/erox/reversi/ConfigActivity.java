package com.example.erox.reversi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ConfigActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
    }
    public void moveToGame(View view){
        Intent in = new Intent(this,DesarrolloActivity.class);
        startActivity(in);
        finish();
    }
}
