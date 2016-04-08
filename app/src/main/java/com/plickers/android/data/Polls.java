package com.plickers.android.data;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonValue;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by marc on 8/04/16.
 */
public class Polls implements Serializable {

    List<Poll> polls = new ArrayList<>();

    @Override
    public void fromJson(JsonValue value) {

        //Make sure an array was passed
        if(value==null || !value.isArray()) return;

        JsonArray pollsJson = value.asArray();

        //Add the list with the polls in the json array
        polls = new ArrayList<>(pollsJson.size());
        for(JsonValue pollJson:pollsJson){
            Poll poll = new Poll();
            poll.fromJson(pollJson);
            polls.add(poll);
        }
    }

    @Override
    public void toJson(JsonValue value) {

    }

    public List<Poll> getPolls() {
        return polls;
    }
}
