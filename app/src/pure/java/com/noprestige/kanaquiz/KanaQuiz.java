package com.noprestige.kanaquiz;

import android.content.Context;

import org.acra.ACRA;
import org.acra.annotation.AcraCore;
import org.acra.annotation.AcraLimiter;
import org.acra.annotation.AcraMailSender;
import org.acra.annotation.AcraToast;

@AcraCore(buildConfigClass = BuildConfig.class)
@AcraMailSender(mailTo = "kanaquizcrashreports@noprestige.com")
@AcraToast(resText = R.string.acra_alert)
@AcraLimiter
public class KanaQuiz extends KanaQuizMain
{
    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);

//        CoreConfigurationBuilder builder = new CoreConfigurationBuilder(this)
//                .setBuildConfigClass(BuildConfig.class);
//        builder.getPluginConfigurationBuilder(MailSenderConfigurationBuilder.class)
//                .setMailTo("kanaquizcrashreports@noprestige.com")
//                .setEnabled(true);
//        builder.getPluginConfigurationBuilder(ToastConfigurationBuilder.class)
//                .setResText(R.string.acra_alert)
//                .setEnabled(true);
//        builder.getPluginConfigurationBuilder(LimiterConfigurationBuilder.class)
//                .setEnabled(true);
//        ACRA.init(this, builder);

        ACRA.init(this);
    }
}
