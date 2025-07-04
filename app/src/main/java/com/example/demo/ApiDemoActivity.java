package com.example.demo;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.demo.adapter.PostAdapter;
import com.example.demo.api.RetrofitClient;
import com.example.demo.databinding.ActivityApiDemoBinding;
import com.example.demo.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiDemoActivity extends AppCompatActivity {
    private static final String TAG = "ApiDemoActivity";
    private ActivityApiDemoBinding binding;
    private PostAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityApiDemoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 设置工具栏
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("API数据演示");
        }

        // 初始化RecyclerView
        setupRecyclerView();

        // 设置下拉刷新
        binding.swipeRefresh.setOnRefreshListener(this::fetchPosts);

        // 加载数据
        fetchPosts();
    }

    private void setupRecyclerView() {
        adapter = new PostAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        // 设置点击事件
        adapter.setOnItemClickListener(post -> {
            Toast.makeText(this, "点击了: " + post.getTitle(), Toast.LENGTH_SHORT).show();
        });
    }

    private void fetchPosts() {
        showLoading();

        RetrofitClient.getInstance()
                .getApiService()
                .getPosts()
                .enqueue(new Callback<List<Post>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
                        binding.swipeRefresh.setRefreshing(false);
                        
                        if (response.isSuccessful() && response.body() != null) {
                            List<Post> posts = response.body();
                            adapter.setPostList(posts);
                            showContent();
                            Log.d(TAG, "成功获取 " + posts.size() + " 条数据");
                        } else {
                            showError();
                            Log.e(TAG, "API响应错误: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable t) {
                        binding.swipeRefresh.setRefreshing(false);
                        showError();
                        Log.e(TAG, "API请求失败", t);
                    }
                });
    }

    private void showLoading() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.tvError.setVisibility(View.GONE);
    }

    private void showContent() {
        binding.progressBar.setVisibility(View.GONE);
        binding.tvError.setVisibility(View.GONE);
    }

    private void showError() {
        binding.progressBar.setVisibility(View.GONE);
        binding.tvError.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
} 