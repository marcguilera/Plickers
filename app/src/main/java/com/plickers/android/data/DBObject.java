package com.plickers.android.data;

import android.os.Parcelable;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.plickers.android.network.Api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Every object coming from the {@link Api} extends this object
 * so that it contains a modified, created and id.
 */
public abstract class DBObject implements Serializable{
    Date modified;
    Date created;
    String id;

    private static final long serialVersionUID = 1L;

    @Override
    public void fromJson(JsonValue value) {
        if(value==null || !value.isObject()) return;


        JsonObject poll= value.asObject();
        id = poll.getString("id",null);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        String createdString = poll.getString("created",null);
        if(createdString!=null){
            try {
                created = format.parse(createdString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        String modifiedString = poll.getString("modified",null);
        if(modifiedString!=null){
            try {
                modified = format.parse(modifiedString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void toJson(JsonValue value) {

    }

    public Date getModified() {
        return modified;
    }

    public Date getCreated() {
        return created;
    }

    public String getId() {
        return id;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setId(String id) {
        this.id = id;
    }
}


