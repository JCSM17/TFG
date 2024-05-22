package com.example.tfg.jesus;

import android.content.Context;
import android.os.CountDownTimer;

public class CustomCountdownTimer {
    private long millisInFuture;
    private long countDownInterval;
    private long millisUntilFinished;
    private InternalTimer timer;
    private final Context context;

    private OnTick onTick;
    private OnFinish onFinish;

    public CustomCountdownTimer(Context context, long millisInFuture, long countDownInterval) {
        this.context = context;
        this.millisInFuture = millisInFuture;
        this.countDownInterval = countDownInterval;
        this.millisUntilFinished = millisInFuture;
        this.timer = new InternalTimer(this, millisInFuture, countDownInterval);
    }

    public interface OnTick {
        void onTick(long millisUntilFinished);
    }

    public interface OnFinish {
        void onFinish();

        void onVibrate();
    }

    private class InternalTimer extends CountDownTimer {
        private CustomCountdownTimer parent;
        private boolean isRunning;

        public InternalTimer(CustomCountdownTimer parent, long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            this.parent = parent;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            this.parent.millisUntilFinished = millisUntilFinished;
            if (parent.onTick != null) {
                parent.onTick.onTick(millisUntilFinished);
            }
        }

        @Override
        public void onFinish() {
            this.parent.millisUntilFinished = 0;
            if (parent.onFinish != null) {
                parent.onFinish.onFinish();
            }
            isRunning = false;
        }

        public void startTimer() {
            this.start();
            isRunning = true;
        }

        public void pauseTimer() {
            this.cancel();
            isRunning = false;
        }

        public boolean isRunning() {
            return isRunning;
        }
    }

    public void setOnTick(OnTick onTick) {
        this.onTick = onTick;
    }

    public void setOnFinish(OnFinish onFinish) {
        this.onFinish = onFinish;
    }

    public void pauseTimer() {
        timer.pauseTimer();
    }

    public void resumeTimer() {
        if (!timer.isRunning() && millisUntilFinished > 0) {
            timer = new InternalTimer(this, millisUntilFinished, countDownInterval);
            timer.startTimer();
        }
    }

    public void startTimer() {
        timer.startTimer();
    }

    public void restartTimer() {
        timer.cancel();
        timer = new InternalTimer(this, millisInFuture, countDownInterval);
        timer.startTimer();
    }

    public void destroyTimer() {
        timer.cancel();
    }
}