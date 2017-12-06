package com.example.leisu.selectdorm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by leisu on 2017/11/29.
 */

public class SelectWay extends Activity implements View.OnClickListener {

    private TextView yanzheng;
    private TextView exit;
    private Button stu_info;
    private Button dor_info;
    private Button sel_dor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select);

        yanzheng = (TextView)findViewById(R.id.sel_yanzheng);
        yanzheng.setText("F4DS6T");

        exit = (TextView)findViewById(R.id.sel_back);
        exit.setOnClickListener(this);

        stu_info = (Button) findViewById(R.id.sel_btn_1);
        stu_info.setOnClickListener(this);
        dor_info = (Button) findViewById(R.id.sel_btn_2);
        dor_info.setOnClickListener(this);
        sel_dor = (Button) findViewById(R.id.sel_btn_3);
        sel_dor.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.sel_back) {
            Toast.makeText(SelectWay.this,"退出登陆!", Toast.LENGTH_LONG).show();
            Intent i1 = new Intent(SelectWay.this,MainActivity.class);
            startActivity(i1);
            //finish();
        }

        if (view.getId() == R.id.sel_btn_1) {
            Toast.makeText(SelectWay.this,"查看个人信息!", Toast.LENGTH_LONG).show();
            Intent i2 = new Intent(SelectWay.this,StudentInfo.class);
            startActivity(i2);
            //finish();
        }

        if (view.getId() == R.id.sel_btn_2) {
            Toast.makeText(SelectWay.this,"查看宿舍信息!", Toast.LENGTH_LONG).show();
            Intent i3 = new Intent(SelectWay.this,DormInfo.class);
            startActivity(i3);
            //finish();
        }

        if (view.getId() == R.id.sel_btn_3) {
            Toast.makeText(SelectWay.this,"选择宿舍!", Toast.LENGTH_LONG).show();
            Intent i4 = new Intent(this,SelectDorm.class);
            startActivity(i4);
            //finish();
        }
    }
}
