package com.example.erox.reversi;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ResultsActivity extends AppCompatActivity {
    EditText mail;
    EditText log;
    EditText day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        this.mail = (EditText)findViewById(R.id.emailEditText);
        this.log = (EditText)findViewById(R.id.idLog);
        this.day = (EditText)findViewById(R.id.dayHour);
        if(savedInstanceState != null){
            mail.setText(savedInstanceState.getString("mail"));
            log.setText(savedInstanceState.getString("Log"));
            day.setText(savedInstanceState.getString("day"));
        }
    }

    public void moveToGame(View view){
        Intent in = new Intent(this, DesarrolloActivity.class);
        startActivity(in);
        finish();
    }
    public void SendMail(View view){

        if(!mail.getText().toString().equals(null) && !log.getText().toString().equals(null)) {
            Intent in = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", mail.getText().toString(), null));
            in.putExtra(Intent.EXTRA_TEXT, log.getText().toString());
            startActivity(Intent.createChooser(in, "Send email..."));
            finish();
        }
    }
    public void Exit(View view){
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("Log",log.getText().toString());
        outState.putString("mail",mail.getText().toString());
        outState.putString("hour",day.getText().toString());
        super.onSaveInstanceState(outState);

    }
}
