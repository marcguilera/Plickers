package com.plickers.android.data;

import android.graphics.Bitmap;
import android.view.View;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.plickers.android.network.ImageLoaderCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marc on 8/04/16.
 */
public class Question extends DBObject{

    private String body;
    private String image;
    private List<Choice> choices = new ArrayList<>(0);
    private Bitmap imageBitmap;

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

    public void fetchBitmap(final ImageLoaderCallback callback){
        if(imageBitmap!=null) {
            callback.onComplete(imageBitmap);
            return;
        }

        ImageLoader loader = ImageLoader.getInstance();
        loader.loadImage(image, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                imageBitmap=loadedImage;
                callback.onComplete(imageBitmap);
            }
        });
    }
}
