package com.lithiumsheep.jacketapp.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.lithiumsheep.jacketapp.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MultiTransitionEndActivity extends AppCompatActivity {

    @BindView(R.id.image)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_end);
        ButterKnife.bind(this);

        Picasso.with(this)
                .load("https://vignette4.wikia.nocookie.net/candh/images/3/3c/Dinosaurs.jpeg/revision/latest?cb=20120605140211")
                .into(imageView);
    }

    @OnClick(R.id.image)
    void imageClicked() {
        finishAfterTransition();
    }
}
