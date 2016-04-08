package com.plickers.android.data;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.sql.Date;

/**
 * Created by marc on 8/04/16.
 */
public abstract class DBObject implements Serializable{
    Date modified;
    Date created;
    String id;

    @Override
    public void fromJson(JsonValue value) {
        if(value==null || !value.isObject()) return;

        JsonObject poll= value.asObject();
        id = poll.getString("id",null);
    }

    @Override
    public void toJson(JsonValue value) {

    }
}


