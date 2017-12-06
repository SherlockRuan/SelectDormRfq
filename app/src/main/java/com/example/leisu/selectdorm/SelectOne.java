package com.example.leisu.selectdorm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by leisu on 2017/12/6.
 */

public class SelectOne extends Activity implements View.OnClickListener{

    private EditText editText;
    private TextView Tback;
    private TextView Tstart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_one);

        editText = (EditText)findViewById(R.id.edit_one);

        Tback = (TextView)findViewById(R.id.sel_one_bb);
        Tback.setOnClickListener(this);

        Tstart = (TextView)findViewById(R.id.sel_1_start);
        Tstart.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.sel_one_bb) {
            Intent i1 = new Intent(SelectOne.this,SelectDorm.class);
            startActivity(i1);
            //finish();
        }

        if (view.getId() == R.id.sel_1_start) {
            Intent i2 = new Intent(SelectOne.this,MainActivity.class);
            startActivity(i2);
            //finish();
        }
    }
}
