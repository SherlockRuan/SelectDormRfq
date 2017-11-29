package com.example.leisu.selectdorm;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.util.Log;
import android.widget.Toast;
import android.content.Intent;

/**
 * Created by leisu on 2017/10/27.
 */

public class MainActivity extends Activity implements View.OnClickListener{

    private ImageView sign;
    private EditText wel_xuehao;
    private EditText wel_mima;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dorm_info);

        sign = (ImageView)findViewById(R.id.wel_btn_sign);
        sign.setOnClickListener(this);

        wel_xuehao = (EditText)findViewById(R.id.wel_edit_xuehao);
        wel_mima = (EditText)findViewById(R.id.wel_edit_mima);
    }

    public void onClick(View view) {

        if (view.getId() == R.id.wel_btn_sign) {
            String xuehao_s = wel_xuehao.getText().toString();
            String mima_s = wel_mima.getText().toString();
            Log.d("xuehao:",xuehao_s);
            Log.d("mima:",mima_s);
            if (!xuehao_s.equals("") & !mima_s.equals("")){
                if (Integer.valueOf(xuehao_s) % 2 == 0) {
                    Toast.makeText(MainActivity.this,"学号或密码错误!", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(MainActivity.this,"欢迎登陆!", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(this,SelectWay.class);
                    startActivity(i);
                    finish();
                }
            }else {
                Toast.makeText(MainActivity.this,"信息输入不完整!", Toast.LENGTH_LONG).show();
            }
        }

    }
}
