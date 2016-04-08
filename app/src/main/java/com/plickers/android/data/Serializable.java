package com.plickers.android.data;

import com.eclipsesource.json.JsonValue;

/**
 * General interface implemented by all objects that
 * need conversion form and to JSON for convenience.
 */
public interface Serializable {
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
