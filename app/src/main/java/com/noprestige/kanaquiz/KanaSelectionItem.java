package com.noprestige.kanaquiz;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class KanaSelectionItem extends LinearLayout
{
    private String title;
    private String baseContents;
    private String diacritics;
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
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.KanaSelectionItem, defStyle, 0);

        title = a.getString(R.styleable.KanaSelectionItem_title);
        baseContents = a.getString(R.styleable.KanaSelectionItem_baseContents);
        diacritics = a.getString(R.styleable.KanaSelectionItem_diacritics);
        prefId = a.getString(R.styleable.KanaSelectionItem_prefId);

        a.recycle();

        if (prefId == null)
            prefId = "";

        // Set up initial objects
        LayoutInflater.from(getContext()).inflate(R.layout.kana_selection_item, this);

        lblTitle = (TextView) findViewById(R.id.lblTitle);
        lblContents = (TextView) findViewById(R.id.lblContents);
        chkCheckBox = (CheckBox) findViewById(R.id.chkCheckBox);

        this.setOnClickListener(
                new OnClickListener()
                {
                    public void onClick(View view)
                    {
                        chkCheckBox.toggle();
                    }
                }
        );

        updateObject();
    }

    private void updateObject()
    {
        lblTitle.setText(getTitle());
        lblContents.setText(getContents());

        if (!isInEditMode())
            chkCheckBox.setChecked(OptionsControl.getBoolean(getPrefId()));

        chkCheckBox.setOnCheckedChangeListener(
                new android.widget.CompoundButton.OnCheckedChangeListener()
                {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                    {
                        OptionsControl.setBoolean(getPrefId(), isChecked);
                    }
                }
        );
    }

    public String getTitle()
    {
        return title;
    }

    public String getContents()
    {
        String contents = baseContents;
        if (diacritics != null && (isInEditMode() || OptionsControl.getBoolean(R.string.prefid_diacritics)))
            contents += " " + diacritics;
        return contents;
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
        this.title = title;
        updateObject();
    }

    public void setBaseContents(int resId)
    {
        setBaseContents(getResources().getString(resId));
    }

    public void setBaseContents(String baseContents)
    {
        this.baseContents = baseContents;
        updateObject();
    }

    public void setDiacritics(int resId)
    {
        setDiacritics(getResources().getString(resId));
    }

    public void setDiacritics(String diacritics)
    {
        this.diacritics = diacritics;
        updateObject();
    }

    public void setPrefId(int resId)
    {
        setPrefId(getResources().getString(resId));
    }

    public void setPrefId(String prefId)
    {
        this.prefId = prefId;
        updateObject();
    }
}
