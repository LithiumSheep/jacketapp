package com.lithiumsheep.jacketapp.util;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.lithiumsheep.jacketapp.R;

/**
 * Created by jessexx on 5/30/17.
 */

public class DialogUtil {

    public static MaterialDialog createMaterialProgress(Context context, String title) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .content("Please wait...")
                .widgetColorRes(R.color.colorPrimary)
                .progress(true, 0)
                .canceledOnTouchOutside(false)
                .cancelable(false)
                .build();
    }
}
