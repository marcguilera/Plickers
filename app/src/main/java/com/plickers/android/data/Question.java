package com.plickers.android.data;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Question extends DBObject{

    private String body;
    private String image;
    private List<Choice> choices = new ArrayList<>(0);

    @Override
    public void fromJson(JsonValue value) {
        super.fromJson(value);

        if(value==null || !value.isObject()) return;
        JsonObject question = value.asObject();

        body = question.getString("body",null);
        image = question.getString("image", null);

        choices = new ArrayList<>();
        JsonValue choicesJson = question.get("choices");
        //If there where choices...
        if(choicesJson!=null && choicesJson.isArray()){
            JsonArray choicesArray = choicesJson.asArray();
            for(JsonValue choiceJson : choicesArray){
                Choice choice = new Choice();
                choice.fromJson(choiceJson);
                choices.add(choice);
            }
        }

    }


    @Override
    public void toJson(JsonValue value) {

    }

    public String getBody() {
        return body;
    }

    public String getImage() {
        return image;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public void setChoices(Choice ... choices) {
        this.choices = new ArrayList<>(Arrays.asList(choices));
    }
}
