package com.plickers.android.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.eclipsesource.json.JsonValue;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.plickers.android.R;
import com.plickers.android.data.Poll;
import com.plickers.android.data.Polls;
import com.plickers.android.network.Api;
import com.plickers.android.network.ApiCallback;


public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        initImageLoader();
        dialog = ProgressDialog.show(this, getString(R.string.loading_polls_title), getString(R.string.loading_polls_body));
        loadPolls();
    }

    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader loader = ImageLoader.getInstance();
        loader.init(config);
    }

    private void loadPolls(){

        Api.getPolls(new ApiCallback(){
            @Override
            public void onSuccess(JsonValue response){
                super.onSuccess(response);
                Polls polls = new Polls();
                polls.fromJson(response);
                showList(polls);
            }

            @Override
            public void onFailed(){
                super.onFailed();
                showError();
            }
        });
    }

    //Called when list is ready to be shown (downloaded)
    private void showList(Polls polls){
        hideLoading();
        //The polls show part of the question
        List<Poll> list = polls.getPolls();

        PollAdapter adapter = new PollAdapter(this,list);
        ListView lv = (ListView) findViewById(R.id.polls_list);
        lv.setAdapter(adapter);


        addSearchListener(adapter);
    }

    private void addSearchListener(final PollAdapter adapter) {
        SearchView searchView = (SearchView) findViewById(R.id.poll_search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

                return false;
            }
        });
    }

    private void hideLoading(){
        dialog.hide();
    }

    private void showError(){
        hideLoading();
    }
}
