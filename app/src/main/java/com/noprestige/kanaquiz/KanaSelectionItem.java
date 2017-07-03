package com.noprestige.kanaquiz;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
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

    public KanaSelectionItem(Context context) {
        super(context);
        init(null, 0);
    }

    public KanaSelectionItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public KanaSelectionItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.KanaSelectionItem, defStyle, 0);

        title = a.getString(R.styleable.KanaSelectionItem_title);
        baseContents = a.getString(R.styleable.KanaSelectionItem_baseContents);
        diacritics = a.getString(R.styleable.KanaSelectionItem_diacritics);
        prefId = a.getString(R.styleable.KanaSelectionItem_prefId);

        a.recycle();

        Context context = this.getContext();  //TODO: Fix this

        // Set up initial objects
        this.setOrientation(LinearLayout.VERTICAL);

        LinearLayout textContainer = new LinearLayout(context);
        LinearLayout mainContainer = new LinearLayout(context);
        ImageView imgBorder = new ImageView(context);
        lblTitle = new TextView(context);
        lblContents = new TextView(context);
        chkCheckBox = new CheckBox(context);

        textContainer.setOrientation(LinearLayout.VERTICAL);
        textContainer.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        mainContainer.setOrientation(LinearLayout.HORIZONTAL);
        mainContainer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        imgBorder.setImageResource(R.drawable.border);

        lblTitle.setTypeface(null, Typeface.BOLD);
        lblTitle.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        lblContents.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        chkCheckBox.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        chkCheckBox.setGravity(Gravity.CENTER_VERTICAL);

        this.setOnClickListener(
            new OnClickListener()
            {
                public void onClick(View view)
                {
                    chkCheckBox.toggle();
                }
            }
        );

        textContainer.addView(lblTitle);
        textContainer.addView(lblContents);

        mainContainer.addView(textContainer);
        mainContainer.addView(chkCheckBox);

        this.addView(mainContainer);
        this.addView(imgBorder);

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

    public String getTitle() {
        return title;
    }
    public String getContents()
    {
        String contents = baseContents;
        if (diacritics != null && (isInEditMode() || OptionsControl.getBoolean(R.string.prefid_diacritics)))
            contents += " " + diacritics;
        return contents;
    }
    public String getPrefId() {
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
