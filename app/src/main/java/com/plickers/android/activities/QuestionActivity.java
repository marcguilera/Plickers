package com.plickers.android.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.plickers.android.R;

public class QuestionActivity extends PlickersActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        init();
    }

    private void init(){

    }
}
