package com.plickers.android.data;

import android.util.Log;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by marc on 8/04/16.
 */
public class Poll extends DBObject{

    private String id;
    private String section;
    private Question question;
    private List<Response> responses = new ArrayList<>(0);

    @Override
    public void fromJson(JsonValue value) {
        super.fromJson(value);

        if(value==null || !value.isObject()) return;

        JsonObject poll= value.asObject();
        id = poll.getString("id",null);
        section = poll.getString("section",null);

        question = new Question();
        JsonValue questionJson = poll.get("question");
        question.fromJson(questionJson);

        responses = new ArrayList<>();
        JsonValue responsesJson = poll.get("responses");

        //If there where responses...
        if(responsesJson!=null && responsesJson.isArray()){
            JsonArray respnsesArray = responsesJson.asArray();
            for(JsonValue responseJson : respnsesArray){
                Response response = new Response();
                response.fromJson(responseJson);
                responses.add(response);
            }
        }

    }

    @Override
    public void toJson(JsonValue value) {

    }

    public String getId() {
        return id;
    }

    public String getSection() {
        return section;
    }

    public Question getQuestion() {
        return question;
    }

    public List<Response> getResponses() {
        return responses;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }

    public void setResponses(Response ... responses) {
        this.responses = new ArrayList<>(Arrays.asList(responses));
    }
}
