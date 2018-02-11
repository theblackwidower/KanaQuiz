package com.noprestige.kanaquiz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class AboutScreen extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_screen);

        ((TextView) findViewById(R.id.lblVersionDisplay)).setText(BuildConfig.VERSION_NAME);
    }

    public void toGithub(View view)
    {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/theblackwidower/KanaQuiz")));
    }

    public void toTwitter(View view)
    {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/theblackwidower/")));
    }

    public void toAssetStudioGithub(View view)
    {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/romannurik/AndroidAssetStudio")));
    }

    public void toNotoCjkGithub(View view)
    {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/googlei18n/noto-cjk")));
    }
}
