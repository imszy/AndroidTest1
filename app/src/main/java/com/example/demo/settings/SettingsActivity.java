package com.example.demo.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.demo.BuildConfig;
import com.example.demo.databinding.ActivitySettingsBinding;
import com.example.demo.model.UserManager;

/**
 * 应用设置页面
 * 允许用户调整应用的各种配置，包括主题、通知、测试模式等
 */
public class SettingsActivity extends AppCompatActivity {
    private ActivitySettingsBinding binding;
    private UserManager userManager;
    private SharedPreferences preferences;
    private static final String PREF_NAME = "settings_prefs";
    private static final String KEY_NIGHT_MODE = "night_mode";
    private static final String KEY_NOTIFICATIONS = "notifications";
    private static final String KEY_LOGGING = "logging";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 初始化工具栏
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 获取UserManager实例
        userManager = UserManager.getInstance(this);
        
        // 获取SharedPreferences
        preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        
        // 加载设置
        loadSettings();
        
        // 设置版本信息
        binding.tvVersion.setText(BuildConfig.VERSION_NAME + " (" + BuildConfig.VERSION_CODE + ")");
        
        // 设置点击事件
        setupClickListeners();
    }
    
    private void loadSettings() {
        // 加载夜间模式设置
        boolean nightMode = preferences.getBoolean(KEY_NIGHT_MODE, false);
        binding.switchNightMode.setChecked(nightMode);
        
        // 加载通知设置
        boolean notifications = preferences.getBoolean(KEY_NOTIFICATIONS, true);
        binding.switchNotifications.setChecked(notifications);
        
        // 加载测试模式设置
        binding.switchTestMode.setChecked(userManager.isTestMode());
        
        // 加载日志设置
        boolean logging = preferences.getBoolean(KEY_LOGGING, false);
        binding.switchLogging.setChecked(logging);
    }
    
    private void setupClickListeners() {
        // 夜间模式点击事件
        binding.layoutNightMode.setOnClickListener(v -> {
            binding.switchNightMode.toggle();
        });
        
        // 通知设置点击事件
        binding.layoutNotifications.setOnClickListener(v -> {
            binding.switchNotifications.toggle();
        });
        
        // 测试模式点击事件
        binding.layoutTestMode.setOnClickListener(v -> {
            binding.switchTestMode.toggle();
        });
        
        // 日志设置点击事件
        binding.layoutLogging.setOnClickListener(v -> {
            binding.switchLogging.toggle();
        });
        
        // 版本信息点击事件
        binding.layoutVersion.setOnClickListener(v -> {
            Toast.makeText(this, "版本: " + BuildConfig.VERSION_NAME, Toast.LENGTH_SHORT).show();
        });
        
        // 检查更新点击事件
        binding.layoutCheckUpdate.setOnClickListener(v -> {
            Toast.makeText(this, "已是最新版本", Toast.LENGTH_SHORT).show();
        });
        
        // 保存按钮点击事件
        binding.btnSaveSettings.setOnClickListener(v -> {
            saveSettings();
            Toast.makeText(this, "设置已保存", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
    
    private void saveSettings() {
        SharedPreferences.Editor editor = preferences.edit();
        
        // 保存夜间模式设置
        boolean nightMode = binding.switchNightMode.isChecked();
        editor.putBoolean(KEY_NIGHT_MODE, nightMode);
        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        
        // 保存通知设置
        boolean notifications = binding.switchNotifications.isChecked();
        editor.putBoolean(KEY_NOTIFICATIONS, notifications);
        
        // 保存测试模式设置
        boolean testMode = binding.switchTestMode.isChecked();
        userManager.setTestMode(testMode);
        
        // 保存日志设置
        boolean logging = binding.switchLogging.isChecked();
        editor.putBoolean(KEY_LOGGING, logging);
        
        editor.apply();
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
} 