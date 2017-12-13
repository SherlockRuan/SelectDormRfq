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

public class SelectFour extends Activity implements View.OnClickListener {

    private EditText editText;
    private EditText tongzhuren1_xh;
    private EditText tongzhuren1_yz;
    private EditText tongzhuren2_xh;
    private EditText tongzhuren2_yz;
    private EditText tongzhuren3_xh;
    private EditText tongzhuren3_yz;
    private TextView Tback;
    private TextView Tstart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_four);

        editText = (EditText)findViewById(R.id.edit_one);

        tongzhuren1_xh = (EditText)findViewById(R.id.tongzhuren1_xh_r);
        tongzhuren1_yz = (EditText)findViewById(R.id.tongzhuren1_yz_r);
        tongzhuren2_xh = (EditText)findViewById(R.id.tongzhuren2_xh_r);
        tongzhuren2_yz = (EditText)findViewById(R.id.tongzhuren2_yz_r);
        tongzhuren3_xh = (EditText)findViewById(R.id.tongzhuren3_xh_r);
        tongzhuren3_yz = (EditText)findViewById(R.id.tongzhuren3_yz_r);

        Tback = (TextView)findViewById(R.id.sel_four_bb);
        Tback.setOnClickListener(this);

        Tstart = (TextView)findViewById(R.id.sel_4_start);
        Tstart.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.sel_four_bb) {
            Intent i1 = new Intent(SelectFour.this,SelectDorm.class);
            startActivity(i1);
            //finish();
        }

        if (view.getId() == R.id.sel_4_start) {
            Intent i2 = new Intent(SelectFour.this,SelectFinal.class);
            startActivity(i2);
            //finish();
        }
    }
}
