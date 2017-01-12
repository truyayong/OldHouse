package com.truyayong.oldhouse;

import android.app.Application;

import com.truyayong.oldhouse.utils.BmobUtil;

/**
 * Created by alley_qiu on 2016/12/4.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BmobUtil.initBmob(this);
    }
}
