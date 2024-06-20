package com.example.tfg.jesus;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.tfg.R;
import com.example.tfg.javi.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class NutricionActivity extends AppCompatActivity {

    private LinearLayout desayunoDescription;
    private LinearLayout comidaDescription;
    private LinearLayout meriendaDescription;
    private LinearLayout cenaDescription;

    private ViewPager2 desayunoViewPager;
    private ViewPager2 comidaViewPager;
    private ViewPager2 meriendaViewPager;
    private ViewPager2 cenaViewPager;

    private TextView totalCaloriesTextView;
    private TextView consumedCaloriesTextView;
    private ProgressBar caloriesProgressBar;
    private DatabaseHelper dbHelper;

    public NutricionActivity() {
        // Constructor vacío requerido
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutricion);
        dbHelper = new DatabaseHelper(this);

        // Consulta para imprimir los nombres de las columnas de la tabla userData
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM userData LIMIT 1", null);
        String[] columnNames = cursor.getColumnNames();
        for (String name : columnNames) {
            Log.d("NutricionActivity", "Column name: " + name); // Imprime los nombres de las columnas
        }

        // Inicialización de vistas y asignación de adaptadores para ViewPager2
        desayunoDescription = findViewById(R.id.desayunoDescription);
        comidaDescription = findViewById(R.id.comidaDescription);
        meriendaDescription = findViewById(R.id.meriendaDescription);
        cenaDescription = findViewById(R.id.cenaDescription);

        desayunoViewPager = findViewById(R.id.desayunoViewPager);
        comidaViewPager = findViewById(R.id.comidaViewPager);
        meriendaViewPager = findViewById(R.id.meriendaViewPager);
        cenaViewPager = findViewById(R.id.cenaViewPager);

        totalCaloriesTextView = findViewById(R.id.TotalCaloriasTextView);
        consumedCaloriesTextView = findViewById(R.id.ConsumidasCaloriesTextView);
        caloriesProgressBar = findViewById(R.id.caloriesProgressBar);

        // Obtiene y muestra las calorías totales y consumidas
        int consumedCalories = getConsumedCalories();
        int totalCalories = getTotalCalories();
        totalCaloriesTextView.setText(totalCalories + " kcal");
        consumedCaloriesTextView.setText(consumedCalories + " kcal");

        // Calcula y establece el progreso de la barra de progreso de calorías
        int progress = (int) (((double) consumedCalories / totalCalories) * 100);
        caloriesProgressBar.setProgress(progress);

        // Muestra las calorías restantes y quemadas
        TextView remainingCaloriesTextView = findViewById(R.id.remainingCaloriesTextView);
        TextView burnedCaloriesTextView = findViewById(R.id.burnedCaloriesTextView);
        int remainingCalories = totalCalories - consumedCalories;
        int burnedCalories = getBurnedCalories();
        remainingCaloriesTextView.setText(remainingCalories + "\nRestantes");
        burnedCaloriesTextView.setText(burnedCalories + "\nQuemadas");

        // Configuración de los datos y adaptadores para los ViewPager2
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

        ImageAdapter desayunoAdapter = new ImageAdapter(this, desayunoItems);
        ImageAdapter comidaAdapter = new ImageAdapter(this, comidaItems);
        ImageAdapter meriendaAdapter = new ImageAdapter(this, meriendaItems);
        ImageAdapter cenaAdapter = new ImageAdapter(this, cenaItems);

        desayunoViewPager.setAdapter(desayunoAdapter);
        comidaViewPager.setAdapter(comidaAdapter);
        meriendaViewPager.setAdapter(meriendaAdapter);
        cenaViewPager.setAdapter(cenaAdapter);

        // Configura los listeners para los títulos de las secciones
        findViewById(R.id.desayunoTitle).setOnClickListener(v -> {
            toggleVisibility(desayunoDescription);
            TextView description = desayunoDescription.findViewById(R.id.desayunoDescriptionText);
            int currentItem = desayunoViewPager.getCurrentItem();
            description.setText(desayunoItems.get(currentItem).getDescription());
        });

        findViewById(R.id.comidaTitle).setOnClickListener(v -> {
            toggleVisibility(comidaDescription);
            TextView description = comidaDescription.findViewById(R.id.comidaDescriptionText);
            int currentItem = comidaViewPager.getCurrentItem();
            description.setText(comidaItems.get(currentItem).getDescription());
        });

        findViewById(R.id.meriendaTitle).setOnClickListener(v -> {
            toggleVisibility(meriendaDescription);
            TextView description = meriendaDescription.findViewById(R.id.meriendaDescriptionText);
            int currentItem = meriendaViewPager.getCurrentItem();
            description.setText(meriendaItems.get(currentItem).getDescription());
        });

        findViewById(R.id.cenaTitle).setOnClickListener(v -> {
            toggleVisibility(cenaDescription);
            TextView description = cenaDescription.findViewById(R.id.cenaDescriptionText);
            int currentItem = cenaViewPager.getCurrentItem();
            description.setText(cenaItems.get(currentItem).getDescription());
        });

        // Recupera y muestra las calorías totales del usuario
        totalCaloriesTextView = findViewById(R.id.TotalCaloriasTextView);
        SharedPreferences prefs = getSharedPreferences("tfg_preferences", MODE_PRIVATE);
        int userId = prefs.getInt("userId", 0);
        Log.d("NutricionActivity", "Recuperado userId de preferencias compartidas: " + userId);
        int calories = getUserCalories(String.valueOf(userId));
        totalCaloriesTextView.setText("de " + calories + " kcal");
    }

    private void toggleVisibility(View view) {
        // Método para alternar la visibilidad de una vista entre VISIBLE y GONE
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    private int getUserCalories(String id) {
        // Método para obtener las calorías del usuario desde la base de datos
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT calorias FROM userData WHERE id = ?", new String[]{id});
        int calories = 0;
        if (cursor.moveToFirst()) {
            calories = cursor.getInt(0);
        }
        cursor.close();
        return calories;
    }

    private int getConsumedCalories() {
        // Método temporal para devolver calorías consumidas (sustituir con lógica real).
        return 1769;
    }

    private int getTotalCalories() {
        // Método temporal para devolver calorías totales (sustituir con lógica real)
        return 2100;
    }

    private int getBurnedCalories() {
        // Método temporal para devolver calorías quemadas (sustituir con lógica real)
        return 267;
    }
}