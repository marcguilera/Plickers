package com.plickers.android.data;

import android.content.Intent;

import com.eclipsesource.json.JsonValue;

/**
 * General interface implemented by all objects that
 * need conversion form and to JSON for convenience.
 * extends {@link java.io.Serializable} so they are
 * serializable between {@link Intent}
 */
public interface Serializable extends java.io.Serializable{
    /**
     * Fills the serializable object with the information in the
     * JsonValue.
     * @param value
     */
    public void fromJson(JsonValue value);

    /**
     * Fills the given JsonValue with the values in the object.
     * @return JsonValue
     */
    public void toJson(JsonValue value);
}
