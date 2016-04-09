package com.plickers.android.activities;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.plickers.android.R;
import com.plickers.android.data.Choice;
import com.plickers.android.data.Poll;
import com.plickers.android.data.Question;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by marc on 9/04/16.
 */
public class ChoicesAdapter extends ArrayAdapter<Choice> {
    public ChoicesAdapter(Context context, List<Choice> resource) {
        super(context, R.layout.choices_row, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View row = inflater.inflate(R.layout.choices_row,parent,false);

        Choice choice = getItem(position);
        TextView bodyLV = (TextView) row.findViewById(R.id.choice_body);
        bodyLV.setText(choice.getBody());

        if(!choice.isCorrect()){
            ImageView indicator = (ImageView) row.findViewById(R.id.choice_correct_indicator);
            Drawable d = getContext().getDrawable(android.R.drawable.presence_offline);
            indicator.setImageDrawable(d);
        }

        return row;
    }
}
