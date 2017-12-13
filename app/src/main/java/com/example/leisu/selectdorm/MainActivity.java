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
import android.app.Notification.MessagingStyle.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.net.HttpURLConnection;
import javax.net.ssl.*;
import java.net.ConnectException;




/**
 * Created by leisu on 2017/10/27.
 */

public class MainActivity extends Activity implements View.OnClickListener{

    private ImageView sign;
    private EditText wel_xuehao;
    private EditText wel_mima;
    private int error = 1;
    private TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dorm_info);

        sign = (ImageView)findViewById(R.id.wel_btn_sign);
        sign.setOnClickListener(this);

        wel_xuehao = (EditText)findViewById(R.id.wel_edit_xuehao);
        wel_mima = (EditText)findViewById(R.id.wel_edit_mima);

        test = (TextView)findViewById(R.id.wel_title_text);
        test.setOnClickListener(this);
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

        if (view.getId() == R.id.wel_title_text) {
            query2();
            if (error == 0){
                Toast.makeText(MainActivity.this,"测试成功!", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(MainActivity.this,"测试失败!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void query() {
        final String address = "http://www.sojson.com/open/api/lunar/json.shtml";
        Log.d("url",address);
        new Thread (new Runnable(){
            @Override
            public void run() {
                HttpURLConnection con = null;
                try {
                    //创建SSLContext对象，并使用我们指定的信任管理器初始化
                    //TrustManager[] tm = { new MyX509TrustManager() };
                    //SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
                    //sslContext.init(null, tm, new java.security.SecureRandom());
                    // 从上述SSLContext对象中得到SSLSocketFactory对象
                    //SSLSocketFactory ssf = sslContext.getSocketFactory();





                    Log.d("url","***1***");
                    URL url = new URL(address);
                    con = (HttpURLConnection)url.openConnection();//con.setSSLSocketFactory(ssf);
                    con.setRequestMethod("GET");
                    con.setConnectTimeout(8000);
                    con.setReadTimeout(8000);
                    Log.d("url","***2***");
                    InputStream in = con.getInputStream();
                    Log.d("url","***3***");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    Log.d("url","***4***");
                    StringBuilder response = new StringBuilder();
                    Log.d("url","***5***");
                    String str;
                    Log.d("url","***6***");
                    while ((str = reader.readLine()) != null) {
                        response.append(str);
                        Log.d("url",str);
                    }
                    String responseStr = response.toString();
                    Log.d("url",responseStr);
                    error = parseJSON(responseStr);
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


    public void query2() {
        final String address = "https://api.mysspku.com/index.php/V1/MobileCourse/Login?username=1301210899&password=1301210899";
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(address);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod("GET");

            if ("GET".equalsIgnoreCase("GET"))
                httpUrlConn.connect();


            InputStream in = httpUrlConn.getInputStream();
            Log.d("url","***3***");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            Log.d("url","***4***");
            StringBuilder response = new StringBuilder();
            Log.d("url","***5***");
            String str;
            Log.d("url","***6***");
            while ((str = reader.readLine()) != null) {
                response.append(str);
                Log.d("url",str);
            }
            String responseStr = response.toString();
            Log.d("url",responseStr);
            error = parseJSON(responseStr);
            // 将返回的输入流转换成字符串
            /*InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;*/
            httpUrlConn.disconnect();
        } catch (ConnectException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
