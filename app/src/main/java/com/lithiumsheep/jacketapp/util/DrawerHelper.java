package com.lithiumsheep.jacketapp.util;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.lithiumsheep.jacketapp.R;
import com.lithiumsheep.jacketapp.ui.activities.SettingsActivity;
import com.lithiumsheep.jacketapp.ui.activities.TransitionActivity;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.weather_icons_typeface_library.WeatherIcons;

public class DrawerHelper {

    public static Drawer attach(final Activity activity) {
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
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch(position) {
                            case 0:
                                return false;
                            case 1:
                                return false;
                            case 2:
                                return false;
                            case 3:
                                activity.startActivity(new Intent(activity, SettingsActivity.class));
                                return false;
                            case 4:
                                activity.startActivity(new Intent(activity, TransitionActivity.class));
                                return false;
                            default:
                                return false;
                        }
                    }
                })
                .build();
    }
}
