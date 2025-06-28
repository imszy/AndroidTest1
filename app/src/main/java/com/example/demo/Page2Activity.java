package com.example.demo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.widget.LinearLayout;
import android.util.Log;

public class Page2Activity extends AppCompatActivity {
    private static final String TAG = "Page2Activity";
    
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
            tv.setText("页面2");
            tv.setTextSize(24);
            layout.addView(tv);
            
            // 添加内容描述
            TextView content = new TextView(this);
            content.setText("这是导航到的第二个页面，展示了多页面应用的基本结构。");
            content.setTextSize(16);
            content.setPadding(0, 30, 0, 30);
            layout.addView(content);
            
            // 添加一些额外内容，使页面2与页面1有所区别
            TextView extraContent = new TextView(this);
            extraContent.setText("这里可以放置一些特定于页面2的内容，比如图片、列表或其他UI元素。");
            extraContent.setTextSize(14);
            extraContent.setPadding(0, 20, 0, 30);
            layout.addView(extraContent);
            
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
            Log.d(TAG, "Page2Activity created successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate", e);
            TextView errorView = new TextView(this);
            errorView.setText("Something went wrong: " + e.getMessage());
            setContentView(errorView);
        }
    }
} 