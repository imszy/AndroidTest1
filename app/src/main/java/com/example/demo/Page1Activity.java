package com.example.demo;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.databinding.ActivityPage1Binding;
import com.google.android.material.button.MaterialButtonToggleGroup;

public class Page1Activity extends AppCompatActivity {
    private static final String TAG = "Page1Activity";
    private ActivityPage1Binding binding;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            // 使用视图绑定
            binding = ActivityPage1Binding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            
            // 设置工具栏
            setSupportActionBar(binding.toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }
            
            // 初始化UI组件
            setupUIComponents();
            
            Log.d(TAG, "Page1Activity created successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate", e);
            setContentView(R.layout.activity_page1); // 备用方案
            Toast.makeText(this, "初始化错误: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    
    /**
     * 初始化UI组件
     */
    private void setupUIComponents() {
        // 设置下拉菜单
        String[] items = new String[]{"男", "女", "其他"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, items);
        AutoCompleteTextView dropdownText = findViewById(R.id.dropdown_text);
        dropdownText.setAdapter(adapter);
        
        // 设置按钮点击事件
        binding.btnStandard.setOnClickListener(v -> {
            Toast.makeText(this, "点击了标准按钮", Toast.LENGTH_SHORT).show();
        });
        
        binding.btnOutlined.setOnClickListener(v -> {
            Toast.makeText(this, "点击了轮廓按钮", Toast.LENGTH_SHORT).show();
        });
        
        binding.btnText.setOnClickListener(v -> {
            Toast.makeText(this, "点击了文本按钮", Toast.LENGTH_SHORT).show();
        });
        
        binding.btnIcon.setOnClickListener(v -> {
            Toast.makeText(this, "点击了图标按钮", Toast.LENGTH_SHORT).show();
        });
        
        // 设置按钮组选择监听
        binding.toggleButtonGroup.addOnButtonCheckedListener(
                (group, checkedId, isChecked) -> {
                    if (isChecked) {
                        String option = "";
                        if (checkedId == R.id.btn_option1) {
                            option = "选项1";
                        } else if (checkedId == R.id.btn_option2) {
                            option = "选项2";
                        } else if (checkedId == R.id.btn_option3) {
                            option = "选项3";
                        }
                        Toast.makeText(this, "选择了: " + option, Toast.LENGTH_SHORT).show();
                    }
                });
    }
    
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // 处理返回按钮点击
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
} 