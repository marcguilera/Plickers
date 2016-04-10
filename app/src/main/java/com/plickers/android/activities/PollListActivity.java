package com.plickers.android.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewManager;
import android.view.Window;
import android.widget.ProgressBar;

import com.eclipsesource.json.JsonValue;
import com.plickers.android.R;
import com.plickers.android.data.Poll;
import com.plickers.android.data.Polls;
import com.plickers.android.data.Response;
import com.plickers.android.network.Api;
import com.plickers.android.network.ApiCallback;
import com.plickers.android.ui.adapters.PollListingAdapter;
import com.plickers.android.ui.views.SearchListView;

public class PollListActivity extends PlickersActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll_list);

        init();
    }

    private void init() {
        loadPolls();

        //Click listener on one of the polls
        final SearchListView listView = (SearchListView) findViewById(R.id.lvSearch);
        listView.setSearchListViewItemClickListener(new SearchListView.SearchListViewItemClickListener(){

            @Override
            public void onItemClicked(Object item) {
                Poll poll = (Poll) item;
                goToQuestion(poll);
            }
        });
    }

    private void goToQuestion(Poll poll){
        //Go to the item
        final SearchListView listView = (SearchListView) findViewById(R.id.lvSearch);
        Intent intent = new Intent(listView.getContext(), QuestionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("poll",poll);
        startActivity(intent);
    }

    private void loadPolls() {
        //Show loading dialog
        final ProgressDialog loading = ProgressDialog.show(this, getString(R.string.loading_polls_title), getString(R.string.loading_polls_body));
        final View view = findViewById(R.id.pbBody);
        view.setVisibility(View.INVISIBLE);


        Api.getPolls(new ApiCallback(){
            public void onSuccess(JsonValue response){
                //Read the response into the stucture of polls
                Polls polls = new Polls();
                polls.fromJson(response);

                //Assign the adapter to the list
                SearchListView listView = (SearchListView) findViewById(R.id.lvSearch);
                PollListingAdapter adapter = new PollListingAdapter(listView.getContext(),polls.getPolls());
                listView.setAdapter(adapter);
                done();
            }

            public void onError(int errorCode){
                super.onError(errorCode);
                done();
                //Remove the progress bar from the view
                showErrorDialog(errorCode);
            }

            private void done(){
                loading.hide();
                view.setVisibility(View.VISIBLE);
            }
        });

    }

    private void showErrorDialog(int code){
        AlertDialog dialog;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        final View view = findViewById(R.id.pbBody);
        view.setVisibility(View.INVISIBLE);

        String title = getString(R.string.err_dialog_title);
        if(code!=0) title += " - "+code;


        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setMessage(getString(R.string.err_dialog_message));
        alertDialogBuilder.setPositiveButton(getString(R.string.retry), new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                view.setVisibility(View.VISIBLE);
                loadPolls();
            }
        });
        dialog = alertDialogBuilder.create();
        dialog.show();
    }
}
