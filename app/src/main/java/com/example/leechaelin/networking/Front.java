package com.example.leechaelin.networking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Front extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);
    }

    public void onClick(View v){
        if(v.getId()==R.id.btn1){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }else if(v.getId()==R.id.btn2){
            Intent i = new Intent(this,Main2Activity.class);
            startActivity(i);
        }else if(v.getId()==R.id.btn3){
            Intent ii = new Intent(this,Main3Activity.class);
            startActivity(ii);
        }
    }
}
