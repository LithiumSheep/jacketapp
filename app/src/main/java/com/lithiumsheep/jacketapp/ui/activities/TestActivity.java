package com.lithiumsheep.jacketapp.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.lapism.searchview.Search;
import com.lapism.searchview.widget.SearchAdapter;
import com.lapism.searchview.widget.SearchItem;
import com.lapism.searchview.widget.SearchView;
import com.lithiumsheep.jacketapp.R;
import com.lithiumsheep.jacketapp.api.HttpClient;
import com.lithiumsheep.jacketapp.api.NetworkCallback;
import com.lithiumsheep.jacketapp.models.weather.CurrentWeather;
import com.lithiumsheep.jacketapp.ui.WeatherViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.backdrop)
    ImageView backdrop;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.search_view)
    SearchView searchView;

    WeatherViewHolder weatherViewHolder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        weatherViewHolder = new WeatherViewHolder(this);

        SearchAdapter adapter = new SearchAdapter(this);

        SearchItem item = new SearchItem(this);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                weatherViewHolder.clear();

                HttpClient.get().getWeather("Arlington")
                        .enqueue(new NetworkCallback<CurrentWeather>() {
                            @Override
                            protected void onSuccess(CurrentWeather response) {
                                refreshLayout.setRefreshing(false);
                                updateUi(response);
                            }

                            @Override
                            protected void onError(Error error) {
                                Timber.w(error.getMessage());
                                refreshLayout.setRefreshing(false);
                            }
                        });
            }
        });

        searchView.setOnQueryTextListener(new Search.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(CharSequence query) {
                return false;
            }

            @Override
            public void onQueryTextChange(CharSequence newText) {
                
            }
        });
    }

    void updateUi(CurrentWeather weather) {
        weatherViewHolder.bind(weather);
    }
}
