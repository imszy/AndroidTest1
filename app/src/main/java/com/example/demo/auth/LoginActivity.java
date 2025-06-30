package com.example.demo.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.MainActivity;
import com.example.demo.databinding.ActivityLoginBinding;
import com.example.demo.model.UserManager;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 初始化工具栏
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 获取UserManager实例
        userManager = UserManager.getInstance(this);

        // 如果已经登录，直接进入主页
        if (userManager.isLoggedIn()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        // 设置点击事件
        setupClickListeners();
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
    }

    private void attemptLogin() {
        // 重置错误提示
        binding.tilUsername.setError(null);
        binding.tilPassword.setError(null);

        // 获取输入值
        String username = binding.etUsername.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        // 验证输入
        boolean cancel = false;
        if (TextUtils.isEmpty(username)) {
            binding.tilUsername.setError("请输入用户名");
            cancel = true;
        }

        if (TextUtils.isEmpty(password)) {
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
            Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
} 