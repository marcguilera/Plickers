package com.plickers.android.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.plickers.android.R;
import com.plickers.android.data.Choice;

import java.util.List;

/**
 * Created by marc on 10/04/16.
 */
public class ChoiceListingAdapter extends ArrayAdapter<Choice> {
    public ChoiceListingAdapter(Context context, List<Choice> resources) {
        super(context, R.layout.choice_listing_row, resources);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View row = inflater.inflate(R.layout.choice_listing_row,parent,false);

        Choice choice = getItem(position);
        TextView bodyLV = (TextView) row.findViewById(R.id.choice_body);

        if(choice.getBody()!=null && !choice.getBody().isEmpty()){
            bodyLV.setText(choice.getBody());
        }else{
            bodyLV.setText(row.getResources().getString(R.string.no_choice));
            bodyLV.setTextColor(Color.RED);
        }

        if(!choice.isCorrect()){
            ImageView indicator = (ImageView) row.findViewById(R.id.choice_correct_indicator);
            Drawable d = getContext().getDrawable(android.R.drawable.presence_offline);
            indicator.setImageDrawable(d);
        }

        return row;
    }
}
