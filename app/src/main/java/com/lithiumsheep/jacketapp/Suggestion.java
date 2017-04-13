package com.lithiumsheep.jacketapp;

import android.annotation.SuppressLint;
import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;


@SuppressLint("ParcelCreator")
public class Suggestion implements SearchSuggestion {

    private String suggestion;

    public Suggestion(String string) {
        suggestion = string;
    }

    @Override
    public String getBody() {
        return suggestion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
