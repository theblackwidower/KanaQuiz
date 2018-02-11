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

    private void toSite(String url)
    {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    private void toGithub(String githubAddress)
    {
        toSite("https://github.com/" + githubAddress);
    }

    public void toAppGithub(View view)
    {
        toGithub("theblackwidower/KanaQuiz");
    }

    public void toTwitter(View view)
    {
        toSite("https://twitter.com/theblackwidower/");
    }

    public void toAssetStudioGithub(View view)
    {
        toGithub("romannurik/AndroidAssetStudio");
    }

    public void toNotoCjkGithub(View view)
    {
        toGithub("googlei18n/noto-cjk");
    }
}
