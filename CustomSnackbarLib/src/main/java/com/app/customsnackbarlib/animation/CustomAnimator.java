package com.app.customsnackbarlib.animation;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.app.customsnackbarlib.listeners.CustomAnimationListener;

public class CustomAnimator {

    public static void slideInTop(final View v, long duration, final CustomAnimationListener listener) {
        setViewProperties(v, 1f, 1f, 0f, -v.getHeight());
        animateView(v, 1f, 1f, 1f, 0f, duration, listener);
    }

    public static void slideOutTop(final View v, long duration, final CustomAnimationListener listener) {
        animateView(v, 1f, 1f, 0f, -v.getHeight(), duration, listener);
    }

    public static void slideInBottom(final View v, long duration, final CustomAnimationListener listener) {
        setViewProperties(v, 1f, 1f, 0f, v.getHeight());
        animateView(v, 1f, 1f, 1f, 0f, duration, listener);
    }

    public static void slideOutBottom(final View v, long duration, final CustomAnimationListener listener) {
        animateView(v, 1f, 1f, 0f, v.getHeight(), duration, listener);
    }

    public static void popIn(final View view, final long duration, final CustomAnimationListener listener) {
        setViewProperties(view, 0.5f, 0.5f, 0f, 0f);
        animateView(view, 1f, 1f, 1f, 0f,  duration, listener);
    }

    public static void popOut(final View view, final long duration, final CustomAnimationListener listener) {
        animateView(view, 0.5f, 0.5f, 0f, 0f, duration, listener);
    }

    public static void slideInLeft(final View view, final long duration, final CustomAnimationListener listener) {
        setViewProperties(view, 1f, 1f, 1f, -view.getWidth());
        animateView(view, 1f, 1f, 1f, 0f, duration, listener);
    }

    public static void slideInRight(final View view, final long duration, final CustomAnimationListener listener) {
        setViewProperties(view, 1f, 1f, 1f, view.getWidth());
        animateView(view, 1f, 1f, 1f, 0f, duration, listener);
    }

    public static void slideOutLeft(final View view, final long duration, final CustomAnimationListener listener) {
        animateView(view, 1f, 1f, 1f, -view.getWidth(), duration, listener);
    }

    public static void slideOutRight(final View view, final long duration, final CustomAnimationListener listener) {
        animateView(view, 1f, 1f, 1f, view.getWidth(), duration, listener);
    }

    private static void setViewProperties(View v, float scaleX, float scaleY, float alpha, float translationY) {
        v.setScaleX(scaleX);
        v.setScaleY(scaleY);
        v.setAlpha(alpha);
        v.setTranslationY(translationY);
    }

    private static void animateView(View v, float scaleX, float scaleY, float alpha, float translationY, long duration, CustomAnimationListener listener) {
        v.animate()
                .scaleX(scaleX)
                .scaleY(scaleY)
                .alpha(alpha)
                .translationY(translationY)
                .setDuration(duration)
                .withEndAction(() -> {
                    if (listener != null) listener.onAnimationEnd();
                })
                .start();
    }

    public static AnimationSet createAnimationSet(float fromXDelta, float toXDelta,
                                                  float fromYDelta, float toYDelta,
                                                  float fromScale, float toScale,
                                                  float fromAlpha, float toAlpha,
                                                  long duration, Interpolator interpolator) {
        if (interpolator == null) {
            interpolator = new OvershootInterpolator();
        }
        final AnimationSet animationSet = new AnimationSet(true);
        animationSet.setInterpolator(interpolator);

        final TranslateAnimation translateAnimation = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
        translateAnimation.setDuration(duration);
        translateAnimation.setFillAfter(true);

        final ScaleAnimation scaleAnimation = new ScaleAnimation(fromScale, toScale, fromScale, toScale,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(duration);
        scaleAnimation.setFillAfter(true);

        final AlphaAnimation alphaAnimation = new AlphaAnimation(fromAlpha, toAlpha);
        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);

        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);

        return animationSet;
    }



}
