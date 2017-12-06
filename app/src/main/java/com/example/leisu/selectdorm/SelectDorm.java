package com.example.leisu.selectdorm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by leisu on 2017/12/6.
 */

public class SelectDorm extends Activity implements View.OnClickListener{

    private TextView T1,T2,T3,T4,Tback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_dorm);

        T1 = (TextView)findViewById(R.id.sel_1_r);
        T1.setOnClickListener(this);

        T2 = (TextView)findViewById(R.id.sel_2_r);
        T2.setOnClickListener(this);

        T3 = (TextView)findViewById(R.id.sel_3_r);
        T3.setOnClickListener(this);

        T4 = (TextView)findViewById(R.id.sel_4_r);
        T4.setOnClickListener(this);

        Tback = (TextView)findViewById(R.id.sel_dorm_bb);
        Tback.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.sel_1_r) {
            Toast.makeText(SelectDorm.this,"选择单人办理!", Toast.LENGTH_LONG).show();
            Intent i1 = new Intent(SelectDorm.this,SelectOne.class);
            startActivity(i1);
            //finish();
        }

        if (view.getId() == R.id.sel_2_r) {
            Toast.makeText(SelectDorm.this,"选择双人办理!", Toast.LENGTH_LONG).show();
            Intent i2 = new Intent(SelectDorm.this,StudentInfo.class);
            startActivity(i2);
            //finish();
        }

        if (view.getId() == R.id.sel_3_r) {
            Toast.makeText(SelectDorm.this,"选择三人办理!", Toast.LENGTH_LONG).show();
            Intent i3 = new Intent(SelectDorm.this,DormInfo.class);
            startActivity(i3);
            //finish();
        }

        if (view.getId() == R.id.sel_4_r) {
            Toast.makeText(SelectDorm.this,"选择四人办理!", Toast.LENGTH_LONG).show();
            Intent i4 = new Intent(SelectDorm.this,StudentInfo.class);
            startActivity(i4);
            //finish();
        }

        if (view.getId() == R.id.sel_dorm_bb) {
            Intent i5 = new Intent(SelectDorm.this,SelectWay.class);
            startActivity(i5);
            //finish();
        }
    }
}
