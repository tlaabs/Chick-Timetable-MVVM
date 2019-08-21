package io.github.tlaabs.ctt.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import io.github.tlaabs.ctt.model.SaveManager;


public class BaseActivity extends AppCompatActivity {

    protected SaveManager saveManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        saveManager = SaveManager.getInstance(getApplicationContext());
    }
}
