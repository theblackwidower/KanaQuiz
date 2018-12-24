package com.noprestige.kanaquiz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.noprestige.kanaquiz.themes.ThemeManager;

import androidx.appcompat.app.AppCompatActivity;

import static android.graphics.Paint.UNDERLINE_TEXT_FLAG;

public class AboutScreen extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ThemeManager.setTheme(this);
        setContentView(R.layout.activity_about_screen);

        ((TextView) findViewById(R.id.lblVersionDisplay)).setText(BuildConfig.VERSION_NAME);

        if (KanaQuiz.isFirebaseInstalled())
        {
            ((TextView) findViewById(R.id.lblDisclosureStart)).setText(R.string.firebase_disclosure);
            ((TextView) findViewById(R.id.lblDisclosureDetails)).setText(R.string.firebase_details);
        }
        else if (KanaQuiz.isAcraInstalled())
        {
            ((TextView) findViewById(R.id.lblDisclosureStart)).setText(R.string.acra_disclosure);
            ((TextView) findViewById(R.id.lblDisclosureDetails)).setText(R.string.acra_details);
        }
        else
            ((ViewManager) findViewById(R.id.lblPrivacyDisclosure).getParent())
                    .removeView(findViewById(R.id.lblPrivacyDisclosure));

        String translatorCreditUrl = getResources().getString(R.string.translator_credit_url);

        TextView translatorCredit = findViewById(R.id.lblTranslatorCredit);

        if (translatorCreditUrl.isEmpty())
            translatorCredit.setTextColor(ThemeManager.getThemeColour(this, android.R.attr.textColorTertiary));
        else
        {
            //ref: https://stackoverflow.com/questions/8033316/to-draw-an-underline-below-the-textview-in-android
            translatorCredit.setPaintFlags(translatorCredit.getPaintFlags() | UNDERLINE_TEXT_FLAG);

            translatorCredit.setOnClickListener(view -> toSite(translatorCreditUrl));
        }

        if (!ThemeManager.isLightTheme(this))
            ((ImageView) findViewById(R.id.imgGitHubLogo)).setImageResource(R.drawable.ic_github_logo_white);
    }

    private void toSite(String url)
    {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    private void toGithub(String githubAddress)
    {
        toSite("https://github.com/" + githubAddress);
    }

    public void toPrivacyPolicy(View view)
    {
        toGithub("theblackwidower/KanaQuiz/blob/master/privacy_policy.md");
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
