/*
 *    Copyright 2018 T Duke Perry
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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
