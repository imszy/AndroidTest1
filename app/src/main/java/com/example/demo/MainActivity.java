package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.auth.LoginActivity;
import com.example.demo.auth.ProfileActivity;
import com.example.demo.databinding.ActivityMainBinding;
import com.example.demo.model.User;
import com.example.demo.model.UserManager;
import com.example.demo.settings.SettingsActivity;
import com.example.demo.util.LoginChecker;
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
        // 更新登录状态显示
        updateLoginStatusDisplay();
        
        // 检查登录状态，如果未登录则跳转到登录页面
        if (!userManager.isLoggedIn()) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("REQUIRE_LOGIN", true);
            startActivity(intent);
        }
    }
    
    /**
     * 更新登录状态显示
     */
    private void updateLoginStatusDisplay() {
        if (userManager.isLoggedIn()) {
            User user = userManager.getCurrentUser();
            binding.tvLoginStatus.setText("已登录: " + user.getNickname());
            binding.btnLoginAction.setText("退出登录");
        } else {
            binding.tvLoginStatus.setText("未登录");
            binding.btnLoginAction.setText("登录");
        }
    }
    
    /**
     * 设置点击事件
     */
    private void setupClickListeners() {
        // UI组件展示卡片点击事件
        binding.cardUiComponents.setOnClickListener(v -> {
            if (LoginChecker.checkLogin(this)) {
                Intent intent = new Intent(MainActivity.this, Page1Activity.class);
                startActivity(intent);
            }
        });
        
        // 列表与数据卡片点击事件
        binding.cardListData.setOnClickListener(v -> {
            if (LoginChecker.checkLogin(this)) {
                Intent intent = new Intent(MainActivity.this, Page2Activity.class);
                startActivity(intent);
            }
        });
        
        // 动画与手势卡片点击事件
        binding.cardAnimations.setOnClickListener(v -> {
            if (LoginChecker.checkLogin(this)) {
                Intent intent = new Intent(MainActivity.this, Page3Activity.class);
                startActivity(intent);
            }
        });
        
        // 传感器与设备功能卡片点击事件
        binding.cardSensors.setOnClickListener(v -> {
            if (LoginChecker.checkLogin(this)) {
                Intent intent = new Intent(MainActivity.this, Page4Activity.class);
                startActivity(intent);
            }
        });
        
        // API数据演示卡片点击事件
        binding.cardApiDemo.setOnClickListener(v -> {
            if (LoginChecker.checkLogin(this)) {
                Intent intent = new Intent(MainActivity.this, ApiDemoActivity.class);
                startActivity(intent);
            }
        });
        
        // 功能测试页面卡片点击事件
        binding.cardTestPage.setOnClickListener(v -> {
            if (LoginChecker.checkLogin(this)) {
                Intent intent = new Intent(MainActivity.this, TestPage2Activity.class);
                startActivity(intent);
            }
        });
        
        // 设置页面卡片点击事件
        binding.cardSettings.setOnClickListener(v -> {
            if (LoginChecker.checkLogin(this)) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
        
        // 电话页面卡片点击事件
        binding.cardPhone.setOnClickListener(v -> {
            if (LoginChecker.checkLogin(this)) {
                Intent intent = new Intent(MainActivity.this, PhoneActivity.class);
                startActivity(intent);
            }
        });
        
        // 关于按钮点击事件
        binding.btnAbout.setOnClickListener(v -> {
            showAboutInfo();
        });
        
        // 登录/退出按钮点击事件
        binding.btnLoginAction.setOnClickListener(v -> {
            if (userManager.isLoggedIn()) {
                // 已登录，执行退出操作
                userManager.logout();
                updateLoginStatusDisplay();
                Toast.makeText(this, "已退出登录", Toast.LENGTH_SHORT).show();
                // 退出登录后重新检查登录状态
                onResume();
            } else {
                // 未登录，跳转到登录页面
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.putExtra("REQUIRE_LOGIN", true);
                startActivity(intent);
            }
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
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            intent.putExtra("REQUIRE_LOGIN", true);
                            startActivity(intent);
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
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("REQUIRE_LOGIN", true);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
            return true;
        } else if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
} 