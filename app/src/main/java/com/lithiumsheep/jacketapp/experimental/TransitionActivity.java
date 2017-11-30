package com.lithiumsheep.jacketapp.experimental;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.lithiumsheep.jacketapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TransitionActivity extends AppCompatActivity {

    @BindView(R.id.image)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);

        Picasso.with(this)
                .load("https://vignette4.wikia.nocookie.net/candh/images/3/3c/Dinosaurs.jpeg/revision/latest?cb=20120605140211")
                .into(imageView);
    }

    @OnClick(R.id.image)
    void imageClicked() {
        Intent intent = new Intent(this, TransitionBActivity.class);

        // ignore statusbar, toolbar
        View statusBar = findViewById(android.R.id.statusBarBackground);
        View navigationBar = findViewById(android.R.id.navigationBarBackground);
        List<Pair<View, String>> pairs = new ArrayList<>();
        pairs.add(Pair.create(statusBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME));
        pairs.add(Pair.create(navigationBar, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME));
        pairs.add(Pair.create((View) imageView, imageView.getTransitionName()));
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
