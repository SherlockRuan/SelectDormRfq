package com.example.leisu.selectdorm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by leisu on 2017/11/29.
 */

public class DormInfo extends Activity implements View.OnClickListener{

    private TextView kc_5;
    private TextView kc_13;
    private TextView kc_14;
    private TextView kc_8;
    private TextView kc_9;
    private TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dor_info);

        kc_5 = (TextView)findViewById(R.id.k_5);
        kc_8 = (TextView)findViewById(R.id.k_8);
        kc_9 = (TextView)findViewById(R.id.k_9);
        kc_13 = (TextView)findViewById(R.id.k_13);
        kc_14 = (TextView)findViewById(R.id.k_14);

        back = (TextView)findViewById(R.id.dor_bb);
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.dor_bb) {
            Intent i1 = new Intent(DormInfo.this,SelectWay.class);
            i1.setClass(DormInfo.this,SelectWay.class);
            startActivity(i1);
        }
        /*if (view.getId() == R.id.dor_back) {
            Intent i2 = new Intent(DormInfo.this,MainActivity.class);
            startActivity(i2);
            //finish();
        }*/
    }
}
