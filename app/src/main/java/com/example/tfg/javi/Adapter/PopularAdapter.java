package com.example.tfg.javi.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.tfg.javi.Activity.DetailActivity;
import com.example.tfg.databinding.ViewholderPupListBinding;
import com.example.tfg.javi.domain.PopularDomain;
import java.util.ArrayList;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.Viewholder>{
    ArrayList<PopularDomain> items;
    Context context;

    public PopularAdapter(ArrayList<PopularDomain> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewholderPupListBinding binding = ViewholderPupListBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false);
        context = parent.getContext();
        return new Viewholder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        ViewholderPupListBinding binding = holder.binding;
        binding.TitleText.setText(items.get(position).getTitleText());
        binding.FeeTxt.setText(items.get(position).getPrice()+"€");
        binding.ScoreTxt.setText("" + items.get(position).getScoreTxt());
        binding.ReviewTxt.setText(""+ items.get(position).getReviewTxt());

        String imageName = items.get(position).getPicUrl();
        @SuppressLint("DiscouragedApi") int imageResId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        Glide.with(context)
                .load(imageResId)
                .transform(new GranularRoundedCorners(30, 30, 0, 0))
                .into(binding.Pic1);

        holder.itemView.setOnClickListener(v ->{
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("object", items.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        ViewholderPupListBinding binding;

        public Viewholder(ViewholderPupListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}