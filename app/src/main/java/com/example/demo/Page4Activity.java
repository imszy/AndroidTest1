package com.example.demo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.databinding.ActivityPage4Binding;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class Page4Activity extends AppCompatActivity implements SensorEventListener {
    private static final String TAG = "Page4Activity";
    private ActivityPage4Binding binding;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor lightSensor;
    private Sensor proximitySensor;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 使用视图绑定
        binding = ActivityPage4Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        // 设置工具栏
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("传感器与设备功能");
        }
        
        // 初始化传感器管理器
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        
        // 获取传感器
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        
        // 显示设备可用传感器列表
        displayAvailableSensors();
        
        // 设置点击事件
        setupClickListeners();
    }
    
    private void displayAvailableSensors() {
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        StringBuilder sensorText = new StringBuilder();
        
        for (Sensor sensor : sensorList) {
            sensorText.append(sensor.getName()).append("\n");
        }
        
        binding.txtAvailableSensors.setText(sensorText.toString());
    }
    
    private void setupClickListeners() {
        // 加速度传感器按钮
        binding.btnAccelerometer.setOnClickListener(v -> {
            toggleSensorMonitoring(accelerometer, binding.txtAccelerometerData);
        });
        
        // 光线传感器按钮
        binding.btnLight.setOnClickListener(v -> {
            toggleSensorMonitoring(lightSensor, binding.txtLightData);
        });
        
        // 距离传感器按钮
        binding.btnProximity.setOnClickListener(v -> {
            toggleSensorMonitoring(proximitySensor, binding.txtProximityData);
        });
        
        // 电池信息按钮
        binding.btnBattery.setOnClickListener(v -> {
            showBatteryInfo();
        });
        
        // 设备信息按钮
        binding.btnDeviceInfo.setOnClickListener(v -> {
            showDeviceInfo();
        });
    }
    
    private void toggleSensorMonitoring(Sensor sensor, TextView dataView) {
        if (sensor == null) {
            Snackbar.make(binding.coordinatorLayout, "设备不支持此传感器", Snackbar.LENGTH_SHORT).show();
            return;
        }
        
        if (dataView.getVisibility() == View.VISIBLE) {
            // 停止监听
            sensorManager.unregisterListener(this, sensor);
            dataView.setVisibility(View.GONE);
            Snackbar.make(binding.coordinatorLayout, "已停止监听", Snackbar.LENGTH_SHORT).show();
        } else {
            // 开始监听
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            dataView.setVisibility(View.VISIBLE);
            Snackbar.make(binding.coordinatorLayout, "开始监听传感器数据", Snackbar.LENGTH_SHORT).show();
        }
    }
    
    private void showBatteryInfo() {
        // 在实际应用中，这里应该使用BatteryManager获取电池信息
        binding.txtBatteryInfo.setVisibility(
                binding.txtBatteryInfo.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }
    
    private void showDeviceInfo() {
        StringBuilder info = new StringBuilder();
        info.append("设备型号: ").append(android.os.Build.MODEL).append("\n");
        info.append("Android版本: ").append(android.os.Build.VERSION.RELEASE).append("\n");
        info.append("API级别: ").append(android.os.Build.VERSION.SDK_INT).append("\n");
        info.append("制造商: ").append(android.os.Build.MANUFACTURER).append("\n");
        info.append("产品名称: ").append(android.os.Build.PRODUCT);
        
        binding.txtDeviceInfo.setText(info.toString());
        binding.txtDeviceInfo.setVisibility(
                binding.txtDeviceInfo.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }
    
    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                binding.txtAccelerometerData.setText(String.format("X: %.2f\nY: %.2f\nZ: %.2f", 
                        event.values[0], event.values[1], event.values[2]));
                break;
                
            case Sensor.TYPE_LIGHT:
                binding.txtLightData.setText(String.format("光线强度: %.2f lux", event.values[0]));
                break;
                
            case Sensor.TYPE_PROXIMITY:
                binding.txtProximityData.setText(String.format("距离: %.2f cm", event.values[0]));
                break;
        }
    }
    
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // 传感器精度变化时的处理
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        // 停止所有传感器监听
        sensorManager.unregisterListener(this);
    }
    
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
} 