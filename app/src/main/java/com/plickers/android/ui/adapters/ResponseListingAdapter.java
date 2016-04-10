package com.plickers.android.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.plickers.android.R;
import com.plickers.android.data.Choice;
import com.plickers.android.data.Response;

import java.util.List;

/**
 * Generates the {@link View} of a single response containing the student and the
 * option.
 */
public class ResponseListingAdapter extends FiltrableAdapter<Response> {
    public ResponseListingAdapter(Context context, List<Response> resource) {
        super(context, R.layout.response_listing_row, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View row = inflater.inflate(R.layout.response_listing_row,parent,false);
        Response response = getItem(position);

        TextView studentTF = (TextView) row.findViewById(R.id.response_student);

        String studentString = response.getStudent();

        if(studentString!=null&&!studentString.isEmpty()){
            if(studentString.length()>28){
                studentString = studentString.substring(0,26)+"...";
            }
            studentTF.setText(studentString);
        }else{
            studentTF.setTextColor(Color.RED);
            studentTF.setText(row.getResources().getString(R.string.no_student));
        }

        TextView optionTF = (TextView) row.findViewById(R.id.response_choice);
        String answer = response.getAnswer();

        if(answer!=null&&!studentString.isEmpty()){
            optionTF.setText(answer);
        }else{
            optionTF.setTextColor(Color.RED);
            optionTF.setText(row.getResources().getString(R.string.no_answer));
        }

        return row;
    }

    @Override
    public boolean performFilter(Response current, String query) {
        return query.contains(current.getAnswer());
    }
}