package com.lithiumsheep.jacketapp.ui.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.lithiumsheep.jacketapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MultiTransitionActivity extends AppCompatActivity {

    @BindView(R.id.image)
    ImageView imageView;
    @BindView(R.id.text_name)
    TextView nameText;
    @BindView(R.id.text_date)
    TextView dateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_start);
        ButterKnife.bind(this);

        Picasso.with(this)
                .load("https://vignette4.wikia.nocookie.net/candh/images/3/3c/Dinosaurs.jpeg/revision/latest?cb=20120605140211")
                .into(imageView);
    }

    @OnClick(R.id.image)
    void imageClicked() {
        Intent intent = new Intent(this, MultiTransitionEndActivity.class);

        // ignore statusbar, toolbar
        View statusBar = findViewById(android.R.id.statusBarBackground);
        View navigationBar = findViewById(android.R.id.navigationBarBackground);
        List<Pair<View, String>> pairs = new ArrayList<>();
        pairs.add(Pair.create(statusBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME));
        pairs.add(Pair.create(navigationBar, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME));

        // add sharedElements
        pairs.add(Pair.create((View) imageView, imageView.getTransitionName()));
        pairs.add(Pair.create((View) nameText, nameText.getTransitionName()));
        pairs.add(Pair.create((View) dateText, dateText.getTransitionName()));

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,
                pairs.toArray(new Pair[pairs.size()]));

        // just deal with imageView
        /*ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,
                imageView, ViewCompat.getTransitionName(imageView));*/

        // no shared element
        //ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
        startActivity(intent, options.toBundle());
    }
}
