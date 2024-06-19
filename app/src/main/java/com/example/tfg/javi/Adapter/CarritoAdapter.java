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
    ArrayList<PopularDomain> items; // Lista de elementos en el carrito
    Context context; // Contexto de la aplicación
    ViewholderCartBinding binding; // Binding para el ViewHolder
    ChangeNumberItemsListener changeNumberItemsListener; // Listener para cambios en el número de elementos
    ManagmentCart managmentCart; // Instancia para manejar el carrito de compras

    public CarritoAdapter(ArrayList<PopularDomain> items, ChangeNumberItemsListener changeNumberItemsListener) {
        this.items = items; // Inicializa la lista de elementos en el carrito
        this.changeNumberItemsListener = changeNumberItemsListener; // Asigna el listener para cambios en el número de elementos
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewholderCartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        context = parent.getContext(); // Obtiene el contexto del padre (RecyclerView)
        managmentCart = new ManagmentCart(context); // Inicializa la gestión del carrito de compras
        return new Viewholder(binding); // Retorna un nuevo ViewHolder con el binding
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        // Configura los datos del producto en el ViewHolder actual
        binding.titleTxt.setText(items.get(position).getTitleText());
        binding.TarifaCadaArticulo.setText(items.get(position).getPrice()+ "€");
        binding.TotalPorArticulo.setText(Math.round(items.get(position).getNumberInCart() * items.get(position).getPrice()) + "€");
        binding.NumArticuloTxt.setText(String.valueOf(items.get(position).getNumberInCart()));

        // Carga la imagen del producto usando Glide y aplica transformaciones de esquinas redondeadas
        int drawableResourced = holder.itemView.getResources().getIdentifier(items.get(position).getPicUrl(), "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(context)
                .load(drawableResourced)
                .transform(new GranularRoundedCorners(30, 30, 0, 0)) // Esquinas redondeadas personalizadas
                .into(binding.Pic);

        // Configura listeners para los botones de incremento y decremento de cantidad
        binding.PlusCarroBtn.setOnClickListener(v -> managmentCart.plusNumberItem(items, position, () -> {
            notifyDataSetChanged(); // Notifica cambios en el dataset
            changeNumberItemsListener.change(); // Llama al listener para actualizar el número de elementos
        }));

        binding.MinusCarroBtn.setOnClickListener(v -> managmentCart.minusNumberItem(items, position, () -> {
            notifyDataSetChanged(); // Notifica cambios en el dataset
            changeNumberItemsListener.change(); // Llama al listener para actualizar el número de elementos
        }));
    }

    @Override
    public int getItemCount() {
        return items.size(); // Retorna la cantidad de elementos en el carrito
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        public Viewholder(ViewholderCartBinding binding) {
            super(binding.getRoot()); // Inicializa el ViewHolder con el binding raíz
        }
    }
}