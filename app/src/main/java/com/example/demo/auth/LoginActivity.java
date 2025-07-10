package com.example.demo.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.MainActivity;
import com.example.demo.databinding.ActivityLoginBinding;
import com.example.demo.model.UserManager;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private UserManager userManager;
    private boolean requireLogin = false;
    
    // Demo credentials
    private static final String DEMO_USERNAME = "demo";
    private static final String DEMO_PASSWORD = "password";
    private static final String DEMO_EMAIL = "demo@example.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 检查是否需要强制登录
        requireLogin = getIntent().getBooleanExtra("REQUIRE_LOGIN", false);

        // 初始化工具栏
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(!requireLogin);

        // 获取UserManager实例
        userManager = UserManager.getInstance(this);

        // 如果已经登录，直接进入主页
        if (userManager.isLoggedIn()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        // 确保demo账号存在
        ensureDemoAccountExists();
        
        // 根据测试模式设置UI
        updateUIForTestMode();
        
        // 设置点击事件
        setupClickListeners();
        
        // 显示demo账号信息
        showDemoCredentials();
        
        // 如果需要强制登录，显示提示并处理返回按钮
        if (requireLogin) {
            Snackbar.make(binding.getRoot(), "请登录后继续使用应用", Snackbar.LENGTH_LONG).show();
            
            // 处理返回按钮
            getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    showExitConfirmDialog();
                }
            });
        }
    }

    private void updateUIForTestMode() {
        // 如果是测试模式，隐藏密码输入框并更新提示
        if (userManager.isTestMode()) {
            binding.tilPassword.setVisibility(View.GONE);
            binding.testModeIndicator.setVisibility(View.VISIBLE);
            binding.btnDemoLogin.setText("填充演示用户名");
            binding.tvDemoPassword.setVisibility(View.GONE);
        } else {
            binding.tilPassword.setVisibility(View.VISIBLE);
            binding.testModeIndicator.setVisibility(View.GONE);
            binding.btnDemoLogin.setText("使用演示账号登录");
            binding.tvDemoPassword.setVisibility(View.VISIBLE);
        }
    }

    private void setupClickListeners() {
        // 登录按钮点击事件
        binding.btnLogin.setOnClickListener(v -> {
            attemptLogin();
        });

        // 注册链接点击事件
        binding.tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
        
        // 自动填充demo账号按钮
        binding.btnDemoLogin.setOnClickListener(v -> {
            binding.etUsername.setText(DEMO_USERNAME);
            if (!userManager.isTestMode()) {
                binding.etPassword.setText(DEMO_PASSWORD);
            }
        });
    }
    
    private void ensureDemoAccountExists() {
        // 如果demo账号不存在，创建一个
        if (!userManager.login(DEMO_USERNAME, DEMO_PASSWORD)) {
            userManager.register(DEMO_USERNAME, DEMO_PASSWORD, DEMO_EMAIL);
        }
    }
    
    private void showDemoCredentials() {
        String message = userManager.isTestMode() ? 
                "测试模式：只需输入用户名即可登录" : 
                "使用演示账号登录体验应用功能";
                
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG)
                .setAction("填充", v -> {
                    binding.etUsername.setText(DEMO_USERNAME);
                    if (!userManager.isTestMode()) {
                        binding.etPassword.setText(DEMO_PASSWORD);
                    }
                })
                .show();
    }

    private void attemptLogin() {
        // 重置错误提示
        binding.tilUsername.setError(null);
        if (!userManager.isTestMode()) {
            binding.tilPassword.setError(null);
        }

        // 获取输入值
        String username = binding.etUsername.getText().toString().trim();
        String password = userManager.isTestMode() ? "" : binding.etPassword.getText().toString().trim();

        // 验证输入
        boolean cancel = false;
        if (TextUtils.isEmpty(username)) {
            binding.tilUsername.setError("请输入用户名");
            cancel = true;
        }

        if (!userManager.isTestMode() && TextUtils.isEmpty(password)) {
            binding.tilPassword.setError("请输入密码");
            cancel = true;
        }

        if (cancel) {
            return;
        }

        // 尝试登录
        if (userManager.login(username, password)) {
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            if (userManager.isTestMode()) {
                Toast.makeText(this, "用户名不存在", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
            }
        }
    }
    
    /**
     * 显示退出确认对话框
     */
    private void showExitConfirmDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("退出应用")
                .setMessage("应用需要登录才能使用，确定要退出吗？")
                .setPositiveButton("退出", (dialog, which) -> {
                    // 结束所有Activity
                    finishAffinity();
                })
                .setNegativeButton("取消", null)
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (requireLogin) {
                showExitConfirmDialog();
            } else {
                finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
} 