package com.example.tfg.jc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tfg.R;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private int[] images; // Arreglo que contiene los recursos de las imágenes a mostrar
    private LayoutInflater inflater; // Objeto LayoutInflater para inflar el diseño de cada elemento

    public ImageAdapter(int[] images, Context context) {
        this.images = images; // Inicializa el arreglo de imágenes con los recursos proporcionados
        this.inflater = LayoutInflater.from(context); // Inicializa el LayoutInflater desde el contexto dado
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.carousel_item, parent, false); // Infla el diseño de cada elemento de la lista
        return new ImageViewHolder(view); // Devuelve una nueva instancia de ImageViewHolder con la vista inflada
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.imageView.setImageResource(images[position]); // Establece la imagen correspondiente en ImageView según la posición
    }

    @Override
    public int getItemCount() {
        return images.length; // Devuelve la cantidad total de elementos en el arreglo de imágenes
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView; // ImageView que muestra la imagen en cada elemento de la lista

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.carousel_image); // Obtiene la referencia de ImageView desde el diseño
        }
    }
}
