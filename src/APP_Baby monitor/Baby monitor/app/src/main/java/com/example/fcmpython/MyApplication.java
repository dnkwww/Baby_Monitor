package com.example.fcmpython;

import android.app.Application;

public class MyApplication extends Application {
    private String currentTime;

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }
}