package com.example.demo;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.demo.databinding.ActivityPage4Binding;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import android.Manifest;

public class Page4Activity extends AppCompatActivity implements SensorEventListener {
    private static final String TAG = "Page4Activity";
    private ActivityPage4Binding binding;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor lightSensor;
    private Sensor proximitySensor;
    
    // 手电筒相关
    private CameraManager cameraManager;
    private String cameraId;
    private boolean isFlashlightOn = false;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    
    // NFC相关
    private NfcAdapter nfcAdapter;
    private PendingIntent nfcPendingIntent;
    private IntentFilter[] nfcIntentFilters;
    private String[][] nfcTechLists;
    private boolean isNfcEnabled = false;
    
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
        
        // 初始化相机管理器（用于手电筒）
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraId = cameraManager.getCameraIdList()[0]; // 通常后置摄像头是第一个
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        
        // 初始化NFC
        initNfc();
        
        // 显示设备可用传感器列表
        displayAvailableSensors();
        
        // 设置点击事件
        setupClickListeners();
    }
    
    private void initNfc() {
        // 获取NFC适配器
        NfcManager nfcManager = (NfcManager) getSystemService(Context.NFC_SERVICE);
        nfcAdapter = nfcManager.getDefaultAdapter();
        
        // 检查设备是否支持NFC
        if (nfcAdapter == null) {
            binding.btnNfc.setEnabled(false);
            binding.txtNfcStatus.setText("设备不支持NFC功能");
            return;
        }
        
        // 创建PendingIntent，用于NFC标签检测
        nfcPendingIntent = PendingIntent.getActivity(
                this, 0, 
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 
                PendingIntent.FLAG_MUTABLE);
        
        // 设置Intent过滤器，用于检测各种NFC标签
        IntentFilter ndefFilter = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndefFilter.addDataType("*/*");
        } catch (IntentFilter.MalformedMimeTypeException e) {
            e.printStackTrace();
        }
        
        IntentFilter tagFilter = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter techFilter = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        
        nfcIntentFilters = new IntentFilter[] { ndefFilter, tagFilter, techFilter };
        
        // 设置要处理的NFC技术列表
        nfcTechLists = new String[][] { 
                new String[] { NfcA.class.getName() },
                new String[] { NfcB.class.getName() },
                new String[] { NfcF.class.getName() },
                new String[] { NfcV.class.getName() },
                new String[] { Ndef.class.getName() }
        };
        
        // 更新NFC状态
        updateNfcStatus();
    }
    
    private void updateNfcStatus() {
        if (nfcAdapter == null) {
            binding.txtNfcStatus.setText("设备不支持NFC功能");
            return;
        }
        
        if (!nfcAdapter.isEnabled()) {
            binding.txtNfcStatus.setText("NFC功能已关闭，请在系统设置中开启");
            binding.btnNfc.setText("开启NFC设置");
            isNfcEnabled = false;
        } else {
            binding.txtNfcStatus.setText("NFC功能已开启，等待扫描NFC标签...");
            binding.btnNfc.setText("关闭NFC");
            isNfcEnabled = true;
        }
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
        
        // 手电筒按钮
        binding.btnFlashlight.setOnClickListener(v -> {
            toggleFlashlight();
        });
        
        // NFC按钮
        binding.btnNfc.setOnClickListener(v -> {
            toggleNfc();
        });
    }
    
    private void toggleNfc() {
        if (nfcAdapter == null) {
            Snackbar.make(binding.coordinatorLayout, "设备不支持NFC功能", Snackbar.LENGTH_SHORT).show();
            return;
        }
        
        if (!nfcAdapter.isEnabled()) {
            // 打开系统NFC设置
            startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
        } else {
            // 显示NFC状态
            binding.txtNfcData.setVisibility(
                    binding.txtNfcData.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            
            if (binding.txtNfcData.getVisibility() == View.VISIBLE) {
                Snackbar.make(binding.coordinatorLayout, "NFC监听已开启，请将NFC标签靠近设备背面", Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(binding.coordinatorLayout, "NFC监听已关闭", Snackbar.LENGTH_SHORT).show();
            }
        }
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
    
    // 切换手电筒开关状态
    private void toggleFlashlight() {
        // 检查相机权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) 
                != PackageManager.PERMISSION_GRANTED) {
            // 请求相机权限
            ActivityCompat.requestPermissions(this, 
                    new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
            return;
        }
        
        try {
            if (cameraManager != null && cameraId != null) {
                isFlashlightOn = !isFlashlightOn;
                cameraManager.setTorchMode(cameraId, isFlashlightOn);
                
                // 更新按钮文本
                binding.btnFlashlight.setText(isFlashlightOn ? "关闭手电筒" : "打开手电筒");
                
                // 显示提示
                Snackbar.make(binding.coordinatorLayout, 
                        isFlashlightOn ? "手电筒已开启" : "手电筒已关闭", 
                        Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(binding.coordinatorLayout, 
                        "设备不支持手电筒功能", Snackbar.LENGTH_SHORT).show();
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
            Snackbar.make(binding.coordinatorLayout, 
                    "无法访问手电筒: " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 权限获取成功，重新尝试开启手电筒
                toggleFlashlight();
            } else {
                // 权限被拒绝
                Snackbar.make(binding.coordinatorLayout, 
                        "需要相机权限才能使用手电筒功能", Snackbar.LENGTH_SHORT).show();
            }
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        
        // 重新注册传感器监听器
        if (binding.txtAccelerometerData.getVisibility() == View.VISIBLE) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (binding.txtLightData.getVisibility() == View.VISIBLE) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (binding.txtProximityData.getVisibility() == View.VISIBLE) {
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        
        // 更新NFC状态
        updateNfcStatus();
        
        // 如果NFC功能已开启，启用前台调度系统
        if (nfcAdapter != null && nfcAdapter.isEnabled() && binding.txtNfcData.getVisibility() == View.VISIBLE) {
            nfcAdapter.enableForegroundDispatch(this, nfcPendingIntent, nfcIntentFilters, nfcTechLists);
        }
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        // 停止所有传感器监听
        sensorManager.unregisterListener(this);
        
        // 确保退出页面时关闭手电筒
        if (isFlashlightOn) {
            try {
                cameraManager.setTorchMode(cameraId, false);
                isFlashlightOn = false;
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
        
        // 禁用NFC前台调度
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }
    
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        
        // 处理NFC标签
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction()) ||
                NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction()) ||
                NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            
            // 获取NFC标签
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if (tag != null) {
                // 显示标签信息
                displayNfcTag(tag);
            }
        }
    }
    
    private void displayNfcTag(Tag tag) {
        // 构建标签信息
        StringBuilder info = new StringBuilder();
        info.append("标签ID: ").append(bytesToHex(tag.getId())).append("\n\n");
        
        // 显示支持的技术
        String[] techList = tag.getTechList();
        info.append("支持的技术:\n");
        for (String tech : techList) {
            info.append("- ").append(tech.substring(tech.lastIndexOf(".") + 1)).append("\n");
        }
        
        // 显示标签信息
        binding.txtNfcData.setText(info.toString());
        binding.txtNfcData.setVisibility(View.VISIBLE);
        
        // 显示提示
        Snackbar.make(binding.coordinatorLayout, "已检测到NFC标签", Snackbar.LENGTH_SHORT).show();
    }
    
    // 将字节数组转换为十六进制字符串
    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString();
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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
} 