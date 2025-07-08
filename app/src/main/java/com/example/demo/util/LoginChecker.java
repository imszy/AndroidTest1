package com.example.demo.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.demo.auth.LoginActivity;
import com.example.demo.model.UserManager;

/**
 * 登录检查工具类
 * 用于检查用户是否已登录，未登录则跳转到登录页面
 */
public class LoginChecker {
    
    /**
     * 检查用户是否已登录
     * @param context 上下文
     * @return 是否已登录
     */
    public static boolean isLoggedIn(Context context) {
        UserManager userManager = UserManager.getInstance(context);
        return userManager.isLoggedIn();
    }
    
    /**
     * 检查用户是否已登录，未登录则跳转到登录页面
     * @param activity 当前活动
     * @return 是否已登录
     */
    public static boolean checkLogin(Activity activity) {
        if (isLoggedIn(activity)) {
            return true;
        } else {
            Toast.makeText(activity, "请先登录", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(activity, LoginActivity.class);
            // 添加标志，表示这是强制登录
            intent.putExtra("REQUIRE_LOGIN", true);
            // 清除任务栈，登录后直接返回到主页面
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intent);
            return false;
        }
    }
} 