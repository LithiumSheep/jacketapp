package com.lithiumsheep.jacketapp.models.search;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBufferResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlaceSuggestion implements SearchSuggestion {

    public PlaceSuggestion(String prediction) {
        this.prediction = prediction;
    }

    public PlaceSuggestion(Parcel in) {
        this.prediction = in.readString();
    }

    private String prediction;

    public String getPrediction() {
        return prediction;
    }

    @Override
    public String getBody() {
        return prediction;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(prediction);
    }

    public static final Creator<PlaceSuggestion> CREATOR = new Creator<PlaceSuggestion>() {
        @Override
        public PlaceSuggestion createFromParcel(Parcel in) {
            return new PlaceSuggestion(in);
        }

        @Override
        public PlaceSuggestion[] newArray(int size) {
            return new PlaceSuggestion[size];
        }
    };

    public static List<PlaceSuggestion> of(AutocompletePredictionBufferResponse response) {
        List<PlaceSuggestion> list;
        if (response == null) {
            list = Collections.emptyList();
        } else {
            list = new ArrayList<>();
            /*if (response.getCount() >= 3) {
                for (int i = 0; i < 3; i++) {
                    list.add(new PlaceSuggestion(response.get(i).getFullText(null).toString()));
                }
            } else {
                for (int i = 0; i < response.getCount(); i++) {
                    list.add(new PlaceSuggestion(response.get(i).getFullText(null).toString()));
                }
            }*/
            for (AutocompletePrediction aResponse : response) {
                list.add(new PlaceSuggestion(aResponse.getFullText(null).toString()));
            }
            response.release();
        }
        return list;
    }

    public static List<PlaceSuggestion> mock() {
        List<PlaceSuggestion> list = new ArrayList<>();
        list.add(new PlaceSuggestion("Blah"));
        list.add(new PlaceSuggestion("Bork"));
        list.add(new PlaceSuggestion("Snerk"));
        return list;
    }
}
