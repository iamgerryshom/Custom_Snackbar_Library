package com.app.customsnackbarlib.behaviour;

import android.content.Context;

public interface SnackBarBehavior {
    int getColorResource(final Context context);
    String getDescription();
    int getDrawableResource();
    int getDrawableColorResource(final Context context);
}
