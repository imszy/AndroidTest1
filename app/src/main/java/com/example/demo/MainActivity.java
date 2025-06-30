package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.auth.LoginActivity;
import com.example.demo.auth.ProfileActivity;
import com.example.demo.databinding.ActivityMainBinding;
import com.example.demo.model.User;
import com.example.demo.model.UserManager;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private UserManager userManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            // 使用视图绑定
            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            
            // 设置工具栏
            setSupportActionBar(binding.toolbar);
            
            // 获取UserManager实例
            userManager = UserManager.getInstance(this);
            
            // 设置点击事件
            setupClickListeners();
            
            Log.d(TAG, "MainActivity created successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate", e);
            setContentView(R.layout.activity_main); // 备用方案
            Toast.makeText(this, "初始化错误: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // 刷新菜单以更新登录状态
        invalidateOptionsMenu();
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
        
        // 动画与手势卡片点击事件
        binding.cardAnimations.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Page3Activity.class);
            startActivity(intent);
        });
        
        // 关于按钮点击事件
        binding.btnAbout.setOnClickListener(v -> {
            showAboutInfo();
        });
        
        // 浮动操作按钮点击事件
        binding.fab.setOnClickListener(v -> {
            if (userManager.isLoggedIn()) {
                // 已登录，显示用户信息
                User user = userManager.getCurrentUser();
                Snackbar.make(v, "当前用户: " + user.getNickname(), Snackbar.LENGTH_LONG)
                        .setAction("个人中心", view -> {
                            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                        }).show();
            } else {
                // 未登录，提示登录
                Snackbar.make(v, "您尚未登录，请先登录", Snackbar.LENGTH_LONG)
                        .setAction("登录", view -> {
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        }).show();
            }
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
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        
        // 根据登录状态显示不同菜单项
        MenuItem loginItem = menu.findItem(R.id.action_login);
        MenuItem profileItem = menu.findItem(R.id.action_profile);
        
        if (userManager.isLoggedIn()) {
            // 已登录，显示个人中心选项
            loginItem.setVisible(false);
            profileItem.setVisible(true);
        } else {
            // 未登录，显示登录选项
            loginItem.setVisible(true);
            profileItem.setVisible(false);
        }
        
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        
        if (id == R.id.action_login) {
            startActivity(new Intent(this, LoginActivity.class));
            return true;
        } else if (id == R.id.action_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
} 