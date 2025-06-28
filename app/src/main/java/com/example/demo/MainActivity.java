package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.widget.LinearLayout;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            // 创建一个线性布局作为根视图
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(50, 50, 50, 50);
            
            // 添加欢迎文本
            TextView tv = new TextView(this);
            tv.setText("Hello World!");
            tv.setTextSize(24);
            layout.addView(tv);
            
            // 添加间距
            View space = new View(this);
            space.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 50));
            layout.addView(space);
            
            // 添加页面1按钮
            Button btnPage1 = new Button(this);
            btnPage1.setText("跳转到页面1");
            btnPage1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Page1Activity.class);
                    startActivity(intent);
                }
            });
            layout.addView(btnPage1);
            
            // 添加页面2按钮
            Button btnPage2 = new Button(this);
            btnPage2.setText("跳转到页面2");
            btnPage2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Page2Activity.class);
                    startActivity(intent);
                }
            });
            layout.addView(btnPage2);
            
            setContentView(layout);
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