package com.plickers.android.activities;


import android.content.Context;
import android.os.Bundle;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.plickers.android.R;

/**
 * Base activity for all activities in plickers.
 */
public class PlickersActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    private void init() {
        initActionBart();
    }

    private void initActionBart() {

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(false);
        bar.setDisplayShowTitleEnabled(false);
        bar.setDefaultDisplayHomeAsUpEnabled(true);


        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.top_bar,null);

        bar.setCustomView(v);
        bar.setDisplayShowCustomEnabled(true);
    }
}
