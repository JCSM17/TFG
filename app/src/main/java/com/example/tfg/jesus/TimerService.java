package com.example.tfg.jesus;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.example.tfg.R;
import com.example.tfg.javi.MainActivity;

public class TimerService extends Service {

    private CustomCountdownTimer customCountdownTimer;

    @Override
    public void onCreate() {
        super.onCreate();

        int countdownTime = 60; // 60 segundos
        long clockTime = countdownTime * 1000;

        customCountdownTimer = new CustomCountdownTimer(this, clockTime, 1000);
        customCountdownTimer.startTimer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(this, App.CHANNEL_ID)
                .setContentTitle("Servicio de Temporizador")
                .setContentText(input)
                .setSmallIcon(R.drawable.icono_play)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        customCountdownTimer.destroyTimer();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}