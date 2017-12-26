package com.example.leisu.selectdorm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by leisu on 2017/11/29.
 */

public class SelectWay extends Activity implements View.OnClickListener {

    private TextView yanzheng;
    private TextView exit;
    private Button stu_info;
    private Button dor_info;
    private Button sel_dor;
    private String xuehao;
    private String vcode;
    private String gender;
    private String room = "未选择";
    private int errorcode;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    Log.d("url_h", msg.obj.toString());
                    yanzheng.setText(vcode);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select);

        yanzheng = (TextView)findViewById(R.id.sel_yanzheng);

        exit = (TextView)findViewById(R.id.sel_back);
        exit.setOnClickListener(this);

        stu_info = (Button) findViewById(R.id.sel_btn_1);
        stu_info.setOnClickListener(this);
        dor_info = (Button) findViewById(R.id.sel_btn_2);
        dor_info.setOnClickListener(this);
        sel_dor = (Button) findViewById(R.id.sel_btn_3);
        sel_dor.setOnClickListener(this);

        Intent intent = this.getIntent();
        xuehao = intent.getStringExtra("number");
        Log.d("xuehao",xuehao);
        query(xuehao);
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
            i2.putExtra("stuId", xuehao);
            startActivity(i2);
            //finish();
        }

        if (view.getId() == R.id.sel_btn_2) {
            Toast.makeText(SelectWay.this,"查看宿舍信息!", Toast.LENGTH_LONG).show();
            Intent i3 = new Intent(SelectWay.this,DormInfo.class);
            i3.putExtra("gender", gender);
            startActivity(i3);
            //finish();
        }

        if (view.getId() == R.id.sel_btn_3) {
            //Toast.makeText(SelectWay.this,"选择宿舍!", Toast.LENGTH_LONG).show();
            if (room.equals("")) {
                Intent i4 = new Intent(this,SelectDorm.class);
                i4.putExtra("student", xuehao);
                startActivity(i4);
                //finish();
            }else {
                Toast.makeText(SelectWay.this,"已经选择了宿舍!无法更改!", Toast.LENGTH_LONG).show();
            }
        }
    }


    public void query(String code) {
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
                    vcode = getVcode(responseStr);
                    gender = getGender(responseStr);
                    room = getRoom(responseStr);
                    if (errorcode == 0) {
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

    private String getRoom(String json) {
        String code = "";
        try{
            JSONObject obj = new JSONObject(json);
            JSONObject mid = obj.getJSONObject("data");
            code = mid.getString("room");
            Log.d("url_c",code);
        }catch(JSONException e) {
            e.printStackTrace();
        }
        return code;
    }
}
