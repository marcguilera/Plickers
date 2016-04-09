package com.plickers.android.ui.adapters;

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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.plickers.android.R;
import com.plickers.android.data.Poll;
import com.plickers.android.data.Question;
import com.plickers.android.network.ImageLoaderCallback;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by marc on 9/04/16.
 */
public class PollListingAdapter extends FiltrableAdapter<Poll> {

    public PollListingAdapter(Context context) {
        this(context, new ArrayList<Poll>());
    }

    public PollListingAdapter(Context context, List<Poll> polls) {
        super(context, R.layout.poll_listing_row, polls);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Resources res = getContext().getResources();
        LayoutInflater inflater = LayoutInflater.from(getContext());

        //Grab the poll
        final View row = inflater.inflate(R.layout.poll_listing_row,parent,false);

        Poll poll = getItem(position);
        Question question = poll.getQuestion();

        TextView questionTV = (TextView) row.findViewById(R.id.tfQuestion);
        String questionString = question.getBody();


        if(questionString!=null&&!questionString.isEmpty()){
            if(questionString.length()>34){
                questionString = questionString.substring(0,31)+"...";
            }
            questionTV.setText(questionString);
        }else{
            ((ViewManager)row).removeView(questionTV);
        }


        TextView responsesTV = (TextView) row.findViewById(R.id.tfResponsesCount);

        int numResponses = poll.getResponses().size();
        String responseString = res.getQuantityString(R.plurals.answered_x_times, 0, numResponses);

        responsesTV.setText(responseString);

        TextView addedTV = (TextView) row.findViewById(R.id.tfAddedOn);
        Date addedDate = poll.getCreated();

        if(addedDate!=null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            String addedString = res.getString(R.string.added_on) + " " +dateFormat.format(poll.getCreated());
            addedTV.setText(addedString);
        }else{
            ((ViewManager)row).removeView(addedTV);
        }

        return row;
    }

    @Override
    public boolean performFilter(Poll current, String query) {
        return current.getQuestion().getBody().toLowerCase().contains(query.toLowerCase());
    }
}