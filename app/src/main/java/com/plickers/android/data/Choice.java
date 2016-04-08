package com.plickers.android.data;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.ArrayList;

/**
 * Created by marc on 8/04/16.
 */
public class Choice extends DBObject{
    private String body;
    private boolean correct;

    @Override
    public void fromJson(JsonValue value) {
        super.fromJson(value);

        if(value==null || !value.isObject()) return;
        JsonObject choice = value.asObject();

        body = choice.getString("body",null);
        correct = choice.getBoolean("correct",false);
    }

    @Override
    public void toJson(JsonValue value) {

    }


    public String getBody() {
        return body;
    }

    public boolean isCorrect() {
        return correct;
    }
}
