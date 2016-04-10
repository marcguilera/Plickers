package com.plickers.android.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.plickers.android.R;
import com.plickers.android.data.Poll;
import com.plickers.android.data.Question;
import com.plickers.android.data.Response;
import com.plickers.android.ui.adapters.ChoiceListingAdapter;
import com.plickers.android.ui.adapters.FiltrableAdapter;
import com.plickers.android.ui.adapters.ResponseListingAdapter;
import com.plickers.android.ui.views.ListSectionView;
import com.plickers.android.ui.views.TitleView;
import com.plickers.android.data.Choice;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * {@link PlickersActivity} showing a poll. It shows the picture, the question,
 * a list of possible {@link Choice}s and {@link Response}s.
 */
public class QuestionActivity extends PlickersActivity {

    private Poll poll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        init();
    }

    /**
     * Initializes all the elements in the {@link View}.
     */
    private void init(){
        //Fetch the poll passed by the previous activity
        poll = (Poll) getIntent().getSerializableExtra("poll");

        initThumb();
        initTitle();
        initChoices();
        initResponses();
        initMoreInfo();
        initProTip();
        initFilters();
    }

    /**
     * Shows the toast saying it is possible to get more info
     * by tapping on a student.
     */
    private void initProTip() {
        Toast.makeText(this,getString(R.string.hint_responses), Toast.LENGTH_LONG).show();
    }

    /**
     * Sets the title to the body of the {@link Question}.
     */
    private void initTitle() {
        Question question = poll.getQuestion();
        TitleView title = (TitleView) findViewById(R.id.qbTitle);

        if(question.getBody()!=null&&!question.getBody().isEmpty()){
            title.setTitle(question.getBody());
        }else{
            title.setTitle(getString(R.string.no_question));
        }


    }

    /**
     * Fills the list of choices with the adapter.
     */
    private void initChoices() {
        Question question = poll.getQuestion();
        ListSectionView view = (ListSectionView) findViewById(R.id.qbChoices);
        ChoiceListingAdapter adapter = new ChoiceListingAdapter(this,question.getChoices());
        view.setAdapter(adapter);

        int numChoices = poll.getQuestion().getChoices().size();
        String choiceString = getResources().getQuantityString(R.plurals.poll_choices_title, 0, numChoices);
        view.setTitle(choiceString);
    }

    /**
     * Fills the list of responses with the adapter.
     */
    private void initResponses() {
        List<Response> responses = poll.getResponses();
        ListSectionView view = (ListSectionView) findViewById(R.id.qbResponses);
        ResponseListingAdapter adapter = new ResponseListingAdapter(this,responses);
        view.setAdapter(adapter);

        int numResponses = responses.size();
        String responseString = getResources().getQuantityString(R.plurals.poll_responses_title, 0, numResponses);
        view.setTitle(responseString);

        //Listen for taps on a name
        view.setListSectionViewItemClickListener(new ListSectionView.ListSectionViewItemClickListener(){

            @Override
            public void onItemClicked(Object item) {
                Response resp = (Response) item;
                showResponseDialog(resp);
            }
        });
    }

    /**
     * Shows a dialog with extra info about the given response.
     * @param response
     */
    private void showResponseDialog(Response response){
        AlertDialog dialog;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        String student = response.getStudent();
        if(student==null||student.isEmpty()){
            student = getString(R.string.no_student);
        }

        alertDialogBuilder.setTitle(student);
        StringBuilder builder = new StringBuilder();

        String answer = response.getAnswer();
        if(answer==null||answer.isEmpty()){
            answer = getString(R.string.no_answer);
        }

        String card = response.getCard()+"";
        if(card.equals("-1")){
            card = getString(R.string.no_card);
        }

        builder.append(getString(R.string.answer))
                .append(": ")
                .append(answer)
                .append("\n")
                .append(getString(R.string.card))
                .append(": ")
                .append(card);
        alertDialogBuilder.setMessage(builder.toString());
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = alertDialogBuilder.create();
        dialog.show();
    }

    /**
     * Creates the listener for the More info button at the bottom
     */
    private void initMoreInfo() {
        Button btn = (Button) findViewById(R.id.qbMoreInfo);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMoreInfoDialog();
            }
        });
    }

    /**
     * Shows a dialog with more information about the question being
     * shown like date added, number of responses, etc.
     */
    private void showMoreInfoDialog() {
        Question q = poll.getQuestion();
        SimpleDateFormat dt = new SimpleDateFormat("MM-dd-yyyy");
        AlertDialog dialog;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(getString(R.string.more_info));
        StringBuilder builder = new StringBuilder();

        if(q.getBody()!=null&&!q.getBody().isEmpty()){
            builder.append(getString(R.string.question))
                    .append(": ")
                    .append(q.getBody())
                    .append("\n\n");
        }


        if(poll.getCreated()!=null){
            builder.append(getString(R.string.created))
                    .append(": ")
                    .append(dt.format(poll.getCreated()))
                    .append("\n\n");
        }

        if(poll.getModified()!=null){
            builder.append(getString(R.string.modified))
                    .append(": ")
                    .append(dt.format(poll.getModified()))
                    .append("\n\n");
        }

        builder.append(getString(R.string.choices))
                .append(": ")
                .append(poll.getQuestion().getChoices().size())
                .append("\n\n")
                .append(getString(R.string.responses))
                .append(": ")
                .append(poll.getResponses().size())
        ;
        alertDialogBuilder.setMessage(builder.toString());
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = alertDialogBuilder.create();
        dialog.show();
    }

    /**
     * Downloads the thumbnail and if that works it adds it to the top
     * of the {@link QuestionActivity}.
     */
    private void initThumb() {
        final ImageView iv = (ImageView)findViewById(R.id.qbTumb);
        ImageLoader loader = ImageLoader.getInstance();
        final ViewManager parent = ((ViewManager)iv.getParent());

        loader.displayImage(poll.getQuestion().getImage(), iv, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                removeImage();
                removeLoading();
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                removeLoading();
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                removeLoading();
            }

            private void removeLoading(){
                ProgressBar bar = (ProgressBar) findViewById(R.id.qbProgressBar);
                parent.removeView(bar);
            }

            private void removeImage(){
                parent.removeView(iv);
            }
        });
    }

    /**
     * Adds the listener to the checkboxes that filter the {@link Response}s
     * so we are noticed every time there is a change.
     */
    private void initFilters() {
        CheckBox A = (CheckBox) findViewById(R.id.qbCbA);
        CheckBox B = (CheckBox) findViewById(R.id.qbCbB);
        CheckBox C = (CheckBox) findViewById(R.id.qbCbC);
        CheckBox D = (CheckBox) findViewById(R.id.qbCbD);

        CheckBox.OnCheckedChangeListener listener = new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                filter();
            }
        };

        A.setOnCheckedChangeListener(listener);
        B.setOnCheckedChangeListener(listener);
        C.setOnCheckedChangeListener(listener);
        D.setOnCheckedChangeListener(listener);
    }

    /**
     * Called when there is a change in the filter settings, it applies a filter
     * to the list of {@link Response}.
     */
    private void filter(){
        CheckBox A = (CheckBox) findViewById(R.id.qbCbA);
        CheckBox B = (CheckBox) findViewById(R.id.qbCbB);
        CheckBox C = (CheckBox) findViewById(R.id.qbCbC);
        CheckBox D = (CheckBox) findViewById(R.id.qbCbD);

        ListSectionView view = (ListSectionView) findViewById(R.id.qbResponses);

        StringBuilder query = new StringBuilder();
        if(A.isChecked()){
            query.append("A");
        }
        if(B.isChecked()){
            query.append("B");
        }
        if(C.isChecked()){
            query.append("C");
        }
        if(D.isChecked()){
            query.append("D");
        }

        FiltrableAdapter adapter = (FiltrableAdapter)view.getAdapter();
        adapter.getFilter().filter(query.toString());
    }
}
