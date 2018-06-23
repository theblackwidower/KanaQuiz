package com.noprestige.kanaquiz.options;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.noprestige.kanaquiz.AppTools;

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
