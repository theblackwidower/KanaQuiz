package com.noprestige.kanaquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OptionsScreen extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new OptionsFragment())
                .commit();
    }
}
