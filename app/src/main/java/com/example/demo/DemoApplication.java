package com.example.demo;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import androidx.multidex.MultiDex;

import com.example.demo.model.UserManager;

public class DemoApplication extends Application {
    private static final String TAG = "DemoApplication";
    private static DemoApplication instance;

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
        UserManager.getInstance(this);
        
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
     * 获取应用实例
     */
    public static DemoApplication getInstance() {
        return instance;
    }
} 