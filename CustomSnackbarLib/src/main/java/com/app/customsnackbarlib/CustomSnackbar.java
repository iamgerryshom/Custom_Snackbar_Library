package com.app.customsnackbarlib;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class CustomSnackbar {

    private static final int ANIMATION_DURATION = 300;

    public static CustomSnackbar show(ViewGroup rootView, String message, int duration, int type) {
        View customSnackbarView = createSnackbarView(rootView, message, type);
        showSnackbar(rootView, customSnackbarView, duration);
        return new CustomSnackbar();
    }

    public static CustomSnackbar myLayout(ViewGroup view, int layoutResId, int duration) {
        View customView = LayoutInflater.from(view.getContext()).inflate(layoutResId, null);
        showSnackbar(view, customView, duration);
        return new CustomSnackbar();
    }

    private static View createSnackbarView(ViewGroup rootView, String message, int type) {
        LayoutInflater inflater = LayoutInflater.from(rootView.getContext());
        View customSnackbarView = inflater.inflate(R.layout.custom_snackbar, rootView, false);

        TextView textView = customSnackbarView.findViewById(R.id.snackbar_text);
        ImageView iconView = customSnackbarView.findViewById(R.id.snackbar_icon);
        textView.setText(message);

        int iconResId, backgroundResId;
        switch (type) {
            case 1: // Success
                iconResId = R.drawable.ic_success;
                backgroundResId = R.drawable.snackbar_success_background;
                break;
            case 2: // Warning
                iconResId = R.drawable.ic_warning;
                backgroundResId = R.drawable.snackbar_warning_background;
                break;
            case 3: // Error
                iconResId = R.drawable.ic_error;
                backgroundResId = R.drawable.snackbar_error_background;
                break;
            default:
                iconResId = R.drawable.ic_default;
                backgroundResId = R.drawable.snackbar_default_background;
        }

        iconView.setImageResource(iconResId);
        customSnackbarView.setBackgroundResource(backgroundResId);

        return customSnackbarView;
    }

    private static void showSnackbar(ViewGroup rootView, View customView, int duration) {
        customView.setTranslationY(-customView.getHeight());

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        params.gravity = Gravity.TOP;
        params.setMargins(20, 50, 20, 0);

        if (customView.getParent() == null) {
            rootView.addView(customView, params);
        }

        animateIn(customView);
        addSwipeToDismiss(customView);

        int durationInMillis = getDurationInMillis(duration);
        if (durationInMillis != Snackbar.LENGTH_INDEFINITE) {
            customView.postDelayed(() -> hide(customView), durationInMillis);
        }
    }

    private static void animateIn(View view) {
        view.setTranslationY(-view.getHeight());
        view.animate()
                .translationY(0)
                .setDuration(ANIMATION_DURATION)
                .setStartDelay(0)
                .start();
    }

    private static void addSwipeToDismiss(View customSnackbarView) {
        final float SWIPE_THRESHOLD = 100; // Minimum distance for a swipe to be detected
        final float[] startX = new float[1];
        final float[] startY = new float[1];
        final float[] dX = new float[1];
        final float[] dY = new float[1];

        customSnackbarView.setOnTouchListener((view, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startX[0] = event.getRawX();
                    startY[0] = event.getRawY();
                    dX[0] = customSnackbarView.getX() - event.getRawX();
                    dY[0] = customSnackbarView.getY() - event.getRawY();
                    return true;

                case MotionEvent.ACTION_MOVE:
                    float newX = event.getRawX() + dX[0];
                    float newY = event.getRawY() + dY[0];
                    customSnackbarView.setX(newX);
                    // Limit upward movement only
                    if (newY <= 0) {
                        customSnackbarView.setY(newY);
                    }
                    return true;

                case MotionEvent.ACTION_UP:
                    float endX = event.getRawX();
                    float endY = event.getRawY();
                    float deltaX = endX - startX[0];
                    float deltaY = endY - startY[0];

                    if (Math.abs(deltaX) > SWIPE_THRESHOLD) {
                        // Horizontal swipe detected
                        hide(customSnackbarView);
                    } else if (-deltaY > SWIPE_THRESHOLD) {
                        // Upward swipe detected
                        customSnackbarView.animate()
                                .translationY(0)
                                .setDuration(0)
                                .withEndAction(removeView(customSnackbarView))
                                .start();
                    } else {
                        resetPosition(customSnackbarView);
                    }
                    return true;
            }
            return false;
        });
    }

    private static void resetPosition(View view) {
        view.animate()
                .x(0)
                .setDuration(200)
                .setInterpolator(new AccelerateInterpolator())
                .start();
    }

    // Hide method with direction-based animation
    public static void hide(View customSnackbarView) {
        float currentX = customSnackbarView.getX();
        float currentY = customSnackbarView.getY();
        float screenWidth = customSnackbarView.getResources().getDisplayMetrics().widthPixels;

        if (Math.abs(currentX) > Math.abs(currentY)) {
            // Horizontal swipe
            float targetX = (currentX > 0) ? screenWidth : -screenWidth;
            customSnackbarView.animate()
                    .x(targetX)
                    .setDuration(300)
                    .withEndAction(removeView(customSnackbarView))
                    .start();
        } else {
            // Vertical swipe (upward)
            customSnackbarView.animate()
                    .translationY(-customSnackbarView.getHeight())
                    .setDuration(300)
                    .withEndAction(removeView(customSnackbarView))
                    .start();
        }
    }

    private static Runnable removeView(final View view) {
        return new Runnable() {
            @Override
            public void run() {
                if (view.getParent() != null) {
                    ((ViewGroup) view.getParent()).removeView(view);
                }
            }
        };
    }

    private static int getDurationInMillis(int duration) {
        switch (duration) {
            case Snackbar.LENGTH_SHORT:
                return 2000;
            case Snackbar.LENGTH_LONG:
                return 3500;
            case Snackbar.LENGTH_INDEFINITE:
                return Snackbar.LENGTH_INDEFINITE;
            default:
                return 2000;
        }
    }
}