package com.plickers.android.ui.views;

import android.content.Context;
import android.util.AttributeSet;

import com.plickers.android.R;
import com.plickers.android.data.Poll;
import com.plickers.android.ui.adapters.PollListingAdapter;

import java.util.List;

/**
 * Custom implementation of the SearchListView for convenience
 */
public class PollSearchListView extends SearchListView{

    public PollSearchListView(Context context) {
        this(context,null);
    }

    public PollSearchListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PollSearchListView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public PollSearchListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init(){
        setAdapter(new PollListingAdapter(getContext()));
        String noresults = getContext().getString(R.string.no_results);
        setNoResultsText(noresults);
    }
}
