package com.example.demo;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.demo.adapter.ListAdapter;
import com.example.demo.databinding.ActivityPage2Binding;
import com.example.demo.model.ListItem;

import java.util.ArrayList;
import java.util.List;

public class Page2Activity extends AppCompatActivity {
    private static final String TAG = "Page2Activity";
    private ActivityPage2Binding binding;
    private ListAdapter adapter;
    private List<ListItem> dataList;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            // 使用视图绑定
            binding = ActivityPage2Binding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            
            // 设置工具栏
            setSupportActionBar(binding.toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }
            
            // 初始化数据和列表
            setupRecyclerView();
            
            // 设置下拉刷新
            binding.swipeRefresh.setOnRefreshListener(this::refreshData);
            
            // 设置浮动按钮
            binding.fab.setOnClickListener(v -> {
                addNewItem();
            });
            
            Log.d(TAG, "Page2Activity created successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate", e);
            setContentView(R.layout.activity_page2); // 备用方案
            Toast.makeText(this, "初始化错误: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    
    /**
     * 初始化RecyclerView
     */
    private void setupRecyclerView() {
        // 准备数据
        dataList = createSampleData();
        
        // 设置布局管理器
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        // 创建并设置适配器
        adapter = new ListAdapter(this, dataList);
        binding.recyclerView.setAdapter(adapter);
        
        // 设置点击监听器
        adapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ListItem item, int position) {
                Toast.makeText(Page2Activity.this, 
                        "点击了: " + item.getTitle(), Toast.LENGTH_SHORT).show();
            }
            
            @Override
            public void onMoreClick(ListItem item, int position, View view) {
                showItemMenu(item, position, view);
            }
        });
    }
    
    /**
     * 创建示例数据
     * @return 数据列表
     */
    private List<ListItem> createSampleData() {
        List<ListItem> items = new ArrayList<>();
        items.add(new ListItem(1, "Android开发基础", "入门教程", 
                "学习Android应用开发的基础知识，包括Activity、Fragment、Intent等。", 
                R.mipmap.ic_launcher));
        items.add(new ListItem(2, "UI设计与实现", "界面开发", 
                "学习Android UI组件的使用，包括布局、控件、样式和主题等。", 
                R.mipmap.ic_launcher));
        items.add(new ListItem(3, "数据存储", "本地数据", 
                "学习Android数据存储方式，包括SharedPreferences、SQLite、文件存储等。", 
                R.mipmap.ic_launcher));
        items.add(new ListItem(4, "网络编程", "API交互", 
                "学习Android网络编程，包括HTTP请求、JSON解析、API调用等。", 
                R.mipmap.ic_launcher));
        items.add(new ListItem(5, "多媒体应用", "音视频处理", 
                "学习Android多媒体应用开发，包括音频播放、视频播放、相机使用等。", 
                R.mipmap.ic_launcher));
        return items;
    }
    
    /**
     * 显示列表项菜单
     * @param item 数据项
     * @param position 位置
     * @param view 视图
     */
    private void showItemMenu(ListItem item, int position, View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.menu_list_item, popup.getMenu());
        popup.setOnMenuItemClickListener(menuItem -> {
            int id = menuItem.getItemId();
            if (id == R.id.action_edit) {
                Toast.makeText(Page2Activity.this, 
                        "编辑: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.action_delete) {
                adapter.removeItem(position);
                Toast.makeText(Page2Activity.this, 
                        "删除: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.action_share) {
                Toast.makeText(Page2Activity.this, 
                        "分享: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });
        popup.show();
    }
    
    /**
     * 刷新数据
     */
    private void refreshData() {
        // 模拟网络请求延迟
        binding.swipeRefresh.postDelayed(() -> {
            // 更新数据
            dataList = createSampleData();
            adapter.updateItems(dataList);
            
            // 停止刷新动画
            binding.swipeRefresh.setRefreshing(false);
            
            Toast.makeText(Page2Activity.this, "数据已刷新", Toast.LENGTH_SHORT).show();
        }, 1000);
    }
    
    /**
     * 添加新项目
     */
    private void addNewItem() {
        int id = dataList.size() + 1;
        ListItem newItem = new ListItem(
                id, 
                "新项目 " + id, 
                "刚刚添加", 
                "这是一个新添加的项目，展示了动态添加列表项的功能。", 
                R.mipmap.ic_launcher);
        adapter.addItem(newItem);
        
        // 滚动到新添加的项目
        binding.recyclerView.smoothScrollToPosition(dataList.size() - 1);
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