package com.example.leisu.selectdorm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by leisu on 2017/11/29.
 */

public class StudentInfo extends Activity implements View.OnClickListener{

    private TextView fanhui;
   // private TextView tuichu;
    private TextView xuehao;
    private TextView xingming;
    private TextView xingbie;
    private TextView yanzhengma;
    private TextView sushehao;
    private TextView sushelou;
    private TextView xiaoqu;
    private TextView nianji;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_info);

        fanhui = (TextView)findViewById(R.id.stu_bb);
        fanhui.setOnClickListener(this);

        //tuichu = (TextView)findViewById(R.id.stu_back);
        //tuichu.setOnClickListener(this);

        xuehao = (TextView)findViewById(R.id.xuehao_r);
        xingming = (TextView)findViewById(R.id.xingming_r);
        xingbie = (TextView)findViewById(R.id.xingbie_r);
        yanzhengma = (TextView)findViewById(R.id.yanzhengma_r);
        sushehao = (TextView)findViewById(R.id.sushehao_r);
        sushelou = (TextView)findViewById(R.id.sushehlou_r);
        xiaoqu = (TextView)findViewById(R.id.xiaoqu_r);
        nianji = (TextView)findViewById(R.id.nianji_r);
    }

    @Override
    public void onClick(View view) {
        /*if (view.getId() == R.id.stu_back) {
            Toast.makeText(StudentInfo.this,"退出登陆!", Toast.LENGTH_LONG).show();
            Intent i1 = new Intent(StudentInfo.this,MainActivity.class);
            startActivity(i1);
            //finish();
        }*/

        if (view.getId() == R.id.stu_bb) {
            Intent i2 = new Intent(StudentInfo.this,SelectWay.class);
            startActivity(i2);
        }
    }
}
