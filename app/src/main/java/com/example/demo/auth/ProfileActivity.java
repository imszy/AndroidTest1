package com.example.demo.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.MainActivity;
import com.example.demo.databinding.ActivityProfileBinding;
import com.example.demo.model.User;
import com.example.demo.model.UserManager;

public class ProfileActivity extends AppCompatActivity {
    private ActivityProfileBinding binding;
    private UserManager userManager;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 初始化工具栏
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 获取UserManager实例
        userManager = UserManager.getInstance(this);
        
        // 检查是否已登录
        if (!userManager.isLoggedIn()) {
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        
        // 获取当前用户
        currentUser = userManager.getCurrentUser();
        
        // 显示用户信息
        displayUserInfo();
        
        // 设置点击事件
        setupClickListeners();
    }
    
    private void displayUserInfo() {
        // 显示用户名和昵称
        binding.tvUsername.setText(currentUser.getUsername());
        binding.tvNickname.setText(currentUser.getNickname());
        
        // 显示邮箱
        binding.tvEmail.setText(currentUser.getEmail());
        
        // 填充编辑表单
        binding.etNickname.setText(currentUser.getNickname());
        binding.etEditEmail.setText(currentUser.getEmail());
    }
    
    private void setupClickListeners() {
        // 保存资料按钮点击事件
        binding.btnSave.setOnClickListener(v -> {
            updateProfile();
        });
        
        // 修改密码按钮点击事件
        binding.btnChangePassword.setOnClickListener(v -> {
            changePassword();
        });
        
        // 退出登录按钮点击事件
        binding.btnLogout.setOnClickListener(v -> {
            confirmLogout();
        });
    }
    
    private void updateProfile() {
        // 重置错误提示
        binding.tilNickname.setError(null);
        binding.tilEditEmail.setError(null);
        
        // 获取输入值
        String nickname = binding.etNickname.getText().toString().trim();
        String email = binding.etEditEmail.getText().toString().trim();
        
        // 验证输入
        boolean cancel = false;
        
        if (TextUtils.isEmpty(nickname)) {
            binding.tilNickname.setError("请输入昵称");
            cancel = true;
        }
        
        if (TextUtils.isEmpty(email)) {
            binding.tilEditEmail.setError("请输入邮箱");
            cancel = true;
        } else if (!isEmailValid(email)) {
            binding.tilEditEmail.setError("邮箱格式不正确");
            cancel = true;
        }
        
        if (cancel) {
            return;
        }
        
        // 更新资料
        if (userManager.updateProfile(nickname, email)) {
            Toast.makeText(this, "资料更新成功", Toast.LENGTH_SHORT).show();
            displayUserInfo(); // 刷新显示
        } else {
            Toast.makeText(this, "资料更新失败", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void changePassword() {
        // 重置错误提示
        binding.tilOldPassword.setError(null);
        binding.tilNewPassword.setError(null);
        binding.tilConfirmNewPassword.setError(null);
        
        // 获取输入值
        String oldPassword = binding.etOldPassword.getText().toString().trim();
        String newPassword = binding.etNewPassword.getText().toString().trim();
        String confirmNewPassword = binding.etConfirmNewPassword.getText().toString().trim();
        
        // 验证输入
        boolean cancel = false;
        
        if (TextUtils.isEmpty(oldPassword)) {
            binding.tilOldPassword.setError("请输入旧密码");
            cancel = true;
        }
        
        if (TextUtils.isEmpty(newPassword)) {
            binding.tilNewPassword.setError("请输入新密码");
            cancel = true;
        } else if (newPassword.length() < 6) {
            binding.tilNewPassword.setError("密码至少需要6个字符");
            cancel = true;
        }
        
        if (TextUtils.isEmpty(confirmNewPassword)) {
            binding.tilConfirmNewPassword.setError("请确认新密码");
            cancel = true;
        } else if (!newPassword.equals(confirmNewPassword)) {
            binding.tilConfirmNewPassword.setError("两次输入的密码不一致");
            cancel = true;
        }
        
        if (cancel) {
            return;
        }
        
        // 修改密码
        if (userManager.changePassword(oldPassword, newPassword)) {
            Toast.makeText(this, "密码修改成功", Toast.LENGTH_SHORT).show();
            // 清空输入框
            binding.etOldPassword.setText("");
            binding.etNewPassword.setText("");
            binding.etConfirmNewPassword.setText("");
        } else {
            binding.tilOldPassword.setError("旧密码不正确");
        }
    }
    
    private void confirmLogout() {
        new AlertDialog.Builder(this)
                .setTitle("退出登录")
                .setMessage("确定要退出登录吗？")
                .setPositiveButton("确定", (dialog, which) -> {
                    userManager.logout();
                    Toast.makeText(ProfileActivity.this, "已退出登录", Toast.LENGTH_SHORT).show();
                    // 返回主页
                    Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("取消", null)
                .show();
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