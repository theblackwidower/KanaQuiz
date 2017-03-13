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

        switch (OptionsControl.getInt(R.string.prefid_action_on_incorrect))
        {
            case OptionsControl.CODE_ON_INCORRECT_MOVE_ON:
                grpOnIncorrect.check(R.id.rdoOnIncorrectMoveOn);
                break;
            case OptionsControl.CODE_ON_INCORRECT_SHOW_ANSWER:
                grpOnIncorrect.check(R.id.rdoOnIncorrectShowAnswer);
                break;
            case OptionsControl.CODE_ON_INCORRECT_RETRY:
                grpOnIncorrect.check(R.id.rdoOnIncorrectAllowRetry);
        }
        grpOnIncorrect.setOnCheckedChangeListener(
            new RadioGroup.OnCheckedChangeListener()
            {
                public void onCheckedChanged(RadioGroup group, int checkedId)
                {
                    int optionCode;
                    switch (checkedId)
                    {
                        case (R.id.rdoOnIncorrectMoveOn):
                            optionCode = OptionsControl.CODE_ON_INCORRECT_MOVE_ON;
                            break;
                        case (R.id.rdoOnIncorrectShowAnswer):
                            optionCode = OptionsControl.CODE_ON_INCORRECT_SHOW_ANSWER;
                            break;
                        case (R.id.rdoOnIncorrectAllowRetry):
                            optionCode = OptionsControl.CODE_ON_INCORRECT_RETRY;
                            break;
                        default:
                            optionCode = 0;
                    }
                    OptionsControl.setInt(R.string.prefid_action_on_incorrect, optionCode);
                }
            }
        );
    }
}
