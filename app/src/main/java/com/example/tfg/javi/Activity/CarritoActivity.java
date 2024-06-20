package com.example.tfg.javi.Activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.tfg.javi.Adapter.CarritoAdapter;
import com.example.tfg.javi.Helper.ManagmentCart;
import com.example.tfg.R;
import com.example.tfg.databinding.ActivityCarritoBinding;

public class CarritoActivity extends AppCompatActivity {
    private ManagmentCart managmentCart; // Instancia para manejar el carrito de compras.
    ActivityCarritoBinding binding; // Binding para la actividad
    double percentTax; // Porcentaje de impuestos
    double delivery; // Costo de envío

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCarritoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managmentCart= new ManagmentCart(this); // Inicialización de la gestión del carrito

        setVariable(); // Configura el listener para el botón de retroceso
        initlist(); // Inicializa la lista de productos en el carrito
        calculatorCar(); // Calcula y muestra los totales del carrito
        statusBarColor(); // Configura el color de la barra de estado
    }

    private void statusBarColor(){
        Window window=CarritoActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(CarritoActivity.this, R.color.purple_Dark));
        // Configura el color de la barra de estado en morado oscuro
    }

    private void initlist() {
        if(managmentCart.getListCart().isEmpty()){
            binding.EmptyTxt.setVisibility(View.VISIBLE); // Muestra mensaje de carrito vacío
            binding.ScrollCarrito.setVisibility(View.GONE); // Oculta el RecyclerView de productos
        } else {
            binding.EmptyTxt.setVisibility(View.GONE); // Oculta mensaje de carrito vacío
            binding.ScrollCarrito.setVisibility(View.VISIBLE); // Muestra el RecyclerView de productos
        }

        // Configura el RecyclerView con el adaptador y actualiza la lista de productos
        binding.CarritoView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.CarritoView.setAdapter(new CarritoAdapter(managmentCart.getListCart(), () -> calculatorCar()));
    }

    private void calculatorCar(){
        double percentTax=0.02; // Porcentaje de impuestos fijo
        double delivery=10; // Costo de envío fijo
        percentTax=Math.round(managmentCart.getTotalFee()*percentTax*100)/100; // Calcula el impuesto sobre el total

        // Calcula el total de la compra, el total de productos y actualiza los TextViews correspondientes
        double total = Math.round((managmentCart.getTotalFee()+percentTax+delivery)*100)/100;
        double itemTotal = Math.round(managmentCart.getTotalFee()*100)/100;
        binding.TotalFeeTxt.setText( itemTotal + "€"); // Muestra el total de productos
        binding.TaxTxt.setText(percentTax + "€"); // Muestra el total de impuestos
        binding.DeliveryTxt.setText(delivery + "€"); // Muestra el costo de envío
        binding.TotalTxt.setText(total + "€"); // Muestra el total final
    }

    private void setVariable() {
        binding.BackBtn.setOnClickListener(v -> finish()); // Cierra la actividad al presionar el botón de retroceso
    }
}
