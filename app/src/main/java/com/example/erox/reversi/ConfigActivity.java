package com.example.erox.reversi;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ConfigActivity extends AppCompatActivity implements OnClickListener{
    private Context c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        this.c = this;
        Button bn = (Button) findViewById(R.id.button);
        bn.setOnClickListener(this);
    }

    public void onClick(View view){

        RadioGroup RG = (RadioGroup) findViewById(R.id.RadioGroup);
        RadioButton RB = (RadioButton) findViewById(RG.getCheckedRadioButtonId());
        EditText et = (EditText) findViewById(R.id.editText);

        if(et.getText().toString().equals("")) {

            Toast.makeText(this,"Alias requerido",Toast.LENGTH_LONG).show();

        }else{

            Intent in = new Intent(this.c, DesarrolloActivity.class);
            in.putExtra("Alias", et.getText());
            in.putExtra("key", RB.getText());
            startActivity(in);
            finish();
        }
    }
}
