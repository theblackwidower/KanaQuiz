package com.noprestige.kanaquiz.options;

import android.os.Bundle;

import com.noprestige.kanaquiz.themes.ThemeManager;

import androidx.appcompat.app.AppCompatActivity;

public class OptionsScreen extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ThemeManager.setTheme(this);
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new OptionsFragment()).commit();
    }
}
