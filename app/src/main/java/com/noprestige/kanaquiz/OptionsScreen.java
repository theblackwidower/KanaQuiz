package com.noprestige.kanaquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

public class OptionsScreen extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_screen);

        RadioGroup grpOnIncorrect = (RadioGroup)findViewById(R.id.grpOnIncorrect);

        if (OptionsControl.compareStrings(R.string.prefid_on_incorrect, R.string.prefid_on_incorrect_default))
            grpOnIncorrect.check(R.id.rdoOnIncorrectMoveOn);
        else if (OptionsControl.compareStrings(R.string.prefid_on_incorrect, R.string.prefid_on_incorrect_show_answer))
            grpOnIncorrect.check(R.id.rdoOnIncorrectShowAnswer);
        else if (OptionsControl.compareStrings(R.string.prefid_on_incorrect, R.string.prefid_on_incorrect_retry))
            grpOnIncorrect.check(R.id.rdoOnIncorrectAllowRetry);

        grpOnIncorrect.setOnCheckedChangeListener(
            new RadioGroup.OnCheckedChangeListener()
            {
                public void onCheckedChanged(RadioGroup group, int checkedId)
                {
                    String optionCode;
                    switch (checkedId)
                    {
                        case (R.id.rdoOnIncorrectMoveOn):
                            optionCode = getResources().getString(R.string.prefid_on_incorrect_default);
                            break;
                        case (R.id.rdoOnIncorrectShowAnswer):
                            optionCode = getResources().getString(R.string.prefid_on_incorrect_show_answer);
                            break;
                        case (R.id.rdoOnIncorrectAllowRetry):
                            optionCode = getResources().getString(R.string.prefid_on_incorrect_retry);
                            break;
                        default:
                            optionCode = "";
                    }
                    OptionsControl.setString(R.string.prefid_on_incorrect, optionCode);
                }
            }
        );
    }
}
