package com.plickers.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.plickers.android.network.Api;
import com.plickers.android.network.ApiCallback;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadPolls();
    }

    private void loadPolls(){
        Api.getPolls(new ApiCallback(){
            @Override
            public void onSuccess(Object response){
                Log.d(TAG,response.toString());
            }
        });
    }
}
