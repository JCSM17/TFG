package com.example.tfg.jesus;

import android.os.CountDownTimer;

public class CustomCountdownTimer {
    private long millisInFuture;
    private long countDownInterval;
    private long millisUntilFinished;
    private InternalTimer timer;
    private boolean isRunning;
    private OnTick onTick;
    private OnFinish onFinish;

    public CustomCountdownTimer(long millisInFuture, long countDownInterval) {
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
    }

    private class InternalTimer extends CountDownTimer {
        private CustomCountdownTimer parent;

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
        }
    }

    public void setOnTick(OnTick onTick) {
        this.onTick = onTick;
    }

    public void setOnFinish(OnFinish onFinish) {
        this.onFinish = onFinish;
    }

    public void pauseTimer() {
        timer.cancel();
        isRunning = false;
    }

    public void resumeTimer() {
        if (!isRunning && millisUntilFinished > 0) {
            timer = new InternalTimer(this, millisUntilFinished, countDownInterval);
            startTimer();
        }
    }

    public void startTimer() {
        timer.start();
        isRunning = true;
    }

    public void restartTimer() {
        timer.cancel();
        timer = new InternalTimer(this, millisInFuture, countDownInterval);
        startTimer();
    }

    public void destroyTimer() {
        timer.cancel();
    }
}