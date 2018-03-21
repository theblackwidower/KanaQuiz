package com.noprestige.kanaquiz.reference;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

public class ReferenceTable extends TableLayout
{
    public ReferenceTable(Context context)
    {
        super(context);
        this.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams
                .WRAP_CONTENT));
    }

    @Override
    public void addView(View view)
    {
        if (view != null)
            super.addView(view);
    }
}
