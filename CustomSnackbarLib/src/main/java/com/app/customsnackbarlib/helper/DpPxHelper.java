package com.app.customsnackbarlib.helper;

import android.content.Context;

public class DpPxHelper {
    public static float dpToPx(final Context context, final float dp) {
        final float density = context.getResources().getDisplayMetrics().density;
        return dp * density;
    }
}
