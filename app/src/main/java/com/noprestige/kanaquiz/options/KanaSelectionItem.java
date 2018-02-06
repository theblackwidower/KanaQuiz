package com.noprestige.kanaquiz.options;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.noprestige.kanaquiz.R;

public class KanaSelectionItem extends LinearLayout
{
    private String prefId;

    private TextView lblTitle;
    private TextView lblContents;
    private CheckBox chkCheckBox;

    public KanaSelectionItem(Context context)
    {
        super(context);
        init(null, 0);
    }

    public KanaSelectionItem(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs, 0);
    }

    public KanaSelectionItem(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle)
    {
        // Set up initial objects
        LayoutInflater.from(getContext()).inflate(R.layout.kana_selection_item, this);

        lblTitle = findViewById(R.id.lblTitle);
        lblContents = findViewById(R.id.lblContents);
        chkCheckBox = findViewById(R.id.chkCheckBox);

        this.setOnClickListener(
                new OnClickListener()
                {
                    public void onClick(View view)
                    {
                        chkCheckBox.toggle();
                    }
                }
        );

        chkCheckBox.setOnCheckedChangeListener(
                new OnCheckedChangeListener()
                {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                    {
                        OptionsControl.setBoolean(getPrefId(), isChecked);
                    }
                }
        );

        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.KanaSelectionItem, defStyle, 0);

        String title = a.getString(R.styleable.KanaSelectionItem_title);
        String contents = a.getString(R.styleable.KanaSelectionItem_contents);
        String prefId = a.getString(R.styleable.KanaSelectionItem_prefId);

        if (title != null)
            setTitle(title);
        if (contents != null)
            setContents(contents);
        if (prefId != null)
            setPrefId(prefId);

        a.recycle();
    }

    public String getTitle()
    {
        return (String) lblTitle.getText();
    }

    public String getContents()
    {
        return (String) lblContents.getText();
    }

    public String getPrefId()
    {
        return prefId;
    }

    public void setTitle(int resId)
    {
        setTitle(getResources().getString(resId));
    }

    public void setTitle(String title)
    {
        lblTitle.setText(title);
    }

    public void setContents(int resId)
    {
        setContents(getResources().getString(resId));
    }

    public void setContents(String contents)
    {
        lblContents.setText(contents);
    }

    public void setPrefId(int resId)
    {
        setPrefId(getResources().getString(resId));
    }

    public void setPrefId(String prefId)
    {
        this.prefId = prefId;

        if (!isInEditMode())
            chkCheckBox.setChecked(OptionsControl.getBoolean(getPrefId()));
    }
}
