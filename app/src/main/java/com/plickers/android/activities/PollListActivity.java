package com.plickers.android.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.eclipsesource.json.JsonValue;
import com.plickers.android.R;
import com.plickers.android.data.Polls;
import com.plickers.android.network.Api;
import com.plickers.android.network.ApiCallback;
import com.plickers.android.ui.adapters.PollListingAdapter;
import com.plickers.android.ui.views.SearchListView;

public class PollListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll_list);

        init();
    }

    private void init() {
        loadPolls();
    }

    private void loadPolls() {
        Api.getPolls(new ApiCallback(){
            public void onSuccess(JsonValue response){
                //Read the response into the stucture of polls
                Polls polls = new Polls();
                polls.fromJson(response);

                //Assign the adapter to the list
                SearchListView listView = (SearchListView) findViewById(R.id.lvSearch);
                PollListingAdapter adapter = new PollListingAdapter(listView.getContext(),polls.getPolls());
                listView.setAdapter(adapter);
            }
        });
    }
}
