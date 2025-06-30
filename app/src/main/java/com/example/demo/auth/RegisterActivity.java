package com.example.demo.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.databinding.ActivityRegisterBinding;
import com.example.demo.model.UserManager;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 初始化工具栏
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 获取UserManager实例
        userManager = UserManager.getInstance(this);

        // 设置点击事件
        setupClickListeners();
    }

    private void setupClickListeners() {
        // 注册按钮点击事件
        binding.btnRegister.setOnClickListener(v -> {
            attemptRegister();
        });

        // 登录链接点击事件
        binding.tvLogin.setOnClickListener(v -> {
            finish(); // 返回登录页
        });
    }

    private void attemptRegister() {
        // 重置错误提示
        binding.tilUsername.setError(null);
        binding.tilEmail.setError(null);
        binding.tilPassword.setError(null);
        binding.tilConfirmPassword.setError(null);

        // 获取输入值
        String username = binding.etUsername.getText().toString().trim();
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();
        String confirmPassword = binding.etConfirmPassword.getText().toString().trim();

        // 验证输入
        boolean cancel = false;

        if (TextUtils.isEmpty(username)) {
            binding.tilUsername.setError("请输入用户名");
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            binding.tilEmail.setError("请输入邮箱");
            cancel = true;
        } else if (!isEmailValid(email)) {
            binding.tilEmail.setError("邮箱格式不正确");
            cancel = true;
        }

        if (TextUtils.isEmpty(password)) {
            binding.tilPassword.setError("请输入密码");
            cancel = true;
        } else if (password.length() < 6) {
            binding.tilPassword.setError("密码至少需要6个字符");
            cancel = true;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            binding.tilConfirmPassword.setError("请确认密码");
            cancel = true;
        } else if (!password.equals(confirmPassword)) {
            binding.tilConfirmPassword.setError("两次输入的密码不一致");
            cancel = true;
        }

        if (cancel) {
            return;
        }

        // 尝试注册
        if (userManager.register(username, password, email)) {
            Toast.makeText(this, "注册成功，请登录", Toast.LENGTH_SHORT).show();
            // 返回登录页
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            binding.tilUsername.setError("用户名已存在");
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@") && email.contains(".");
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