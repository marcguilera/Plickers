package com.plickers.android.ui.views;

import android.content.Context;

import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.plickers.android.R;
import com.plickers.android.ui.adapters.FiltrableAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A general listview with a search bar on top.
 */
public class SearchListView extends LinearLayout {

    private SearchView searchView;
    private ListView listView;
    private String noResultsText = "";


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

        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.SearchListView,
                0, 0);

        noResultsText = a.getString(R.styleable.SearchListView_noResultsText);

        init();
    }

    private void init() {
        inflate(getContext(), getLayout() , this);
        searchView = (SearchView) findViewById(R.id.slvSearchBar);
        listView = (ListView) findViewById(R.id.slvList);


        setSearchListViewListener(new SearchListViewListener(){
            @Override
            public void onQueryTextChange(String newText) {
                performFilter(newText);
            }
        });
    }

    /**
     * Can be overridden by the children to use another view
     * The view must have a slvSearchBar and a slvList
     */

    protected int getLayout(){
        return R.layout.search_list_view;
    }

    /**
     * Performs a filter in the list
     * @param query
     */
    private void performFilter(String query){
        FiltrableAdapter adapter = (FiltrableAdapter) listView.getAdapter();
        adapter.getFilter().filter(query);

        if(adapter.isEmpty()){
            Toast.makeText(getContext(),noResultsText,Toast.LENGTH_SHORT).show();
        }
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
