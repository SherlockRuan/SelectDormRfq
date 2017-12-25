package com.example.leisu.selectdorm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
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

public class StudentInfo extends Activity implements View.OnClickListener{

    private TextView fanhui;
    private TextView xuehao;
    private TextView xingming;
    private TextView xingbie;
    private TextView yanzhengma;
    private TextView sushehao;
    private TextView sushelou;
    private TextView xiaoqu;
    private TextView nianji;

    private int errorcode;
    private String stuId;      //学号
    private String name;       //姓名
    private String gender;     //性别
    private String vcode;      //验证码
    private String room;       //房间
    private String building;  //楼
    private String location;  //校区
    private String grade;     //年级

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    Log.d("url_h", msg.obj.toString());
                    xuehao.setText(stuId);
                    xingming.setText(name);
                    xingbie.setText(gender);
                    yanzhengma.setText(vcode);
                    sushehao.setText(room);
                    sushelou.setText(building);
                    xiaoqu.setText(location);
                    nianji.setText(grade);
                    break;
                default:
                    break;
            }
        }
    };

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


        Intent intent = this.getIntent();
        stuId = intent.getStringExtra("stuId");
        Log.d("stuId",stuId);
        query(stuId);
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
                    if (errorcode == 0) {
                        name = getName(responseStr);
                        vcode = getVcode(responseStr);
                        gender = getGender(responseStr);
                        room = getRoom(responseStr);
                        building = getBuilding(responseStr);
                        location = getLocation(responseStr);
                        grade = getGrade(responseStr);
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

    private String getBuilding(String json) {
        String code = "";
        try{
            JSONObject obj = new JSONObject(json);
            JSONObject mid = obj.getJSONObject("data");
            code = mid.getString("building");
            Log.d("url_c",code);
        }catch(JSONException e) {
            e.printStackTrace();
        }
        return code;
    }

    private String getLocation(String json) {
        String code = "";
        try{
            JSONObject obj = new JSONObject(json);
            JSONObject mid = obj.getJSONObject("data");
            code = mid.getString("location");
            Log.d("url_c",code);
        }catch(JSONException e) {
            e.printStackTrace();
        }
        return code;
    }

    private String getGrade(String json) {
        String code = "";
        try{
            JSONObject obj = new JSONObject(json);
            JSONObject mid = obj.getJSONObject("data");
            code = mid.getString("grade");
            Log.d("url_c",code);
        }catch(JSONException e) {
            e.printStackTrace();
        }
        return code;
    }
}
