package com.example.demo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.demo.databinding.ActivityPhoneBinding;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PhoneActivity extends AppCompatActivity {
    private static final String TAG = "PhoneActivity";
    private ActivityPhoneBinding binding;
    private static final int CALL_PHONE_PERMISSION_REQUEST_CODE = 101;
    
    // 模拟通话记录列表
    private List<String> callHistory = new ArrayList<>();
    
    // 预设电话号码
    private static final String EMERGENCY_NUMBER = "110";
    private static final String CONTACT1_NUMBER = "13800138000";
    private static final String CONTACT2_NUMBER = "13900139000";
    private static final String CONTACT3_NUMBER = "13700137000";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 使用视图绑定
        binding = ActivityPhoneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        // 设置工具栏
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("拨打电话");
        }
        
        // 设置点击事件
        setupClickListeners();
    }
    
    private void setupClickListeners() {
        // 拨打电话按钮
        binding.btnCall.setOnClickListener(v -> {
            String phoneNumber = binding.phoneInput.getText().toString().trim();
            if (!TextUtils.isEmpty(phoneNumber)) {
                makePhoneCall(phoneNumber);
            } else {
                Snackbar.make(binding.coordinatorLayout, "请输入电话号码", Snackbar.LENGTH_SHORT).show();
            }
        });
        
        // 紧急电话按钮
        binding.btnEmergency.setOnClickListener(v -> {
            binding.phoneInput.setText(EMERGENCY_NUMBER);
            Snackbar.make(binding.coordinatorLayout, "已填入紧急电话号码", Snackbar.LENGTH_SHORT).show();
        });
        
        // 联系人1按钮
        binding.btnContact1.setOnClickListener(v -> {
            binding.phoneInput.setText(CONTACT1_NUMBER);
            Snackbar.make(binding.coordinatorLayout, "已填入联系人1号码", Snackbar.LENGTH_SHORT).show();
        });
        
        // 联系人2按钮
        binding.btnContact2.setOnClickListener(v -> {
            binding.phoneInput.setText(CONTACT2_NUMBER);
            Snackbar.make(binding.coordinatorLayout, "已填入联系人2号码", Snackbar.LENGTH_SHORT).show();
        });
        
        // 联系人3按钮
        binding.btnContact3.setOnClickListener(v -> {
            binding.phoneInput.setText(CONTACT3_NUMBER);
            Snackbar.make(binding.coordinatorLayout, "已填入联系人3号码", Snackbar.LENGTH_SHORT).show();
        });
    }
    
    private void makePhoneCall(String phoneNumber) {
        // 检查拨打电话权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) 
                != PackageManager.PERMISSION_GRANTED) {
            // 请求权限
            ActivityCompat.requestPermissions(this, 
                    new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_PERMISSION_REQUEST_CODE);
            return;
        }
        
        // 执行拨打电话操作
        try {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(callIntent);
            
            // 添加到通话记录
            addCallToHistory(phoneNumber);
        } catch (Exception e) {
            e.printStackTrace();
            Snackbar.make(binding.coordinatorLayout, 
                    "拨打电话失败: " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }
    
    private void addCallToHistory(String phoneNumber) {
        // 获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentTime = sdf.format(new Date());
        
        // 添加到通话记录
        String record = currentTime + " - " + phoneNumber;
        callHistory.add(0, record); // 添加到列表开头
        
        // 最多显示10条记录
        if (callHistory.size() > 10) {
            callHistory.remove(callHistory.size() - 1);
        }
        
        // 更新UI
        updateCallHistoryUI();
    }
    
    private void updateCallHistoryUI() {
        if (callHistory.isEmpty()) {
            binding.txtCallHistory.setText("暂无通话记录");
        } else {
            StringBuilder historyText = new StringBuilder();
            for (String record : callHistory) {
                historyText.append(record).append("\n\n");
            }
            binding.txtCallHistory.setText(historyText.toString().trim());
        }
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_PHONE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 权限获取成功，重新尝试拨打电话
                String phoneNumber = binding.phoneInput.getText().toString().trim();
                if (!TextUtils.isEmpty(phoneNumber)) {
                    makePhoneCall(phoneNumber);
                }
            } else {
                // 权限被拒绝
                Toast.makeText(this, "需要拨打电话权限才能使用此功能", Toast.LENGTH_SHORT).show();
            }
        }
    }
    
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
} 