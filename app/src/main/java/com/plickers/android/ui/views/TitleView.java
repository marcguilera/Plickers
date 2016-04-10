package com.plickers.android.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.plickers.android.R;

/**
 * A title with background.
 */
public class TitleView extends FrameLayout {

    TextView title;

    public TitleView(Context context) {
        this(context,null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();

        FrameLayout frame = (FrameLayout) findViewById(R.id.tvFrame);

        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.TitleView,
                0, 0);

        String titleString = a.getString(R.styleable.TitleView_titleString);
        int titleColor = a.getInt(R.styleable.TitleView_titleColor, android.R.color.white);
        int bgColor = a.getInt(R.styleable.TitleView_backgroundColor, android.R.color.holo_blue_dark);

        title = (TextView) frame.findViewById(R.id.tvTitle);
        title.setText(titleString);

        title.setTextColor(titleColor);
        frame.setBackgroundColor(bgColor);
    }

    private void init() {
        inflate(getContext(), R.layout.title_view , this);
    }

    public void setTitle(String titleString){
        title.setText(titleString);
    }
}
