package com.example.tfg.jesus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.tfg.R;

import java.util.ArrayList;
import java.util.List;

public class NutricionFragment extends Fragment {

    private LinearLayout desayunoDescription;
    private LinearLayout comidaDescription;
    private LinearLayout meriendaDescription;
    private LinearLayout cenaDescription;

    private ViewPager2 desayunoViewPager;
    private ViewPager2 comidaViewPager;
    private ViewPager2 meriendaViewPager;
    private ViewPager2 cenaViewPager;

    private TextView consumedCaloriesTextView;
    private TextView totalCaloriesTextView;
    private ProgressBar caloriesProgressBar;

    public NutricionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nutricion, container, false);

        desayunoDescription = view.findViewById(R.id.desayunoDescription);
        comidaDescription = view.findViewById(R.id.comidaDescription);
        meriendaDescription = view.findViewById(R.id.meriendaDescription);
        cenaDescription = view.findViewById(R.id.cenaDescription);

        desayunoViewPager = view.findViewById(R.id.desayunoViewPager);
        comidaViewPager = view.findViewById(R.id.comidaViewPager);
        meriendaViewPager = view.findViewById(R.id.meriendaViewPager);
        cenaViewPager = view.findViewById(R.id.cenaViewPager);

        consumedCaloriesTextView = view.findViewById(R.id.consumedCaloriesTextView);
        totalCaloriesTextView = view.findViewById(R.id.totalCaloriesTextView);
        caloriesProgressBar = view.findViewById(R.id.caloriesProgressBar);

        // Assuming you have methods to get the actual values
        int consumedCalories = getConsumedCalories();
        int totalCalories = getTotalCalories();
        int burnedCalories = getBurnedCalories();

        consumedCaloriesTextView.setText(consumedCalories + " kcal");
        totalCaloriesTextView.setText("de " + totalCalories + " kcal");

        int remainingCalories = totalCalories - consumedCalories;
        int progress = (int) (((double) consumedCalories / totalCalories) * 100);

        caloriesProgressBar.setProgress(progress);

        TextView remainingCaloriesTextView = view.findViewById(R.id.remainingCaloriesTextView);
        TextView burnedCaloriesTextView = view.findViewById(R.id.burnedCaloriesTextView);

        remainingCaloriesTextView.setText(remainingCalories + "\nRestantes");
        burnedCaloriesTextView.setText(burnedCalories + "\nQuemadas");

        List<ImageItem> desayunoItems = new ArrayList<>();
        desayunoItems.add(new ImageItem(R.drawable.desayuno1avena, "Avena con Frutas y Nueces: 350 kcal, 6g de grasa, 58g de carbohidratos, 10g de proteína"));
        desayunoItems.add(new ImageItem(R.drawable.desayuno2yogurt, "Yogur Griego con Miel y Granola: 250 kcal, 8g de grasa, 28g de carbohidratos, 14g de proteína"));
        desayunoItems.add(new ImageItem(R.drawable.desayuno3tostada, "Tostadas de Aguacate con Huevo: 300 kcal, 18g de grasa, 30g de carbohidratos, 12g de proteína"));

        List<ImageItem> comidaItems = new ArrayList<>();
        comidaItems.add(new ImageItem(R.drawable.comida1ensalada, "Ensalada de Quinoa y Pollo: 450 kcal, 20g de grasa, 40g de carbohidratos, 30g de proteína"));
        comidaItems.add(new ImageItem(R.drawable.comida2pasta, "Pasta Integral con Vegetales y Pesto: 400 kcal, 14g de grasa, 60g de carbohidratos, 12g de proteína"));
        comidaItems.add(new ImageItem(R.drawable.comida3salmon, "Salmón a la Plancha con Espárragos: 500 kcal, 25g de grasa, 10g de carbohidratos, 45g de proteína"));

        List<ImageItem> meriendaItems = new ArrayList<>();
        meriendaItems.add(new ImageItem(R.drawable.merienda1batido, "Batido Verde: 200 kcal, 1g de grasa, 45g de carbohidratos, 5g de proteína"));
        meriendaItems.add(new ImageItem(R.drawable.merienda2humus, "Hummus con Palitos de Verdura: 180 kcal, 10g de grasa, 18g de carbohidratos, 6g de proteína"));
        meriendaItems.add(new ImageItem(R.drawable.merienda3frutas, "Frutas Frescas con Yogur: 150 kcal, 3g de grasa, 28g de carbohidratos, 6g de proteína"));

        List<ImageItem> cenaItems = new ArrayList<>();
        cenaItems.add(new ImageItem(R.drawable.cena1lentejas, "Sopa de Lentejas: 350 kcal, 10g de grasa, 45g de carbohidratos, 18g de proteína"));
        cenaItems.add(new ImageItem(R.drawable.cena3tacos, "Tacos de Pescado: 400 kcal, 18g de grasa, 30g de carbohidratos, 28g de proteína"));
        cenaItems.add(new ImageItem(R.drawable.cena3garbanzos, "Ensalada de Garbanzos: 300 kcal, 14g de grasa, 36g de carbohidratos, 10g de proteína"));

        ImageAdapter desayunoAdapter = new ImageAdapter(getContext(), desayunoItems);
        ImageAdapter comidaAdapter = new ImageAdapter(getContext(), comidaItems);
        ImageAdapter meriendaAdapter = new ImageAdapter(getContext(), meriendaItems);
        ImageAdapter cenaAdapter = new ImageAdapter(getContext(), cenaItems);

        desayunoViewPager.setAdapter(desayunoAdapter);
        comidaViewPager.setAdapter(comidaAdapter);
        meriendaViewPager.setAdapter(meriendaAdapter);
        cenaViewPager.setAdapter(cenaAdapter);

        view.findViewById(R.id.desayunoTitle).setOnClickListener(v -> toggleVisibility(desayunoDescription));
        view.findViewById(R.id.comidaTitle).setOnClickListener(v -> toggleVisibility(comidaDescription));
        view.findViewById(R.id.meriendaTitle).setOnClickListener(v -> toggleVisibility(meriendaDescription));
        view.findViewById(R.id.cenaTitle).setOnClickListener(v -> toggleVisibility(cenaDescription));

        return view;
    }

    private void toggleVisibility(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    private int getConsumedCalories() {
        // Replace this with actual logic to retrieve consumed calories
        return 1769;
    }

    private int getTotalCalories() {
        // Replace this with actual logic to retrieve total calories
        return 2100;
    }

    private int getBurnedCalories() {
        // Replace this with actual logic to retrieve burned calories
        return 267;
    }
}
