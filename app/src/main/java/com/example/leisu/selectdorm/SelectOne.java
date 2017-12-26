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
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by leisu on 2017/12/6.
 */

public class SelectOne extends Activity implements View.OnClickListener{

    private EditText editText;
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
    //private String room;
    private String vcode;
    private String build;

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
                        Intent i2 = new Intent(SelectOne.this,SelectFinal.class);
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
        setContentView(R.layout.select_one);

        editText = (EditText)findViewById(R.id.edit_one);

        Tback = (TextView)findViewById(R.id.sel_one_bb);
        Tback.setOnClickListener(this);

        Tstart = (TextView)findViewById(R.id.sel_1_start);
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
        if (view.getId() == R.id.sel_one_bb) {
            Intent i1 = new Intent(SelectOne.this,SelectDorm.class);
            startActivity(i1);
            //finish();
        }

        if (view.getId() == R.id.sel_1_start) {
            build = editText.getText().toString();
            //queryRoom();
            jsonPost();
        }
    }

    public void jsonPost() {
        //首先声明一下Url
        String urlPath = "https://api.mysspku.com/index.php/V1/MobileCourse/SelectRoom";
        try {
            MyX509TrustManager.allowAllSSL();
            URL url = new URL(urlPath);
            JSONObject ClientKey = new JSONObject();
            ClientKey.put("num", 1);
            ClientKey.put("stuid", stuId);
            ClientKey.put("buildingNo",Integer.valueOf(build));
           // JSONObject Authorization = new JSONObject();
            //Authorization.put("Person", ClientKey);
            String content = String.valueOf(ClientKey);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();// 已经封装好了数据,接着要把封装好的数据传递过去
            conn.setConnectTimeout(8000);
            conn.setDoOutput(true);// 设置允许输出
            conn.setRequestMethod("POST");
            conn.setRequestProperty("ser-Agent", "Fiddler");// 设置User-Agent: Fiddler
            conn.setRequestProperty("Content-Type", "application/json");// 设置contentType
            if (ClientKey != null) {
                OutputStream os = conn.getOutputStream();
                os.write(content.getBytes());
                os.close();
            }
            int code = conn.getResponseCode();//服务器返回的响应码
            if (code == 200) { // 等于200了,下面就可以获取服务器的数据了
                Log.d("url_res", String.valueOf(code));
                InputStream is = conn.getInputStream();// 已经连接上了，也获得了服务器的数据了，接着就是解析服务器传递过来的数据，开始解析服务器传递过来的参数,
                String json = NetUtils.readString(is);//json就已经是{"Person":{"username":"zhangsan","age":"12"}} //这个形式了,只不过是String类型
                JSONObject jsonObject = new JSONObject(json);//然后我们把json转换成JSONObject类型得到{"Person": //{"username":"zhangsan","age":"12"}}
                errorcode2 = jsonObject.getInt("error_code");
                if (errorcode2 == 0) {
                    Message msg =new Message();
                    msg.what = 2;
                    msg.obj = errorcode2;
                    mHandler.sendMessage(msg);
                }
            } else {
                Log.d("url_res", "数据提交失败");
            }
        } catch (Exception e) {
            e.printStackTrace();

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

    public void queryRoom() {
        final String address = "https://api.mysspku.com/index.php/V1/MobileCourse/SelectRoom?num=1&stuid=" + stuId + "&stu1id=&v1code=&stu2id=&v2code=&stu3id=&v3code=&buildingNo=" + build;
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
                    errorcode2 = getErrorcode2(responseStr);
                    Log.d("url_errorcode2",String.valueOf(errorcode2));
                    if (errorcode == 0) {
                        Message msg =new Message();
                        msg.what = 2;
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

    /*
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
    */
}



class NetUtils {
    public static byte[] readBytes(InputStream is){
        try {
            byte[] buffer = new byte[1024];
            int len = -1 ;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while((len = is.read(buffer)) != -1){
                baos.write(buffer, 0, len);
            }
            baos.close();
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null ;
    }
    public static String readString(InputStream is){
        return new String(readBytes(is));
    }

}