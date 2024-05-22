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
    private ManagmentCart managmentCart;
    ActivityCarritoBinding binding;
    double percentTax;
    double delivery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCarritoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managmentCart= new ManagmentCart(this);

        setVariable();
        initlist();
        calculatorCar();
        statusBarColor();
    }

    private void statusBarColor(){
        Window window=CarritoActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(CarritoActivity.this, R.color.purple_Dark));
    }
    private void initlist() {
        if(managmentCart.getListCart().isEmpty()){
            binding.EmptyTxt.setVisibility(View.VISIBLE);
            binding.ScrollCarrito.setVisibility(View.GONE);
        }else{
            binding.EmptyTxt.setVisibility(View.GONE);
            binding.ScrollCarrito.setVisibility(View.VISIBLE);
        }

        binding.CarritoView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.CarritoView.setAdapter(new CarritoAdapter(managmentCart.getListCart(), () -> calculatorCar()));
    }
    private void calculatorCar(){
        double percentTax=0.02;
        double delivery=10;
        percentTax=Math.round(managmentCart.getTotalFee()*percentTax*100)/100;

        double total = Math.round((managmentCart.getTotalFee()+percentTax+delivery)*100)/100;
        double itemTotal = Math.round(managmentCart.getTotalFee()*100)/100;
        binding.TotalFeeTxt.setText( itemTotal + "€");
        binding.TaxTxt.setText(percentTax + "€");
        binding.DeliveryTxt.setText(delivery + "€");
        binding.TotalTxt.setText(total + "€");
    }
    private void setVariable() {
        binding.BackBtn.setOnClickListener(v -> finish());
    }
}