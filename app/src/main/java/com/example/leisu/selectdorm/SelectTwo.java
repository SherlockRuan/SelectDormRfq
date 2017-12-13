package com.example.leisu.selectdorm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by leisu on 2017/12/13.
 */

public class SelectTwo extends Activity implements View.OnClickListener {

    private EditText editText;
    private EditText tongzhuren_xh;
    private EditText tongzhuren_yz;
    private TextView Tback;
    private TextView Tstart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_two);

        editText = (EditText)findViewById(R.id.edit_one);
        tongzhuren_xh = (EditText)findViewById(R.id.tongzhuren_xh_r);
        tongzhuren_yz = (EditText)findViewById(R.id.tongzhuren_yz_r);

        Tback = (TextView)findViewById(R.id.sel_two_bb);
        Tback.setOnClickListener(this);

        Tstart = (TextView)findViewById(R.id.sel_2_start);
        Tstart.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.sel_two_bb) {
            Intent i1 = new Intent(SelectTwo.this,SelectDorm.class);
            startActivity(i1);
            //finish();
        }

        if (view.getId() == R.id.sel_2_start) {
            Intent i2 = new Intent(SelectTwo.this,SelectFinal.class);
            startActivity(i2);
            //finish();
        }
    }
}
