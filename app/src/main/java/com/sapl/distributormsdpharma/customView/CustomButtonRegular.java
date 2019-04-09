package com.sapl.distributormsdpharma.customView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import com.sapl.distributormsdpharma.R;
import com.sapl.distributormsdpharma.confiq.MyApplication;


/**
 * Created by ACER on 03-02-2017.
 */

public class CustomButtonRegular extends Button {
    public CustomButtonRegular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomButtonRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomButtonRegular(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Nunito_Regular.ttf");
        setTextColor(this.getResources().getColor(R.color.white));
        setBackgroundColor(Color.parseColor(MyApplication.get_session(MyApplication.SESSION_HEADING_BACKGROUND_COLOR)));
        setTypeface(tf ,1);

    }

}
