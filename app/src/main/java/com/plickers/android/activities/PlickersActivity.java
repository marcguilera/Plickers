package com.plickers.android.activities;


import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
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
        initActionBar();
        initImageDownloader();
    }

    private void initImageDownloader() {
        ImageLoader loader = ImageLoader.getInstance();
        if(loader.isInited()) return;

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        loader.init(config);
    }

    private void initActionBar() {

        ActionBar bar = getSupportActionBar();

        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.top_bar,null);
        bar.setCustomView(v);
        bar.setDisplayShowTitleEnabled(false);
        bar.setDisplayShowCustomEnabled(true);
    }
}
