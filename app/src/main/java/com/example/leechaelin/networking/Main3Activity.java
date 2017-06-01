package com.example.leechaelin.networking;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class Main3Activity extends AppCompatActivity {
    EditText e1,e2;
    TextView msg;
    Button b1;
    String userid="",password="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        e1 = (EditText)findViewById(R.id.userid);
        e2 = (EditText)findViewById(R.id.password);
        msg = (TextView)findViewById(R.id.msg);

    }

    public void onClick(View v){
        if(v.getId()==R.id.login){
            userid = e1.getText().toString();
            password = e2.getText().toString();
            thread.start();
        }
    }

    Handler handler = new Handler();
    Thread thread = new Thread(){
        @Override
        public void run() {
            try{
                URL url = new URL("http://jerry1004.dothome.co.kr/info/login.php" );
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                String postData = "userid=" + URLEncoder.encode(userid) + "&password=" + URLEncoder.encode(password);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postData.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                InputStream inputStream;
                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
                    inputStream = httpURLConnection.getInputStream();
                else
                    inputStream = httpURLConnection.getErrorStream();
                final String result = loginResult(inputStream);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(result.equals("FAIL"))
                            msg.setText("로그인에 실패했습니다 ");
                        else
                            msg.setText(result+"님 로그인 성공");
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    private String loginResult(InputStream is) {
        String data ="";
        Scanner s = new Scanner(is);
        while(s.hasNext()) data += s.nextLine();
        s.close();
        return data;

    }
}
