package com.noprestige.kanaquiz;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AboutScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_screen);

        TextView lblVersionDisplay = (TextView) findViewById(R.id.lblVersionDisplay);
        lblVersionDisplay.setText(BuildConfig.VERSION_NAME);
        /*
        try
        {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            lblVersionDisplay.setText(pInfo.versionName);
        }
        catch (PackageManager.NameNotFoundException ex)
        {
            lblVersionDisplay.setText("(Version unknown)");
        }*/


    }

    public void toGithub(View view)
    {
        Uri uri = Uri.parse("https://github.com/theblackwidower/KanaQuiz");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

}
