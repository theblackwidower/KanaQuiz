package com.noprestige.kanaquiz.options;

import android.os.Bundle;

import com.noprestige.kanaquiz.AppTools;

import androidx.appcompat.app.AppCompatActivity;

public class OptionsScreen extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        AppTools.initializeManagers(getApplicationContext());
        getFragmentManager().beginTransaction().replace(android.R.id.content, new OptionsFragment()).commit();
    }
}
