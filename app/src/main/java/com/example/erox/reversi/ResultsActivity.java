package com.example.erox.reversi;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

    }

    public void moveToGame(View view){
        Intent in = new Intent(this, DesarrolloActivity.class);
        startActivity(in);
        finish();
    }
    public void SendMail(View view){
        EditText mail = (EditText)findViewById(R.id.emailEditText);
        EditText log = (EditText)findViewById(R.id.idLog);
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
}
