package com.truyayong.oldhouse.utils;

import android.content.Context;

import cn.bmob.v3.Bmob;

/**
 * Created by alley_qiu on 2016/12/4.
 */

public class BmobUtil {

    public static final String BMOB_APP_KEY = "fe557b2022224ab5e09cc5cced83fd14";

    public static void initBmob(Context context) {
        Bmob.initialize(context, BMOB_APP_KEY);
    }
}
