package com.plickers.android.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

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

        //Add click listener
        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Poll poll = (Poll) parent.getItemAtPosition(position);
                Intent intent = new Intent(view.getContext(), PollActivity.class);
                intent.putExtra("poll",poll);
                startActivity(intent);
            }
        };

        lv.setOnItemClickListener(listener);

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
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(R.string.err_dialog_title);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setMessage(R.string.err_dialog_message);
        alertDialogBuilder.setPositiveButton("Retry", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                loadPolls();
            }
        });

        alertDialogBuilder.create().show();
    }
}
