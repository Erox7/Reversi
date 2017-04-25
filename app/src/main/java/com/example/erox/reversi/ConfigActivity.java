package com.example.erox.reversi;

import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ConfigActivity extends AppCompatActivity implements OnClickListener{
    private Context c;
    RadioGroup RG;
    RadioButton RB;
    EditText et;
    CheckBox cb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        this.RG = (RadioGroup) findViewById(R.id.RadioGroup);
        this.RB = (RadioButton) findViewById(RG.getCheckedRadioButtonId());
        this.et = (EditText) findViewById(R.id.editText);
        this.cb = (CheckBox) findViewById(R.id.checkbox);

        if(savedInstanceState !=  null) {

            et.setText(savedInstanceState.getString("nAlias"));
            RG.check(savedInstanceState.getInt("int"));
            cb.setChecked(savedInstanceState.getBoolean("cbCheck"));
        }

        this.c = this;
        Button bn = (Button) findViewById(R.id.button);
        bn.setOnClickListener(this);
    }

    public void onClick(View view){


        this.et = (EditText) findViewById(R.id.editText);
        if(et.getText().toString().equals("")) {

            Toast.makeText(this,"Alias requerido",Toast.LENGTH_LONG).show();

        }else{

            Intent in = new Intent(this.c, DesarrolloActivity.class);
            this.RG = (RadioGroup) findViewById(R.id.RadioGroup);
            this.RB = (RadioButton) findViewById(RG.getCheckedRadioButtonId());
            this.cb = (CheckBox) findViewById(R.id.checkbox);

            in.putExtra("CheckKey",cb.isChecked());
            in.putExtra("Alias", et.getText());
            in.putExtra("key", RB.getText());
            startActivity(in);
            finish();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("cbCheck",cb.isChecked());
        outState.putString("nAlias",et.getText().toString());
        outState.putInt("int",RG.getCheckedRadioButtonId());
        super.onSaveInstanceState(outState);
    }
}
