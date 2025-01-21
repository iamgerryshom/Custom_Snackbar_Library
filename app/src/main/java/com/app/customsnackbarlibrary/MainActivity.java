package com.app.customsnackbarlibrary;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.customsnackbarlib.CustomSnackBar;
import com.app.customsnackbarlib.enums.CustomAnimation;
import com.app.customsnackbarlib.enums.CustomSnackBarType;
import com.app.customsnackbarlibrary.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        setContentView(binding.getRoot());

        binding.info.setOnClickListener(v -> {
            CustomSnackBar.make(binding.getRoot(), "Download Complete", "The file has been successfully downloaded.")
                    .setCustomSnackBarType(CustomSnackBarType.INFO)
                    .show();
        });

        binding.warning.setOnClickListener(v -> {
            CustomSnackBar.make(binding.getRoot(), "Low Storage Space", "Consider freeing up space to avoid issues.")
                    .setCustomSnackBarType(CustomSnackBarType.WARNING)
                    .show();
        });

        binding.error.setOnClickListener(v -> {
            CustomSnackBar.make(binding.getRoot(), "Upload Failed", "Unable to upload the file. Please try again.")
                    .setCustomSnackBarType(CustomSnackBarType.ERROR)
                    .show();
        });

        binding.success.setOnClickListener(v -> {
            CustomSnackBar.make(binding.getRoot(), "Connection Established", "You are now connected to the server.")
                    .setCustomSnackBarType(CustomSnackBarType.SUCCESS)
                    .show();
        });


    }


    private void init() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

}