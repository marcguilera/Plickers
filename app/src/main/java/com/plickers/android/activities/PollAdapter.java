package com.plickers.android.activities;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.plickers.android.R;
import com.plickers.android.data.Poll;
import com.plickers.android.data.Question;
import com.plickers.android.network.ImageLoaderCallback;

import java.text.SimpleDateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.client.cache.Resource;

/**
 * Created by marc on 9/04/16.
 */
public class PollAdapter extends ArrayAdapter<Poll>{

    public PollAdapter(Context context, Poll[] polls) {
        super(context, R.layout.poll_row, polls);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Resources res = getContext().getResources();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View row = inflater.inflate(R.layout.poll_row,parent,false);

        Poll poll = getItem(position);
        Question question = poll.getQuestion();

        TextView questionTV = (TextView) row.findViewById(R.id.poll_question);
        String questionString = question.getBody();

        if(questionString!=null&&!questionString.isEmpty()){
            if(questionString.length()>28){
                questionString = questionString.substring(0,26)+"...";
            }
            questionTV.setText(questionString);
        }else{
            ((ViewManager)row).removeView(questionTV);
        }


        TextView responsesTV = (TextView) row.findViewById(R.id.poll_responses);

        int numResponses = poll.getResponses().size();
        String responseString = res.getQuantityString(R.plurals.answered_x_times, 0, numResponses);

        responsesTV.setText(responseString);

        TextView addedTV = (TextView) row.findViewById(R.id.poll_added_on);
        Date addedDate = poll.getCreated();

        if(addedDate!=null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            String addedString = res.getString(R.string.added_on) + " " +dateFormat.format(poll.getCreated());
            addedTV.setText(addedString);
        }else{
            ((ViewManager)row).removeView(addedTV);
        }


        question.fetchBitmap(new ImageLoaderCallback() {
            @Override
            public void onComplete(Bitmap bm) {
                ImageView imageView = (ImageView) row.findViewById(R.id.polls_thumb);
                if(bm!=null){
                    imageView.setImageBitmap(bm);
                }else{
                    ((ViewManager)row).removeView(imageView);
                }
            }
        });


        return row;
    }


}
