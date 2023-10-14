package com.example.fcmpython;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {
    int flag = 0;
    private TextView timeTextView;
    String currentTime = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        /*回到主頁面*/
        Button btn_b =(Button) findViewById(R.id.btn_b);
        btn_b.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity2.this,MainActivity.class);
            startActivity(intent);
            finish();
        });

        /*顯示情況發生的圖片頁面*/
        Button btn_c =(Button) findViewById(R.id.btn_c);
        btn_c.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity2.this,MainActivity8.class);
            startActivity(intent);
        });

        Button btnClear = findViewById(R.id.btn_clear);
        btnClear.setOnClickListener(view -> clearHistory());

        // 初始化通知
        initNotification();

        // 填充 ListView
        fillListView();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivity2.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // 填充 ListView
        fillListView();
    }

    private void initNotification() {
        // ...
    }

    private void fillListView() {
        SharedPreferences sharedPreferences = getSharedPreferences("NotificationHistory", Context.MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll();
        List<Long> timestamps = new ArrayList<>();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String timestampString = entry.getKey();
            try {
                long timestamp = Long.parseLong(timestampString);
                timestamps.add(timestamp);
            } catch (NumberFormatException e) {
                // 鍵值不是一個整數，忽略它
            }
        }
        Collections.sort(timestamps, Collections.reverseOrder());
        List<String> messagesWithTimestamp = new ArrayList<>();
        int maxItemsToShow = 20; // 最多顯示前20筆紀錄
        for (int i = 0; i < Math.min(maxItemsToShow, timestamps.size()); i++) {
            Long timestamp = timestamps.get(i);
            String message = sharedPreferences.getString(String.valueOf(timestamp), "");
            String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date(timestamp));
            String messageWithTimestamp = datetime + "\n" + message;
            messagesWithTimestamp.add(messageWithTimestamp);
        }

// 綁定 ListView
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messagesWithTimestamp));
    }

    private void clearHistory() {
        SharedPreferences sharedPreferences = getSharedPreferences("NotificationHistory", Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
        fillListView();
    }


}

