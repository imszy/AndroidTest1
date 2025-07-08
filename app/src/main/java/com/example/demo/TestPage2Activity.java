package com.example.demo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.databinding.ActivityTestPage2Binding;

/**
 * 测试页面2 - 功能测试演示
 */
public class TestPage2Activity extends AppCompatActivity {
    private static final String TAG = "TestPage2Activity";
    private ActivityTestPage2Binding binding;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable delayedTask;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        try {
            // 使用视图绑定
            binding = ActivityTestPage2Binding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            
            // 设置工具栏
            setSupportActionBar(binding.toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle("功能测试页面");
            }
            
            // 设置点击事件
            setupClickListeners();
            
            Log.d(TAG, "TestPage2Activity created successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate", e);
            setContentView(R.layout.activity_test_page2); // 备用方案
            Toast.makeText(this, "初始化错误: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    
    private void setupClickListeners() {
        try {
            // 测试按钮1
            binding.btnTest1.setOnClickListener(v -> {
                try {
                    binding.txtResult.setText("测试按钮1点击成功");
                    Toast.makeText(this, "测试按钮1被点击", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e(TAG, "Error in btnTest1 click", e);
                    Toast.makeText(this, "操作失败: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            
            // 测试按钮2
            binding.btnTest2.setOnClickListener(v -> {
                try {
                    binding.txtResult.setText("测试按钮2点击成功");
                    binding.progressBar.setVisibility(View.VISIBLE);
                    
                    // 3秒后隐藏进度条
                    if (delayedTask != null) {
                        handler.removeCallbacks(delayedTask);
                    }
                    
                    delayedTask = () -> {
                        try {
                            binding.progressBar.setVisibility(View.GONE);
                            binding.txtResult.setText("测试按钮2操作完成");
                        } catch (Exception e) {
                            Log.e(TAG, "Error in delayed task", e);
                        }
                    };
                    
                    handler.postDelayed(delayedTask, 3000);
                } catch (Exception e) {
                    Log.e(TAG, "Error in btnTest2 click", e);
                    Toast.makeText(this, "操作失败: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            
            // 测试按钮3
            binding.btnTest3.setOnClickListener(v -> {
                try {
                    // 切换结果文本的可见性
                    int visibility = binding.txtResult.getVisibility() == View.VISIBLE ? 
                            View.GONE : View.VISIBLE;
                    binding.txtResult.setVisibility(visibility);
                    
                    // 更新按钮文本
                    binding.btnTest3.setText(visibility == View.VISIBLE ? 
                            "隐藏结果" : "显示结果");
                } catch (Exception e) {
                    Log.e(TAG, "Error in btnTest3 click", e);
                    Toast.makeText(this, "操作失败: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Error setting up click listeners", e);
            Toast.makeText(this, "初始化按钮失败: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // 处理返回按钮点击
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    protected void onDestroy() {
        // 移除所有回调，防止内存泄漏
        if (handler != null && delayedTask != null) {
            handler.removeCallbacks(delayedTask);
        }
        super.onDestroy();
        Log.d(TAG, "TestPage2Activity destroyed");
    }
} 