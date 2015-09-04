package com.quickblox.socialmessenger.utils;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.quickblox.socialmessenger.R;

public class Consts {

    // Q-municate release
    public static final String QB_APP_ID = "24851";
    public static final String QB_AUTH_KEY = "SfP3bQg2RgZrEXG";
    public static final String QB_AUTH_SECRET = "BqR9VZL8ufK9C3w";

    public static final String QB_DOMAIN = "https://api.quickblox.com/";

    // Universal Image Loader
    public static final DisplayImageOptions UIL_DEFAULT_DISPLAY_OPTIONS = new DisplayImageOptions.Builder()
            .cacheInMemory(true).imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).cacheOnDisc(true).
                    considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();


    public static final DisplayImageOptions UIL_USER_AVATAR_DISPLAY_OPTIONS = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.placeholder_user).showImageForEmptyUri(R.drawable.placeholder_user)
            .showImageOnFail(R.drawable.placeholder_user).cacheOnDisc(true).cacheInMemory(true).considerExifParams(true).build();

    public static final DisplayImageOptions UIL_GROUP_AVATAR_DISPLAY_OPTIONS = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.placeholder_group).showImageForEmptyUri(
                    R.drawable.placeholder_group).showImageOnFail(R.drawable.placeholder_group).cacheOnDisc(
                    true).cacheInMemory(true).considerExifParams(true).build();
}