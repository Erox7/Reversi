package com.example.erox.reversi;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ConfigActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
    }
    public void moveToGame(View view){
        Intent in = new Intent(this,DesarrolloActivity.class);
        RadioGroup RG = (RadioGroup) findViewById(R.id.RadioGroup);
        RadioButton RB = (RadioButton) findViewById(RG.getCheckedRadioButtonId());
        in.putExtra("key",RB.getText());
        startActivity(in);
        finish();
    }
}
