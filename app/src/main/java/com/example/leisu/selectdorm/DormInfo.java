package com.example.leisu.selectdorm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

public class DormInfo extends Activity implements View.OnClickListener{

    private TextView kc_5;
    private TextView kc_13;
    private TextView kc_14;
    private TextView kc_8;
    private TextView kc_9;
    private TextView back;
    private String gender;
    private int errorcode;
    private int k5;
    private int k13;
    private int k14;
    private int k8;
    private int k9;


    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    Log.d("url_error", msg.obj.toString());
                    kc_5.setText(String.valueOf(k5));
                    kc_13.setText(String.valueOf(k13));
                    kc_14.setText(String.valueOf(k14));
                    kc_8.setText(String.valueOf(k8));
                    kc_9.setText(String.valueOf(k9));
                    break;
                default:
                    break;
            }
        }
    };

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

        Intent intent = this.getIntent();
        gender = intent.getStringExtra("gender");
        Log.d("gender",gender);
        query(gender);
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


    public void query(String code) {
        int sex = 0;
        if (code.equals("男")) {
            sex = 1;
        }else if (code.equals("女")) {
            sex = 2;
        }
        final String address = "https://api.mysspku.com/index.php/V1/MobileCourse/getRoom?gender=" + String.valueOf(sex);
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
                    getNum(responseStr);
                    if (errorcode == 0) {
                        Message msg =new Message();
                        msg.what = 1;
                        msg.obj = errorcode;
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

    public void getNum(String json) {
        try{
            JSONObject obj = new JSONObject(json);
            errorcode = obj.getInt("errcode");
            JSONObject mid = obj.getJSONObject("data");
            k5 = mid.getInt("5");
            k13 = mid.getInt("13");
            k14 = mid.getInt("14");
            k8 = mid.getInt("8");
            k9 = mid.getInt("9");
            Log.d("url_k",k5 + " " +  k13 + " " + k14 + " " + k8 + " " + k9);
        }catch(JSONException e) {
            e.printStackTrace();
        }
    }
}
