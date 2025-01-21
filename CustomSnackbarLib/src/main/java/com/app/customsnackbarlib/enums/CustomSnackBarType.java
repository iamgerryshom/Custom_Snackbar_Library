package com.app.customsnackbarlib.enums;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.app.customsnackbarlib.R;
import com.app.customsnackbarlib.behaviour.SnackBarBehavior;

public enum CustomSnackBarType implements SnackBarBehavior {
    SUCCESS {
        @Override
        public int getColorResource(Context context) {
            return ContextCompat.getColor(context, R.color.success_color);
        }

        @Override
        public String getDescription() {
            return "Operation successful!";
        }

        @Override
        public int getDrawableResource() {
            return R.drawable.ic_success;
        }

        @Override
        public int getDrawableColorResource(Context context) {
            return ContextCompat.getColor(context, R.color.light_success_color);
        }
    },
    WARNING {
        @Override
        public int getColorResource(Context context) {
            return ContextCompat.getColor(context, R.color.warning_color);
        }

        @Override
        public String getDescription() {
            return "Warning: Please check the input.";
        }

        @Override
        public int getDrawableResource() {
            return R.drawable.ic_warning;
        }
        @Override
        public int getDrawableColorResource(Context context) {
            return ContextCompat.getColor(context, R.color.light_warning_color);
        }
    },
    ERROR {
        @Override
        public int getColorResource(Context context) {
            return ContextCompat.getColor(context, R.color.error_color);
        }

        @Override
        public String getDescription() {
            return "An error occurred. Please try again.";
        }

        @Override
        public int getDrawableResource() {
            return R.drawable.ic_error;
        }
        @Override
        public int getDrawableColorResource(Context context) {
            return ContextCompat.getColor(context, R.color.light_error_color);
        }

    },
    INFO {
        @Override
        public int getColorResource(Context context) {
            return ContextCompat.getColor(context, R.color.info_color);

        }

        @Override
        public String getDescription() {
            return "An error occurred. Please try again.";
        }

        @Override
        public int getDrawableResource() {
            return R.drawable.ic_default;
        }
        @Override
        public int getDrawableColorResource(Context context) {
            return ContextCompat.getColor(context, R.color.light_info_color);
        }

    };
}
