package com.plickers.android.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.plickers.android.R;
import com.plickers.android.data.Choice;
import com.plickers.android.data.Poll;
import com.plickers.android.data.Response;

import java.util.List;

public class PollActivity extends AppCompatActivity {

    private Poll poll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);

        init();
    }

    private void init() {
        poll = (Poll) getIntent().getSerializableExtra("poll");



        fillContent();
        fetchBitmap();
    }

    private void fillContent() {
        TextView title = (TextView)findViewById(R.id.poll_question);
        title.setText(poll.getQuestion().getBody());

        fillChoicesList();
        fillResponsesList();
    }

    private void fillResponsesList() {

        List<Response> responses = poll.getResponses();
        TextView title = (TextView) findViewById(R.id.poll_responses_title);
        String responseString = getResources().getQuantityString(R.plurals.poll_responses_title, 0, responses.size());
        title.setText(responseString);

        ListView responsesList = (ListView) findViewById(R.id.poll_responses_list);


        ResponsesAdapter adapter = new ResponsesAdapter(this,responses);

        responsesList.setAdapter(adapter);

    }

    private void fillChoicesList() {
        ListView choicesList = (ListView) findViewById(R.id.poll_choices_list);

        List<Choice> choices = poll.getQuestion().getChoices();
        ChoicesAdapter adapter = new ChoicesAdapter(this,choices);

        choicesList.setAdapter(adapter);
    }

    private void fetchBitmap() {
        final ImageView iv = (ImageView)findViewById(R.id.poll_thumb);
        ImageLoader loader = ImageLoader.getInstance();
        final ViewManager parent = ((ViewManager)iv.getParent());

        loader.displayImage(poll.getQuestion().getImage(), iv, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                removeImage();
                removeLoading();
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                removeLoading();
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                removeLoading();
            }

            private void removeLoading(){
                ProgressBar bar = (ProgressBar) findViewById(R.id.poll_progress_bar);
                parent.removeView(bar);
            }

            private void removeImage(){
                parent.removeView(iv);
            }


        });
    }
}
