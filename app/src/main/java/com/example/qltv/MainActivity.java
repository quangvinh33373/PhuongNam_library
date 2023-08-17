package com.example.qltv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnStart;
    boolean btnClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStart = findViewById(R.id.btnStart);

        Intent intent = new Intent(MainActivity.this, DangNhap.class);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnClick = true;
                startActivity(intent);
            }
        });

        Thread timer = new Thread() {
            public void run(){
                try {
                    sleep(3000);
                    if (!btnClick){
                        startActivity(intent);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        timer.start();
    }
}