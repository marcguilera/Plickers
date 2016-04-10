package com.plickers.android.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.plickers.android.R;
import com.plickers.android.ui.adapters.FiltrableAdapter;

/**
 * A general reusable {@link ListView} with a Title ({@link SectionView}).
 */
public class ListSectionView extends LinearLayout{

    SectionView headView;
    ListView listView;

    public ListSectionView(Context context) {
        this(context,null);
    }

    public ListSectionView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ListSectionView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public ListSectionView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        headView = (SectionView) findViewById(R.id.lsvHead);
        listView = (ListView) findViewById(R.id.lsvList);


        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ListSectionView,
                0, 0);

        boolean clickable = a.getBoolean(R.styleable.ListSectionView_clickableItems,true);
        if(!clickable){
            listView.setSelector(new ColorDrawable(0));
        }
    }

    private void init() {
        inflate(getContext(), R.layout.list_section_view , this);
    }

    public void setTitle(String title){
        headView.setTitle(title);
    }

    public void setColor(int color){
        headView.setColor(color);
    }

    /**
     * Sets the adapter for the list
     */
    public void setAdapter(ListAdapter adapter){
        listView.setAdapter(adapter);
    }

    public ListAdapter getAdapter(){
        return listView.getAdapter();
    }

    public void setListSectionViewItemClickListener(final ListSectionViewItemClickListener listener){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onItemClicked(parent.getItemAtPosition(position));
            }
        });
    }

    public interface ListSectionViewItemClickListener{
        public void onItemClicked(Object item);
    }

}
