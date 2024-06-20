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
    ArrayList<PopularDomain> items; // Lista de elementos populares
    Context context; // Contexto de la aplicación

    public PopularAdapter(ArrayList<PopularDomain> items) {
        this.items = items; // Inicializa la lista de elementos populares
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla el layout del ViewHolder utilizando ViewBinding
        ViewholderPupListBinding binding = ViewholderPupListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        context = parent.getContext(); // Obtiene el contexto del padre (RecyclerView)
        return new Viewholder(binding); // Retorna un nuevo ViewHolder con el binding
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        ViewholderPupListBinding binding = holder.binding; // Obtiene el binding del ViewHolder actual

        // Configura los datos del elemento popular en las vistas correspondientes
        binding.TitleText.setText(items.get(position).getTitleText());
        binding.FeeTxt.setText(items.get(position).getPrice()+"€");
        binding.ScoreTxt.setText("" + items.get(position).getScoreTxt());
        binding.ReviewTxt.setText(""+ items.get(position).getReviewTxt());

        // Carga la imagen del elemento popular usando Glide y aplica transformaciones de esquinas redondeadas
        String imageName = items.get(position).getPicUrl();
        @SuppressLint("DiscouragedApi") int imageResId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        Glide.with(context)
                .load(imageResId)
                .transform(new GranularRoundedCorners(30, 30, 0, 0)) // Esquinas redondeadas personalizadas
                .into(binding.Pic1);

        // Configura el listener para el clic en el elemento, abre la actividad DetailActivity con los datos del elemento
        holder.itemView.setOnClickListener(v ->{
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("object", items.get(position)); // Envía el objeto PopularDomain como extra
            context.startActivity(intent); // Inicia la actividad DetailActivity
        });
    }

    @Override
    public int getItemCount() {
        return items.size(); // Retorna la cantidad de elementos en la lista
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        ViewholderPupListBinding binding; // Binding para el ViewHolder.

        public Viewholder(ViewholderPupListBinding binding) {
            super(binding.getRoot()); // Inicializa el ViewHolder con el binding raíz
            this.binding = binding; // Asigna el binding del ViewHolder
        }
    }
}