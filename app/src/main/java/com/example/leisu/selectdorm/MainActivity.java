package com.example.leisu.selectdorm;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.method.PasswordTransformationMethod;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;




/**
 * Created by leisu on 2017/10/27.
 */

public class MainActivity extends Activity implements View.OnClickListener{

    private ImageView sign;
    private EditText wel_xuehao;
    private EditText wel_mima;
    private int error = 1;
    private TextView test;

    private String xuehao_s;
    private String mima_s;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    Log.d("url", msg.obj.toString());
                    if (error == 0) {
                        Toast.makeText(MainActivity.this,"欢迎登陆!", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(MainActivity.this,SelectWay.class);
                        i.putExtra("number", xuehao_s);
                        startActivity(i);
                        finish();
                    } else if (error == 40001 || error == 40002) {
                        Toast.makeText(MainActivity.this,"账号或密码错误!", Toast.LENGTH_LONG).show();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dorm_info);

        sign = (ImageView)findViewById(R.id.wel_btn_sign);
        sign.setOnClickListener(this);

        wel_xuehao = (EditText)findViewById(R.id.wel_edit_xuehao);
        wel_mima = (EditText)findViewById(R.id.wel_edit_mima);
        wel_mima.setTransformationMethod(PasswordTransformationMethod.getInstance());

        test = (TextView)findViewById(R.id.wel_title_text);
        test.setOnClickListener(this);
    }

    public void onClick(View view) {

        if (view.getId() == R.id.wel_btn_sign) {
            xuehao_s = wel_xuehao.getText().toString();
            mima_s = wel_mima.getText().toString();
            Log.d("xuehao:",xuehao_s);
            Log.d("mima:",mima_s);
            if (!xuehao_s.equals("") & !mima_s.equals("")){
                query(xuehao_s,mima_s);
            }else {
                Toast.makeText(MainActivity.this,"信息输入不完整!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void query(String name,String code) {
        final String address = "https://api.mysspku.com/index.php/V1/MobileCourse/Login?username=" + name + "&password=" + code;
        Log.d("url",address);
        new Thread (new Runnable(){
            @Override
            public void run() {
                HttpURLConnection con = null;
                try {
                    MyX509TrustManager.allowAllSSL();
                    URL url = new URL(address);
                    con = (HttpURLConnection)url.openConnection();//con.setSSLSocketFactory(ssf);
                    con.setRequestMethod("GET");
                    con.setConnectTimeout(8000);
                    con.setReadTimeout(8000);
                    InputStream in = con.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String str;
                    while ((str = reader.readLine()) != null) {
                        response.append(str);
                        Log.d("url",str);
                    }
                    String responseStr = response.toString();
                    Log.d("url",responseStr);
                    error = parseJSON(responseStr);
                    if (error != 1) {
                        Log.d("url", String.valueOf(error));
                        Message msg =new Message();
                        msg.what = 1;
                        msg.obj = error;
                        mHandler.sendMessage(msg);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if(con != null){
                        con.disconnect();
                    }
                }
            }
        }) .start();
    }



    public int parseJSON(String json) {
        int errorcode = 1;
        try{
            JSONObject obj = new JSONObject(json);
            errorcode = obj.getInt("errcode");
        }catch(JSONException e) {
            e.printStackTrace();
        }
        return errorcode;
    }
}
