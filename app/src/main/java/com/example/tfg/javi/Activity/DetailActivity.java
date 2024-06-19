package com.example.tfg.javi.Activity;

import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.tfg.javi.Helper.ManagmentCart;
import com.example.tfg.R;
import com.example.tfg.databinding.ActivityDetailBinding;
import com.example.tfg.javi.domain.PopularDomain;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding; // Binding para la actividad
    private PopularDomain object; // Objeto que representa el elemento popular
    private int numberOrder = 1; // Número inicial de órdenes
    private ManagmentCart managmentCart; // Instancia para manejar el carrito de compras

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getBundles(); // Obtiene los datos pasados a través de Intent (Bundle)
        managmentCart = new ManagmentCart(this); // Inicializa la gestión del carrito de compras
        statusBarColor(); // Configura el color de la barra de estado
    }

    private void statusBarColor() {
        Window window = DetailActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(DetailActivity.this, R.color.white));
        // Configura el color de la barra de estado en blanco
    }

    private void getBundles() {
        // Obtiene el objeto PopularDomain enviado desde la actividad anterior
        object = (PopularDomain) getIntent().getSerializableExtra("object");

        // Carga la imagen del objeto usando Glide
        int drawableResourceId = this.getResources().getIdentifier(object.getPicUrl(), "drawable", this.getPackageName());
        Glide.with(this)
                .load(drawableResourceId)
                .into(binding.itemPic);

        // Establece el texto del título, precio, descripción, reseña y puntuación del objeto
        binding.TitleTxt.setText(object.getTitleText());
        binding.PriceTxt.setText(object.getPrice() + "€");
        binding.DescripcionTxt.setText(object.getDescripcion());
        binding.ReviewTxt.setText(object.getReviewTxt() + "");
        binding.RatingTxt.setText(object.getScoreTxt() + "");

        // Configura el listener para el botón "Añadir al carrito"
        binding.AniadirCarrito.setOnClickListener(v -> {
            object.setNumberInCart(numberOrder); // Establece la cantidad del objeto en el carrito
            managmentCart.insertFood(object); // Inserta el objeto en el carrito de compras
        });

        // Configura el listener para el botón de retroceso
        binding.backBtn.setOnClickListener(v -> finish());
    }
}
