package com.lithiumsheep.jacketapp.util;

import android.app.Activity;

import com.lithiumsheep.jacketapp.R;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.weather_icons_typeface_library.WeatherIcons;

public class DrawerHelper {

    public static Drawer attach(Activity activity) {
        return new DrawerBuilder().withActivity(activity)
                .addDrawerItems(
                        new PrimaryDrawerItem().withIdentifier(0).withName(R.string.jacket_settings)
                                .withIcon(WeatherIcons.Icon.wic_thermometer),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withIdentifier(1).withName(R.string.support_development)
                                .withIcon(CommunityMaterial.Icon.cmd_heart).withSelectable(false),
                        new PrimaryDrawerItem().withIdentifier(2).withName(R.string.settings)
                                .withIcon(CommunityMaterial.Icon.cmd_settings).withSelectable(false),
                        new PrimaryDrawerItem().withIdentifier(3).withName(R.string.about)
                                .withIcon(CommunityMaterial.Icon.cmd_help_circle).withSelectable(false)
                )
                .withSelectedItem(0)
                .build();
    }
}
