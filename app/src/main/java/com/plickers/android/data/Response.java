package com.plickers.android.data;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

/**
 * Created by marc on 8/04/16.
 */
public class Response extends DBObject{
    private String student;
    private String answer;
    private int card;

    @Override
    public void fromJson(JsonValue value) {
        super.fromJson(value);

        if(value==null || !value.isObject()) return;
        JsonObject response = value.asObject();

        //I'd probably store more info for the student such as name
        //so i'd create an object for it... for now I've limited it
        //to the email string.
        student = response.getString("student",null);
        answer = response.getString("answer",null);
        card = response.getInt("card", -1); //Assuming no card can be -1!
    }

    @Override
    public void toJson(JsonValue value) {

    }

    public void setStudent(String student) {
        this.student = student;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setCard(int card) {
        this.card = card;
    }

    public String getStudent() {
        return student;
    }

    public String getAnswer() {
        return answer;
    }

    public int getCard() {
        return card;
    }
}
