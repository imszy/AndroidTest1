package com.example.demo;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import androidx.multidex.MultiDex;

import com.example.demo.model.UserManager;

public class DemoApplication extends Application {
    private static final String TAG = "DemoApplication";
    private static DemoApplication instance;
    
    // Demo user credentials
    private static final String DEMO_USERNAME = "demo";
    private static final String DEMO_PASSWORD = "password";
    private static final String DEMO_EMAIL = "demo@example.com";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 支持多 DEX 文件
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        
        // 初始化 UserManager
        UserManager userManager = UserManager.getInstance(this);
        
        // 确保demo账号存在
        ensureDemoUserExists(userManager);
        
        // 设置全局未捕获异常处理器
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                Log.e(TAG, "Uncaught exception", ex);
                // 这里可以添加崩溃日志记录、上报等逻辑
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
        
        Log.d(TAG, "DemoApplication initialized");
    }
    
    /**
     * 确保demo用户存在
     */
    private void ensureDemoUserExists(UserManager userManager) {
        // 尝试使用demo账号登录，如果失败则创建账号
        if (!userManager.login(DEMO_USERNAME, DEMO_PASSWORD)) {
            // 创建demo账号
            userManager.register(DEMO_USERNAME, DEMO_PASSWORD, DEMO_EMAIL);
            Log.d(TAG, "Demo user created");
        }
        // 登出，确保应用启动时用户未登录
        userManager.logout();
    }
    
    /**
     * 获取应用实例
     */
    public static DemoApplication getInstance() {
        return instance;
    }
} 