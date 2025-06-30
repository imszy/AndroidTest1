package com.example.demo;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import com.example.demo.databinding.ActivityPage3Binding;
import com.google.android.material.snackbar.Snackbar;

public class Page3Activity extends AppCompatActivity {
    private static final String TAG = "Page3Activity";
    private ActivityPage3Binding binding;
    private GestureDetectorCompat gestureDetector;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 使用视图绑定
        binding = ActivityPage3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        // 设置工具栏
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("动画与手势");
        }
        
        // 设置点击事件
        setupClickListeners();
    }
    
    private void setupClickListeners() {
        // 缩放动画按钮
        binding.btnScale.setOnClickListener(v -> {
            Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale);
            binding.animationTarget.startAnimation(scaleAnimation);
            showMessage("缩放动画");
        });
        
        // 旋转动画按钮
        binding.btnRotate.setOnClickListener(v -> {
            Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
            binding.animationTarget.startAnimation(rotateAnimation);
            showMessage("旋转动画");
        });
        
        // 平移动画按钮
        binding.btnTranslate.setOnClickListener(v -> {
            Animation translateAnimation = AnimationUtils.loadAnimation(this, R.anim.translate);
            binding.animationTarget.startAnimation(translateAnimation);
            showMessage("平移动画");
        });
        
        // 渐变动画按钮
        binding.btnFade.setOnClickListener(v -> {
            Animation fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.fade);
            binding.animationTarget.startAnimation(fadeAnimation);
            showMessage("渐变动画");
        });
        
        // 组合动画按钮
        binding.btnCombination.setOnClickListener(v -> {
            Animation comboAnimation = AnimationUtils.loadAnimation(this, R.anim.combination);
            binding.animationTarget.startAnimation(comboAnimation);
            showMessage("组合动画");
        });
        
        // 属性动画示例
        binding.btnProperty.setOnClickListener(v -> {
            // 使用ObjectAnimator进行属性动画
            binding.animationTarget.animate()
                .scaleX(1.5f)
                .scaleY(1.5f)
                .rotation(360)
                .alpha(0.5f)
                .setDuration(1000)
                .withEndAction(() -> {
                    // 动画结束后恢复原状
                    binding.animationTarget.animate()
                        .scaleX(1.0f)
                        .scaleY(1.0f)
                        .rotation(0)
                        .alpha(1.0f)
                        .setDuration(1000)
                        .start();
                })
                .start();
            
            showMessage("属性动画");
        });
    }
    
    private void showMessage(String message) {
        Snackbar.make(binding.coordinatorLayout, message, Snackbar.LENGTH_SHORT).show();
    }
    
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
} 