package com.plickers.android.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.plickers.android.R;

/**
 * Reusable section with a title, a separator and some customizable content.
 */
public class SectionView extends LinearLayout{
    TextView title;

    public SectionView(Context context) {
        this(context,null);
    }

    public SectionView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SectionView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public SectionView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();

        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.SectionView,
                0, 0);

        String titleString = a.getString(R.styleable.SectionView_titleSectionString);
        int color = a.getInt(R.styleable.SectionView_styleColor, android.R.color.black);

        title = (TextView) findViewById(R.id.svTitle);

        setTitle(titleString);
        setColor(color);
    }

    private void init() {
        inflate(getContext(), R.layout.section_view , this);
    }

    public void setColor(int color){
        title.setTextColor(color);
        View separator = findViewById(R.id.separator);
        separator.setBackgroundColor(color);
    }

    public void setTitle(String titleString){
        title.setText(titleString);
    }
}
