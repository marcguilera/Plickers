package com.plickers.android.activities;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

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


public class MainActivity extends ListActivity {
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        initImageLoader();
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
        Poll[] arr = list.toArray(new Poll[list.size()]);

        PollAdapter adapter = new PollAdapter(this,arr);
        getListView().setAdapter(adapter);
    }

    private void hideLoading(){

    }

    private void showError(){
        hideLoading();
    }
}
