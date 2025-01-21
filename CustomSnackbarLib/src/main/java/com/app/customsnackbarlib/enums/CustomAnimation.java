package com.app.customsnackbarlib.enums;

import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;

import com.app.customsnackbarlib.animation.CustomAnimator;
import com.app.customsnackbarlib.listeners.CustomAnimationListener;

public enum CustomAnimation {

    SLIDE_IN_TOP {
        @Override
        public void animate(View view, long duration, Interpolator interpolator) {

            applyAnimation(
                    view,
                    CustomAnimator.createAnimationSet(
                    0, 0,
                    -view.getHeight(),0,
                            .5f,1f,
                            .5f,1f,
                    duration, interpolator
                    )
            );
        }
    },
    SLIDE_IN_BOTTOM {
        @Override
        public void animate(View view, long duration, Interpolator interpolator) {
            applyAnimation(
                    view,
                    CustomAnimator.createAnimationSet(
                    0, 0,
                    view.getHeight(),0,
                            .5f,1f,
                            .5f,1f,
                    duration, interpolator
                    )
            );
        }
    },
    SLIDE_OUT_TOP {
        @Override
        public void animate(View view, long duration, Interpolator interpolator) {
            applyAnimation(
                    view,
                    CustomAnimator.createAnimationSet(
                    0, 0,
                    0,-view.getHeight(),
                    1f,1f,
                    1f,0f,
                    duration, interpolator
                    )
            );
        }
    },
    SLIDE_OUT_BOTTOM {
        @Override
        public void animate(View view, long duration, Interpolator interpolator) {
            applyAnimation(
                    view,
                    CustomAnimator.createAnimationSet(
                    0, 0,
                    0,view.getHeight(),
                    1f,1f,
                    1f,0f,
                    duration, interpolator
                    )
            );
        }
    },
    SLIDE_IN_LEFT {
        @Override
        public void animate(View view, long duration, Interpolator interpolator) {

            applyAnimation(
                    view,
                    CustomAnimator.createAnimationSet(
                    -view.getWidth(), 0,
                    0,0,
                    1f,1f,
                    0f,1f,
                    duration, interpolator
                    )
            );
        }
    },
    SLIDE_IN_RIGHT {
        @Override
        public void animate(View view, long duration, Interpolator interpolator) {
            applyAnimation(
                    view,
                    CustomAnimator.createAnimationSet(
                    view.getWidth(), 0,
                    0,0,
                    1f,1f,
                    0f,1f,
                    duration, interpolator
                    )
            );
        }
    },
    SLIDE_OUT_LEFT {
        @Override
        public void animate(View view, long duration, Interpolator interpolator) {
            applyAnimation(
                    view,
                    CustomAnimator.createAnimationSet(
                    0, -view.getWidth(),
                    0,0,
                    1f,1f,
                    1f,0f,
                    duration, interpolator
                    )
            );
        }
    },
    SLIDE_OUT_RIGHT {
        @Override
        public void animate(View view, long duration, Interpolator interpolator) {
            applyAnimation(
                    view,
                    CustomAnimator.createAnimationSet(
                    0, view.getWidth(),
                    0,0,
                    1f,1f,
                    1f,0f,
                    duration, interpolator
                    )
            );
        }
    },
    FADE_IN {
        @Override
        public void animate(View view, long duration, Interpolator interpolator) {

            applyAnimation(
                    view,
                    CustomAnimator.createAnimationSet(
                    0, 0,
                    0,0,
                            1f,1f,
                            .5f,1f,
                    duration, interpolator
                    )
            );
        }
    },
    FADE_OUT {
        @Override
        public void animate(View view, long duration, Interpolator interpolator) {
            applyAnimation(
                    view,
                    CustomAnimator.createAnimationSet(
                    0, 0,
                    0,0,
                    1f,1f,
                    1f,.5f,
                    duration, interpolator
                    )
            );
        }
    },
    POP_IN {
        @Override
        public void animate(View view, long duration, Interpolator interpolator) {
            applyAnimation(
                    view,
                    CustomAnimator.createAnimationSet(
                    0, 0,
                    0,0,
                    .7f,1f,
                    0f,1f,
                    duration, interpolator
                    )
            );
        }
    },
    POP_OUT {
        @Override
        public void animate(View view, long duration, Interpolator interpolator) {
            applyAnimation(
                    view,
                    CustomAnimator.createAnimationSet(
                    0, 0,
                    0,0,
                    1f,.7f,
                    1f,0f,
                    duration, interpolator
            ));
        }
    };

    public abstract void animate(final View view, final long duration, final Interpolator interpolator);

    protected void applyAnimation(
            final View view,
            final AnimationSet animationSet
    ) {

        Log.d("Animation", "View width: " + view.getWidth());
        Log.d("Animation", "View height: " + view.getHeight());

        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {


                view.getViewTreeObserver().removeOnPreDrawListener(this);
                view.startAnimation(animationSet);

                return true;


            }
        });


    }
}
