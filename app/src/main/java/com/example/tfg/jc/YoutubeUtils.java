package com.example.tfg.jc;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class YoutubeUtils {

    public static void openYoutubeVideo(Context context, String url) {
        Intent intentApp = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intentApp.setPackage("com.google.android.youtube");

        Intent intentBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

        try {
            // Intenta abrir la aplicación de YouTube
            context.startActivity(intentApp);
        } catch (ActivityNotFoundException e) {
            // Si la aplicación de YouTube no está instalada, abre el video en el navegador
            context.startActivity(intentBrowser);
        }
    }
}