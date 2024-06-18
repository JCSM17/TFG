package com.example.tfg.jesus;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import com.example.tfg.jc.MenuActivity;
import com.example.tfg.jc.YoutubeUtils;

public class ButtonSetupUtils {

    public static void setupButton(Fragment fragment, View view, int buttonId, String url) {
        Button button = view.findViewById(buttonId);
        button.setOnClickListener(v -> {
            Context context = fragment.getContext();
            if (context != null) {
                YoutubeUtils.openYoutubeVideo(context, url);
            }
        });
    }

    public static void setupButton(Fragment fragment, View view, int buttonId) {
        AppCompatImageButton button = view.findViewById(buttonId);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(fragment.getActivity(), MenuActivity.class);
            fragment.startActivity(intent);
        });
    }

    public static void setupImageButton(View view, int imageButtonId) {
        ImageButton imageButton = view.findViewById(imageButtonId);
        imageButton.setOnClickListener(v -> {
            Drawable drawable = imageButton.getDrawable();
            if (drawable instanceof Animatable) {
                Animatable animatable = (Animatable) drawable;
                animatable.start();
                new Handler().postDelayed(animatable::stop, 2500); // Stop 2.5 seconds
            }
        });
    }
}