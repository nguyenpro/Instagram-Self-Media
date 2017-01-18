package com.npc.instafeed.ui.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by USER on 16/01/2017.
 */

public class FontTextView extends TextView {

    public FontTextView(Context context) {
        super(context);
        Typeface type = Typeface.createFromAsset(getContext().getAssets(),"fonts/madurai.ttf");
        setTypeface(type);
    }

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface type = Typeface.createFromAsset(getContext().getAssets(),"fonts/madurai.ttf");
        setTypeface(type);
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface type = Typeface.createFromAsset(getContext().getAssets(),"fonts/madurai.ttf");
        setTypeface(type);
    }
}
