package com.example.tfg.javi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.tfg.javi.Helper.ChangeNumberItemsListener;
import com.example.tfg.javi.Helper.ManagmentCart;
import com.example.tfg.databinding.ViewholderCartBinding;
import com.example.tfg.javi.domain.PopularDomain;

import java.util.ArrayList;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.Viewholder>{
    ArrayList<PopularDomain> items;
    Context context;
    ViewholderCartBinding binding;
    ChangeNumberItemsListener changeNumberItemsListener;
    ManagmentCart managmentCart;

    public CarritoAdapter(ArrayList<PopularDomain> items, ChangeNumberItemsListener changeNumberItemsListener) {
        this.items = items;
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=ViewholderCartBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false);
        context = parent.getContext();
        managmentCart= new ManagmentCart(context);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        binding.titleTxt.setText(items.get(position).getTitleText());
        binding.TarifaCadaArticulo.setText(items.get(position).getPrice()+ "€");
        binding.TotalPorArticulo.setText(Math.round(items.get(position).getNumberInCart()*items.get(position).getPrice())+ "€");
        binding.NumArticuloTxt.setText(String.valueOf(items.get(position).getNumberInCart()));

        int drawableResourced =holder.itemView.getResources().getIdentifier(items.get(position).getPicUrl()
                , "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(context)
                .load(drawableResourced)
                .transform(new GranularRoundedCorners(30, 30, 0, 0))
                .into(binding.Pic);

        binding.PlusCarroBtn.setOnClickListener(v -> managmentCart.plusNumberItem(items, position, () -> {
            notifyDataSetChanged();
            changeNumberItemsListener.change();
        }));

        binding.MinusCarroBtn.setOnClickListener(v -> managmentCart.minusNumberItem(items, position, () -> {
            notifyDataSetChanged();
            changeNumberItemsListener.change();
        }));
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        public Viewholder(ViewholderCartBinding binding) {
            super(binding.getRoot());
        }
    }
}