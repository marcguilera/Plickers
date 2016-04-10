package com.plickers.android.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.plickers.android.R;
import com.plickers.android.data.Choice;
import com.plickers.android.data.Poll;
import com.plickers.android.data.Question;
import com.plickers.android.data.Response;
import com.plickers.android.ui.adapters.ChoiceListingAdapter;
import com.plickers.android.ui.adapters.ResponseListingAdapter;
import com.plickers.android.ui.views.ListSectionView;
import com.plickers.android.ui.views.TitleView;

import java.text.SimpleDateFormat;
import java.util.List;

public class QuestionActivity extends PlickersActivity {

    Poll poll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        init();
    }

    private void init(){
        poll = (Poll) getIntent().getSerializableExtra("poll");

        initThumb();
        initTitle();
        initChoices();
        initResponses();
        initMoreInfo();
        initProTip();
    }

    private void initProTip() {
        Toast.makeText(this,getString(R.string.hint_responses), Toast.LENGTH_LONG).show();
    }

    private void initTitle() {
        Question question = poll.getQuestion();
        TitleView title = (TitleView) findViewById(R.id.qbTitle);
        title.setTitle(question.getBody());

    }

    private void initChoices() {
        Question question = poll.getQuestion();
        ListSectionView view = (ListSectionView) findViewById(R.id.qbChoices);
        ChoiceListingAdapter adapter = new ChoiceListingAdapter(this,question.getChoices());
        view.setAdapter(adapter);
    }

    private void initResponses() {
        List<Response> responses = poll.getResponses();
        ListSectionView view = (ListSectionView) findViewById(R.id.qbResponses);
        ResponseListingAdapter adapter = new ResponseListingAdapter(this,poll.getResponses());
        view.setAdapter(adapter);

        int numResponses = poll.getResponses().size();
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

    private void showResponseDialog(Response response){
        AlertDialog dialog;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(response.getStudent());
        StringBuilder builder = new StringBuilder();
        builder.append(getString(R.string.answer))
                .append(": ")
                .append(response.getAnswer())
                .append("\n")
                .append(getString(R.string.card))
                .append(": ")
                .append(response.getCard());
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

    private void initMoreInfo() {
        Button btn = (Button) findViewById(R.id.qbMoreInfo);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMoreInfoDialog();
            }
        });
    }

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
}
