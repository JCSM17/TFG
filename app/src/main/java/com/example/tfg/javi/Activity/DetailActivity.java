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

public class DetailActivity extends AppCompatActivity{
    private ActivityDetailBinding binding;
    private PopularDomain object;
    private int numberOrder = 1;
    private ManagmentCart managmentCart;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getBundles();
        managmentCart = new ManagmentCart(this);
        statusBarColor();
    }

    private void statusBarColor(){
        Window window=DetailActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(DetailActivity.this, R.color.white));
    }
    private void getBundles(){
        object = (PopularDomain) getIntent().getSerializableExtra("object");
        int drawableResourceId=this.getResources().getIdentifier(object.getPicUrl(),"drawable",this.getPackageName());
        Glide.with(this)
                .load(drawableResourceId)
                .into(binding.itemPic);

        binding.TitleTxt.setText(object.getTitleText());
        binding.PriceTxt.setText(object.getPrice()+"€");
        binding.DescripcionTxt.setText(object.getDescripcion());
        binding.ReviewTxt.setText(object.getReviewTxt()+ "");
        binding.RatingTxt.setText(object.getScoreTxt()+ "");

        binding.AniadirCarrito.setOnClickListener(v -> {
            object.setNumberInCart(numberOrder);
            managmentCart.insertFood(object);
        });
        binding.backBtn.setOnClickListener(v -> finish());
    }
}
