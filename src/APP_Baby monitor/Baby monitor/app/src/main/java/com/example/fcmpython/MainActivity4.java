package com.example.fcmpython;

import static android.media.MediaPlayer.create;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        //警報聲
        MediaPlayer mediaPlayer = create(this,R.raw.bell);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        //回到主頁面
        Button btn_turn =(Button) findViewById(R.id.btn_turn1);
        btn_turn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity4.this,MainActivity.class);
            startActivity(intent);
            mediaPlayer.stop();
            finish();
        });

        //去最近事件
        Button btn_event =(Button) findViewById(R.id.btn_event1);
        btn_event.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity4.this,MainActivity2.class);
            startActivity(intent);
            mediaPlayer.stop();
            finish();
        });

        /*接收python發送過來的危險狀況*/
    }
}