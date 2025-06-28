package com.example.demo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.widget.LinearLayout;
import android.util.Log;

public class Page1Activity extends AppCompatActivity {
    private static final String TAG = "Page1Activity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            // 创建一个线性布局作为根视图
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(50, 50, 50, 50);
            
            // 添加页面标题
            TextView tv = new TextView(this);
            tv.setText("页面1");
            tv.setTextSize(24);
            layout.addView(tv);
            
            // 添加内容描述
            TextView content = new TextView(this);
            content.setText("这是导航到的第一个页面，展示了简单的页面导航功能。");
            content.setTextSize(16);
            content.setPadding(0, 30, 0, 30);
            layout.addView(content);
            
            // 添加返回按钮
            Button btnBack = new Button(this);
            btnBack.setText("返回主页");
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish(); // 关闭当前Activity，返回上一个Activity
                }
            });
            layout.addView(btnBack);
            
            setContentView(layout);
            Log.d(TAG, "Page1Activity created successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate", e);
            TextView errorView = new TextView(this);
            errorView.setText("Something went wrong: " + e.getMessage());
            setContentView(errorView);
        }
    }
} 