package com.lithiumsheep.jacketapp.util;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.lithiumsheep.jacketapp.R;
import com.lithiumsheep.jacketapp.experimental.BottomSheetActivity;
import com.lithiumsheep.jacketapp.experimental.MultiTransitionActivity;
import com.lithiumsheep.jacketapp.ui.activities.SettingsActivity;
import com.lithiumsheep.jacketapp.experimental.TransitionActivity;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.weather_icons_typeface_library.WeatherIcons;

public class DrawerHelper {

    public static Drawer attach(final Activity activity) {
        return new DrawerBuilder().withActivity(activity)
                .addDrawerItems(
                        new PrimaryDrawerItem().withTag(0).withName(R.string.home)
                                .withIcon(CommunityMaterial.Icon.cmd_home),
                        new PrimaryDrawerItem().withTag(1).withName(R.string.jacket_settings)
                                .withIcon(WeatherIcons.Icon.wic_thermometer),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withTag(2).withName(R.string.support_development)
                                .withIcon(CommunityMaterial.Icon.cmd_heart).withSelectable(false),
                        new PrimaryDrawerItem().withTag(3).withName(R.string.settings)
                                .withIcon(CommunityMaterial.Icon.cmd_settings).withSelectable(false),
                        new PrimaryDrawerItem().withTag(4).withName(R.string.about)
                                .withIcon(CommunityMaterial.Icon.cmd_help_circle).withSelectable(false),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withTag(5).withName(R.string.test_animation)
                                .withIcon(CommunityMaterial.Icon.cmd_test_tube).withSelectable(false),
                        new SecondaryDrawerItem().withTag(6).withName(R.string.test_transition)
                                .withIcon(CommunityMaterial.Icon.cmd_android_debug_bridge).withSelectable(false),
                        new SecondaryDrawerItem().withTag(7).withName(R.string.test_bottom_sheet)
                                .withIcon(CommunityMaterial.Icon.cmd_android_debug_bridge).withSelectable(false)
                )
                .withSelectedItem(0)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch ((int) drawerItem.getTag()) {
                            case 0:
                                // home
                                return false;
                            case 1:
                                // prefs
                                return false;
                            case 2:
                                return false;
                            case 3:
                                // settings
                                activity.startActivity(new Intent(activity, SettingsActivity.class));
                                return false;
                            case 4:
                                return false;
                            case 5:
                                activity.startActivity(new Intent(activity, TransitionActivity.class));
                                return false;
                            case 6:
                                activity.startActivity(new Intent(activity, MultiTransitionActivity.class));
                                return false;
                            case 7:
                                activity.startActivity(new Intent(activity, BottomSheetActivity.class));
                                return false;
                            default:
                                return false;
                        }
                    }
                })
                .build();
    }
}
