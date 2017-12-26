package com.example.leisu.selectdorm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by leisu on 2017/12/13.
 */

public class SelectTwo extends Activity implements View.OnClickListener {

    private EditText editText;
    private EditText tongzhuren_xh;
    private EditText tongzhuren_yz;
    private TextView Tback;
    private TextView Tstart;
    private TextView xingming;
    private TextView xuehao;
    private TextView yanzhengma;
    private TextView xingbie;

    private String stuId;
    private int errorcode;
    private int errorcode2 = 100;
    private String name;
    private String gender;
    private String vcode;
    private String build;
    private String stu1id;
    private String v1code;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    Log.d("url_h", msg.obj.toString());
                    xuehao.setText(stuId);
                    xingming.setText(name);
                    xingbie.setText(gender);
                    yanzhengma.setText(vcode);
                    break;
                case 2:
                    if (errorcode2 == 0) {
                        Intent i2 = new Intent(SelectTwo.this,SelectFinal.class);
                        i2.putExtra("stuId",stuId);
                        startActivity(i2);
                        //finish();
                    }
                default:
                    break;
            }
        }
    };

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


        xingming = (TextView)findViewById(R.id.xingming_r);
        xingbie = (TextView)findViewById(R.id.xingbie_r);
        yanzhengma = (TextView)findViewById(R.id.yanzhengma_r);
        xuehao = (TextView)findViewById(R.id.xuehao_r);

        Intent intent = this.getIntent();
        stuId = intent.getStringExtra("id");
        Log.d("id",stuId);
        queryStu(stuId);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.sel_two_bb) {
            Intent i1 = new Intent(SelectTwo.this,SelectDorm.class);
            startActivity(i1);
            //finish();
        }

        if (view.getId() == R.id.sel_2_start) {
            build = editText.getText().toString();
            stu1id = tongzhuren_xh.getText().toString();
            v1code = tongzhuren_yz.getText().toString();



        }
    }


    public void queryStu(String code) {
        final String address = "https://api.mysspku.com/index.php/V1/MobileCourse/getDetail?stuid=" + code;
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
                    errorcode = getErrorcode(responseStr);
                    if (errorcode == 0) {
                        name = getName(responseStr);
                        vcode = getVcode(responseStr);
                        gender = getGender(responseStr);
                        //room = getRoom(responseStr);
                        Log.d("url", String.valueOf(vcode));
                        Message msg =new Message();
                        msg.what = 1;
                        msg.obj = vcode;
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


    public int getErrorcode (String json) {
        int code = 1;
        try{
            JSONObject obj = new JSONObject(json);
            code = obj.getInt("errcode");
            Log.d("url_errorcode",String.valueOf(code));
        }catch(JSONException e) {
            e.printStackTrace();
        }
        return code;
    }

    public int getErrorcode2 (String json) {
        int code = 1;
        try{
            JSONObject obj = new JSONObject(json);
            code = obj.getInt("error_code");
            Log.d("url_error_code",String.valueOf(code));
        }catch(JSONException e) {
            e.printStackTrace();
        }
        return code;
    }

    public String getVcode(String json) {
        String code = "";
        try{
            JSONObject obj = new JSONObject(json);
            JSONObject mid = obj.getJSONObject("data");
            code = mid.getString("vcode");
            Log.d("url_c",code);
        }catch(JSONException e) {
            e.printStackTrace();
        }
        return code;
    }

    private String getName(String json) {
        String code = "";
        try{
            JSONObject obj = new JSONObject(json);
            JSONObject mid = obj.getJSONObject("data");
            code = mid.getString("name");
            Log.d("url_c",code);
        }catch(JSONException e) {
            e.printStackTrace();
        }
        return code;
    }

    private String getGender(String json) {
        String code = "";
        try{
            JSONObject obj = new JSONObject(json);
            JSONObject mid = obj.getJSONObject("data");
            code = mid.getString("gender");
            Log.d("url_c",code);
        }catch(JSONException e) {
            e.printStackTrace();
        }
        return code;
    }
}
