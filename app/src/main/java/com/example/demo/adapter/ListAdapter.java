package com.example.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demo.R;
import com.example.demo.model.ListItem;

import java.util.List;

/**
 * 列表适配器
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<ListItem> items;
    private Context context;
    private OnItemClickListener listener;

    /**
     * 列表项点击监听器接口
     */
    public interface OnItemClickListener {
        void onItemClick(ListItem item, int position);
        void onMoreClick(ListItem item, int position, View view);
    }

    /**
     * 构造函数
     * @param context 上下文
     * @param items 数据列表
     */
    public ListAdapter(Context context, List<ListItem> items) {
        this.context = context;
        this.items = items;
    }

    /**
     * 设置点击监听器
     * @param listener 监听器
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * 更新数据
     * @param newItems 新数据列表
     */
    public void updateItems(List<ListItem> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    /**
     * 添加数据项
     * @param item 新数据项
     */
    public void addItem(ListItem item) {
        this.items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    /**
     * 删除数据项
     * @param position 位置
     */
    public void removeItem(int position) {
        if (position >= 0 && position < items.size()) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItem item = items.get(position);
        
        // 设置数据
        holder.titleText.setText(item.getTitle());
        holder.subtitleText.setText(item.getSubtitle());
        holder.descriptionText.setText(item.getDescription());
        
        // 使用Glide加载图片
        Glide.with(context)
                .load(item.getImageResId())
                .circleCrop()
                .into(holder.imageView);
        
        // 设置点击事件
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item, holder.getAdapterPosition());
            }
        });
        
        holder.moreButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onMoreClick(item, holder.getAdapterPosition(), v);
            } else {
                // 默认行为
                Toast.makeText(context, "点击了: " + item.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * ViewHolder类
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView titleText;
        public TextView subtitleText;
        public TextView descriptionText;
        public ImageButton moreButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
            titleText = itemView.findViewById(R.id.item_title);
            subtitleText = itemView.findViewById(R.id.item_subtitle);
            descriptionText = itemView.findViewById(R.id.item_description);
            moreButton = itemView.findViewById(R.id.item_more);
        }
    }
} 