package com.plickers.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.eclipsesource.json.JsonValue;
import com.plickers.android.data.Polls;
import com.plickers.android.network.Api;
import com.plickers.android.network.ApiCallback;

import org.json.JSONArray;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private Polls polls = new Polls();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadPolls();
    }

    private void loadPolls(){
        Api.getPolls(new ApiCallback(){
            @Override
            public void onSuccess(JsonValue response){
                polls.fromJson(response);
                Log.d(TAG,polls.getPolls().get(0).getId());
            }
        });
    }
}
