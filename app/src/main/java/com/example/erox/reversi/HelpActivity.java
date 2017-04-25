package com.example.erox.reversi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        TextView yourTextView = (TextView) findViewById(R.id.scroll);
        yourTextView.setMovementMethod(new ScrollingMovementMethod());
    }

    public void mainMenu(View view){
        Intent in = new Intent(this, MainActivity.class);
        startActivity(in);
        finish();
    }
}
