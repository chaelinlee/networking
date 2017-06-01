package com.example.leechaelin.networking;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    Button b1;
    EditText e1;
    TextView t1;
    String urlstr="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button)findViewById(R.id.download);
        e1 = (EditText)findViewById(R.id.edittext);
        t1 = (TextView)findViewById(R.id.textview);

    }

    Handler handler = new Handler();

    Thread thread = new Thread(){
        String readData(InputStream is){
            String data = "";
            Scanner s = new Scanner(is);
            while(s.hasNext())
                data += s.nextLine()+"\n";
            s.close();
            return data;
        }

        @Override
        public void run() {
            try{
                URL url = new URL(urlstr);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                if(urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK){

                    final String data = readData(urlConnection.getInputStream());

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            t1.setText(data);
                        }
                    });
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    };
    public void onClick(View v){
        if(v.getId()==R.id.download){
            urlstr = e1.getText().toString();
            thread.start();
        }
    }


}
