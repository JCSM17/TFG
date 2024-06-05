package com.example.tfg.jesus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg.R;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private List<ImageItem> imageItems;
    private LayoutInflater mInflater;

    public ImageAdapter(Context context, List<ImageItem> data) {
        this.mInflater = LayoutInflater.from(context);
        this.imageItems = data;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fragment_image_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageItem item = imageItems.get(position);
        holder.myImageView.setImageResource(item.getImageResId());
        holder.myTextView.setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return imageItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView myImageView;
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myImageView = itemView.findViewById(R.id.image);
            myTextView = itemView.findViewById(R.id.description);
        }
    }
}
