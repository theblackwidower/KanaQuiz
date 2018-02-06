package com.noprestige.kanaquiz.options;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class OptionsScreen extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new OptionsFragment()).commit();
    }
}
