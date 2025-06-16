package com.app.customsnackbarlib;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.app.customsnackbarlib.enums.CustomAnimation;
import com.app.customsnackbarlib.enums.CustomSnackBarType;
import com.app.customsnackbarlib.helper.DpPxHelper;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.snackbar.BaseTransientBottomBar;

public class CustomSnackBar extends BaseTransientBottomBar<CustomSnackBar> {

    private static final long ENTER_ANIMATION_DURATION = 300;
    private static final long EXIT_ANIMATION_DURATION = 200;

    private static float _defaultCornerRadius;
    private static float _defaultStrokeWidth;

    private int _defaultStrokeColor;

    private float topRightCornerRadius;
    private float topLeftCornerRadius;
    private float bottomLeftCornerRadius;
    private float bottomRightCornerRadius;
    private float strokeWidth;
    private float opacityPercentage;

    private int backgroundColor;
    private int strokeColor;

    private CustomSnackBarType customSnackBarType;
    private CustomAnimation customEnterAnimation;
    private CustomAnimation customExitAnimation;
    private Interpolator interpolator;
    private int gravity;

    private final String customSnackBarTitle;
    private final String customSnackBarDescription;

    private boolean drawOverApps;

    public CustomSnackBar(final ViewGroup parent, final String customSnackBarTitle, final String customSnackBarDescription) {
        super(parent.getContext(), parent, LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_snackbar, parent, false), new SnackbarContentViewCallback());
        init();
        this.customSnackBarTitle = customSnackBarTitle;
        this.customSnackBarDescription = customSnackBarDescription;
    }

    private static FrameLayout container;
    private static WindowManager.LayoutParams params;
    public static CustomSnackBar make(final Context context, final String title, final String description) {
        if (context == null) throw new IllegalArgumentException("Context cannot be null");
        if (title == null) throw new IllegalArgumentException("Title cannot be null");
        if (description == null) throw new IllegalArgumentException("Description cannot be null");
        container = createContainer(context);
        return new CustomSnackBar(container, title, description);
    }


    public static CustomSnackBar make(final ViewGroup view, final String title) {
        if(view == null) throw new RuntimeException("ViewGroup cannot be null");
        if(title == null) throw new RuntimeException("Title cannot be null");
        return new CustomSnackBar(view, title, null);
    }

    private void init() {
        _defaultCornerRadius = DpPxHelper.dpToPx(getContext(), 8);
        _defaultStrokeWidth = DpPxHelper.dpToPx(getContext(),0);
        _defaultStrokeColor = Color.TRANSPARENT;
    }

    /**/
    private CustomSnackBar setInterpolator(final Interpolator interpolator) {
        if(interpolator == null) throw new RuntimeException("Interpolator cannot be null");
        this.interpolator = interpolator;
        return this;
    }

    public CustomSnackBar setCustomExitAnimation(final CustomAnimation customExitAnimation) {
        if(customExitAnimation == null) throw new RuntimeException("CustomAnimation cannot be null");
        this.customExitAnimation = customExitAnimation;
        return this;
    }

    public CustomSnackBar setCustomEnterAnimation(final CustomAnimation customEnterAnimation) {
        if(customEnterAnimation == null) throw new RuntimeException("CustomAnimation cannot be null");
        this.customEnterAnimation = customEnterAnimation;
        return this;
    }

    public CustomSnackBar setCustomSnackBarType(final CustomSnackBarType customSnackBarType) {
        if(customSnackBarType == null) throw new RuntimeException("CustomSnackBarType cannot be null");
        this.customSnackBarType = customSnackBarType;
        return this;
    }

    public CustomSnackBar setCornerRadius(final int cornerRadius) {
        if(cornerRadius < 0) throw new RuntimeException("cornerRadius cannot be less than zero");
        topLeftCornerRadius = topRightCornerRadius = bottomRightCornerRadius = bottomLeftCornerRadius = DpPxHelper.dpToPx(getContext(), cornerRadius);
        return this;
    }


    public CustomSnackBar setTopCornerRadius(final float topCornerRadius)  {
        if(topCornerRadius < 0) throw new RuntimeException("topCornerRadius cannot be less than zero");
        topLeftCornerRadius = topRightCornerRadius = DpPxHelper.dpToPx(getContext(), topCornerRadius);
        return this;
    }

    public CustomSnackBar setBottomCornerRadius(final float bottomCornerRadius) {
        if(bottomCornerRadius < 0) throw new RuntimeException("bottomCornerRadius cannot be less than zero");
        bottomRightCornerRadius = bottomLeftCornerRadius = DpPxHelper.dpToPx(getContext(), bottomCornerRadius);
        return this;
    }

    public CustomSnackBar setTopRightCornerRadius(final int topRightCornerRadius) {
        if(topRightCornerRadius < 0) throw new RuntimeException("topRightCornerRadius cannot be less than zero");
        this.topRightCornerRadius = DpPxHelper.dpToPx(getContext(), topRightCornerRadius);
        return this;
    }

    public CustomSnackBar setTopLeftCornerRadius(final int topLeftCornerRadius) {
        if(topLeftCornerRadius < 0) throw new RuntimeException("topLeftCornerRadius cannot be less than zero");
        this.topLeftCornerRadius = DpPxHelper.dpToPx(getContext(), topLeftCornerRadius);
        return this;
    }

    public CustomSnackBar setBottomLeftCornerRadius(final int bottomLeftCornerRadius) {
        if(bottomLeftCornerRadius < 0) throw new RuntimeException("bottomLeftCornerRadius cannot be less than zero");
        this.bottomLeftCornerRadius = DpPxHelper.dpToPx(getContext(), bottomLeftCornerRadius);
        return this;
    }

    public CustomSnackBar setBottomRightCornerRadius(final int bottomRightCornerRadius) {
        if(bottomRightCornerRadius < 0) throw new RuntimeException("bottomRightCornerRadius cannot be less than zero");
        this.bottomRightCornerRadius = DpPxHelper.dpToPx(getContext(), bottomRightCornerRadius);
        return this;
    }

    public CustomSnackBar setColor(final int color) {
        return this;
    }

    public CustomSnackBar setBackgroundColor(final int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public CustomSnackBar setStrokeWidth(final int strokeWidth) {
        if(strokeWidth < 0) throw new RuntimeException("strokeWidth cannot be less than zero");
        this.strokeWidth = strokeWidth;
        return this;
    }

    public CustomSnackBar setOpacityPercentage(final float opacityPercentage) {
        if(opacityPercentage < 0 || opacityPercentage > 100) throw new RuntimeException("opacityPercentage can only be between 0 and 100");
        this.opacityPercentage = opacityPercentage;
        return this;
    }

    public CustomSnackBar setStrokeColor(final int strokeColor) {
        if(strokeWidth <= 0) throw new RuntimeException("StrokeColor was set but the strokeWidth is either 0 or less than 0");
        this.strokeColor = strokeColor;
        return this;
    }

    public CustomSnackBar setGravity(final int gravity) {
        this.gravity = gravity;
        return this;
    }

    public CustomSnackBar setDrawOverApps(final boolean drawOverApps) {
        if(!overlayPermissionIsGranted(getContext()) && drawOverApps) requestOverlayPermission(getContext());
        this.drawOverApps = drawOverApps;
        return this;
    }

    private static class SnackbarContentViewCallback implements com.google.android.material.snackbar.ContentViewCallback {

        @Override
        public void animateContentIn(int delay, int duration) {
            // Custom animation logic when content enters
        }

        @Override
        public void animateContentOut(int delay, int duration) {
            // Custom animation logic when content exits
        }
    }

    private static FrameLayout createContainer(Context context) {
        final FrameLayout container = new FrameLayout(context);
        container.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        ));
        return container;
    }

    private WindowManager.LayoutParams createLayoutParams() {
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                drawOverApps ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_APPLICATION_PANEL,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT
        );
        params.gravity = gravity;
        return params;
    }

    private boolean overlayPermissionIsGranted(final Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(context);
        }
        return true;
    }


    private void requestOverlayPermission(final Context context) {
        final Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }

    private static WindowManager windowManager;
    private static void addToWindowManager(Context context, FrameLayout container, WindowManager.LayoutParams params) {
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager == null) {
            throw new RuntimeException("Failed to get WindowManager from context.");
        }
        windowManager.addView(container, params);
    }

    private BaseCallback<CustomSnackBar> baseCallback = new BaseCallback<CustomSnackBar>() {
        @Override
        public void onDismissed(CustomSnackBar transientBottomBar, int event) {
            super.onDismissed(transientBottomBar, event);

            if (container != null && container.getParent() != null) {
                //windowManager.removeView(container);
            }

            switch (event) {
                case DISMISS_EVENT_TIMEOUT:
                    customExitAnimation.animate(getView(), EXIT_ANIMATION_DURATION, interpolator);
                    Log.d("CustomSnackbar", "Snackbar dismissed automatically due to timeout");
                    break;
                case DISMISS_EVENT_MANUAL:
                    Log.d("CustomSnackbar", "Snackbar dismissed manually by user");
                    break;
                case DISMISS_EVENT_ACTION:
                    Log.d("CustomSnackbar", "Snackbar dismissed due to action button press");
                    break;
                case DISMISS_EVENT_CONSECUTIVE:
                    Log.d("CustomSnackbar", "Snackbar dismissed due to another Snackbar appearing");
                    break;
            }

        }
    };

    @Override
    public void show() {
        super.show();

        setDefaults(); //must be called first to init null variables

        params = createLayoutParams();

        addCallback(baseCallback);

        applyCustomSnackBarBackground();

        ((TextView) getView().findViewById(R.id.snackbar_title)).setText(customSnackBarTitle);
        ((TextView) getView().findViewById(R.id.snackbar_description)).setText(customSnackBarDescription == null ? "" : customSnackBarDescription);

        addSwipeToDismiss(getView());

        addToWindowManager(getContext(), container, params);

        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {

                view.getViewTreeObserver().removeOnPreDrawListener(this);

                        customEnterAnimation.animate(getView(), ENTER_ANIMATION_DURATION, interpolator);

                return true;

            }
        });


    }

    private void applyCustomSnackBarBackground() {
        final Drawable drawable = getShapeableDrawable(
                topLeftCornerRadius,
                topRightCornerRadius,
                bottomLeftCornerRadius,
                bottomRightCornerRadius,
                strokeWidth,
                strokeColor,
                backgroundColor
        );

        getView().setBackground(drawable);
        getView().setBackgroundTintList(ColorStateList.valueOf(backgroundColor));

        getView().findViewById(R.id.snackbar_icon).setBackgroundResource(customSnackBarType.getDrawableResource());
        getView().findViewById(R.id.snackbar_icon).setBackgroundTintList(ColorStateList.valueOf(customSnackBarType.getDrawableColorResource(getContext())));
        getView().findViewById(R.id.rounded_card).setBackgroundTintList(ColorStateList.valueOf(customSnackBarType.getDrawableColorResource(getContext())));

    }

    private void setDefaults() {
        setBackgroundDefaultsIfZero();
        setDefaultInterpolatorIfNull();
        setDefaultCornerRadiiIfZero();
        setDefaultAnimationsIfNull();
        setStrokeDefaultsIfZero();
        if(gravity == 0) gravity = Gravity.BOTTOM;
    }


    private void setStrokeDefaultsIfZero() {
        if(strokeWidth == 0) strokeWidth = _defaultStrokeWidth;
        if(strokeColor == 0) strokeColor = _defaultStrokeColor;
    }

    private void setBackgroundDefaultsIfZero() {
        if(backgroundColor == 0) backgroundColor = customSnackBarType.getColorResource(getContext());
    }

    private void setDefaultAnimationsIfNull() {
        if(customEnterAnimation == null) customEnterAnimation = CustomAnimation.POP_IN;
        if(customExitAnimation == null) customExitAnimation = CustomAnimation.POP_OUT;
    }

    private void setDefaultCornerRadiiIfZero() {
        if(topRightCornerRadius == 0) topRightCornerRadius = _defaultCornerRadius;
        if(topLeftCornerRadius == 0) topLeftCornerRadius = _defaultCornerRadius;
        if(bottomRightCornerRadius == 0) bottomRightCornerRadius = _defaultCornerRadius;
        if(bottomLeftCornerRadius == 0) bottomLeftCornerRadius = _defaultCornerRadius;
    }

    private void setDefaultInterpolatorIfNull() {
        if(interpolator == null) interpolator = new OvershootInterpolator();
    }

    private Drawable getShapeableDrawable(
            final float topLeftRadius,
            final float topRightRadius,
            final float bottomLeftRadius,
            final float bottomRightRadius,
            final float strokeWidth,
            final int strokeColor,
            final int backgroundColor
    ) {

        final ShapeAppearanceModel shapeModel = new ShapeAppearanceModel.Builder()
                .setTopLeftCorner(CornerFamily.ROUNDED, topLeftRadius)
                .setTopRightCorner(CornerFamily.ROUNDED, topRightRadius)
                .setBottomLeftCorner(CornerFamily.ROUNDED, bottomLeftRadius)
                .setBottomRightCorner(CornerFamily.ROUNDED, bottomRightRadius)
                .build();

        final MaterialShapeDrawable shapeDrawable = new MaterialShapeDrawable(shapeModel);
        //shapeDrawable.setFillColor(ColorStateList.valueOf(backgroundColor));
        shapeDrawable.setTintList(ColorStateList.valueOf(backgroundColor));
        shapeDrawable.setStrokeWidth(strokeWidth);
        shapeDrawable.setStrokeColor(ColorStateList.valueOf(strokeColor));

        return shapeDrawable;
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

}