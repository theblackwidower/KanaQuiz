package com.noprestige.kanaquiz;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static android.graphics.Paint.UNDERLINE_TEXT_FLAG;

public class AboutScreen extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_screen);

        ((TextView) findViewById(R.id.lblVersionDisplay)).setText(BuildConfig.VERSION_NAME);

        if (!AppTools.isFirebaseInstalled())
            ((ViewManager) findViewById(R.id.lblFirebaseDisclosure).getParent())
                    .removeView(findViewById(R.id.lblFirebaseDisclosure));

        String translatorCreditUrl = getResources().getString(R.string.translator_credit_url);

        if (!translatorCreditUrl.isEmpty())
        {
            TextView translatorCredit = findViewById(R.id.lblTranslatorCredit);

            translatorCredit.setTextColor((Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) ?
                    getTheme().obtainStyledAttributes(new int[]{android.R.attr.colorAccent}).getColor(0, 0) :
                    getResources().getColor(R.color.secondaryColor));
            //ref: https://stackoverflow.com/questions/8033316/to-draw-an-underline-below-the-textview-in-android
            translatorCredit.setPaintFlags(translatorCredit.getPaintFlags() | UNDERLINE_TEXT_FLAG);

            translatorCredit.setOnClickListener(view -> toSite(translatorCreditUrl));
        }
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
