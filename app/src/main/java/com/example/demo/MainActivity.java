package com.example.demo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            TextView tv = new TextView(this);
            tv.setText("Hello World!");
            tv.setTextSize(24);
            setContentView(tv);
            Log.d(TAG, "MainActivity created successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate", e);
            // 创建一个简单的错误视图，避免应用崩溃
            TextView errorView = new TextView(this);
            errorView.setText("Something went wrong: " + e.getMessage());
            setContentView(errorView);
        }
    }
} 