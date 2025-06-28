package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            // 使用视图绑定
            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            
            // 设置工具栏
            setSupportActionBar(binding.toolbar);
            
            // 设置点击事件
            setupClickListeners();
            
            Log.d(TAG, "MainActivity created successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate", e);
            setContentView(R.layout.activity_main); // 备用方案
            Toast.makeText(this, "初始化错误: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    
    /**
     * 设置点击事件
     */
    private void setupClickListeners() {
        // UI组件展示卡片点击事件
        binding.cardUiComponents.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Page1Activity.class);
            startActivity(intent);
        });
        
        // 列表与数据卡片点击事件
        binding.cardListData.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Page2Activity.class);
            startActivity(intent);
        });
        
        // 关于按钮点击事件
        binding.btnAbout.setOnClickListener(v -> {
            showAboutInfo();
        });
        
        // 浮动操作按钮点击事件
        binding.fab.setOnClickListener(v -> {
            Snackbar.make(v, "这是一个演示用的浮动操作按钮", Snackbar.LENGTH_LONG)
                    .setAction("确定", view -> {
                        Toast.makeText(MainActivity.this, "你点击了确定", Toast.LENGTH_SHORT).show();
                    }).show();
        });
    }
    
    /**
     * 显示关于信息
     */
    private void showAboutInfo() {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("关于")
                .setMessage("这是一个Android Demo应用，用于展示基本的UI组件和功能。\n\n" +
                        "包含Material Design风格的UI设计、页面导航、列表展示等功能，适合初学者学习。\n\n" +
                        "版本: 1.0")
                .setPositiveButton("确定", null)
                .show();
    }
} 