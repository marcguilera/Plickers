package com.plickers.android.ui.views;

import android.content.Context;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.plickers.android.ui.adapters.FiltrableAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A general listview with a search bar on top.
 */
public class SearchListView extends LinearLayout{

    private SearchView searchView;
    private ListView listView;

    public SearchListView(Context context) {
        this(context,null);
    }

    public SearchListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SearchListView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public SearchListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setOrientation(LinearLayout.VERTICAL);
        addSearchView();
        addListView();
    }

    private void addSearchView() {
        searchView = newSearchView();

        //Add listener, it can be overriden by the user
        setSearchListViewListener(new SearchListViewListener() {
            @Override
            public void onQueryTextChange(String query) {
                //Text has been submitted, let's search
                performFilter(query);
            }
        });

        addView(searchView);
    }

    private void addListView() {
        listView = newListView();
        addView(listView);
    }

    //Can be overriden if needed by the child
    protected SearchView newSearchView(){
        SearchView sv = new SearchView(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        sv.setLayoutParams(params);

        return sv;
    }

    //Can be overridden if needed by the child
    protected ListView newListView(){
        ListView lv = new ListView(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        lv.setLayoutParams(params);
        return lv;
    }

    /**
     * Performs a filter in the list
     * @param query
     */
    private void performFilter(String query){
        FiltrableAdapter adapter = (FiltrableAdapter) listView.getAdapter();
        adapter.getFilter().filter(query);
    }

    /**
     * Sets the adapter for the list
     */
    public void setAdapter(FiltrableAdapter adapter){
        listView.setAdapter(adapter);
    }


    public void setSearchListViewListener(final SearchListViewListener listener){
        //Listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listener.onQueryTextSubmit(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listener.onQueryTextChange(newText);
                return false;
            }
        });
    }

    public void setSearchListViewItemClickListener(final SearchListViewItemClickListener listener){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onItemClicked(parent.getItemAtPosition(position));
            }
        });
    }


    public abstract class SearchListViewListener{
        public void onQueryTextSubmit(String query){}
        public void onQueryTextChange(String query){}
    }

    public interface SearchListViewItemClickListener{
        public void onItemClicked(Object item);
    }
}
