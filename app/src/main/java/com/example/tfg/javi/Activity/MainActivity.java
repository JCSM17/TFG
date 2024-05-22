package com.example.tfg.javi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.tfg.javi.Adapter.PopularAdapter;
import com.example.tfg.R;
import com.example.tfg.databinding.ActivityMainBinding;
import com.example.tfg.javi.domain.PopularDomain;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        statusBarColor();
        initRecyclerView();
        bottomNavigation();
    }

    private void bottomNavigation() {
        binding.CarritoBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CarritoActivity.class)));
    }

    private void statusBarColor(){
        Window window=MainActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.purple_Dark));
    }

    private void initRecyclerView(){
        ArrayList<PopularDomain> items= new ArrayList<>();
        items.add(new PopularDomain("Whey Protein Xtreme", "item_1", 30, 5, 80, "Gold Standard 100% Whey es una mezcla de proteínas de suero de leche que contiene aislado de proteína de suero WPI, Concentrado de Proteína de Suero de Leche e Hidrolizado de Aislado de Suero con Chocolate. Esta mezcla de tres tipos de proteína, hacen de 100% Whey Gold Standard una de las formulaciones de proteína más puras, versátiles y preferidas por todo tipo de atleta en el mundo." +
                "24 gramos de proteína (80%). 5,5 gramos de BCAA'S. 4 gramos de GlutaGLM y Ácido Glutámico. 1,1 gramos de carbohidrato. Repotenciado con enzimas digestivas (Lactasa). Endulzado con Sucralosa"));

        items.add(new PopularDomain("Guantes Gimnasio", "item_2", 18, 4.8, 25, "Los guantes crossfit BULLSTEP, con acolchonamiento extra y las capas de piel reforzada, cubren toda la superficie de la palma de la mano ofreciendo la máxima protección contra callos y heridas, amortiguación y gran confort. Cara interior reforzada con silicona para un grip antideslizante. Ideales para mejorar rendimiento y eficiencia en todos los ejercicios de barras, anillas, kettle bells, entrenamiento con cuerdas, levantamiento de pesas, cross training y sentadillas."));

        items.add(new PopularDomain("Creatina en Polvo", "item_3", 23, 4.9, 23, "Creatina en Polvo Micronizada Optimum Nutrition, Polvo de Monohidrato de Creatina 100 % Puro para Rendimiento y Potencia Muscular, Sin Sabor, 93 Porciones, 317 g. Para un delicioso batido agrega una medida rasa a 240 ml de agua fría, un zumo o tu batido pre o post entrenamiento, mezcla y disfruta. Sin sabor, perfecto para apilar, este polvo se mezcla fácilmente y, a diferencia de muchos otros polvos de creatina, no tiene sabor ni textura arenosa."));

        binding.PopularView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.PopularView.setAdapter(new PopularAdapter(items));
    }
}