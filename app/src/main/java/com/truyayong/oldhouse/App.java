package com.truyayong.oldhouse;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;
import com.truyayong.oldhouse.utils.BmobUtil;
import com.truyayong.oldhouse.utils.KeyboardUtil;

/**
 * Created by alley_qiu on 2016/12/4.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BmobUtil.initBmob(this);
        CrashReport.initCrashReport(getApplicationContext(), "de60f0e760", true);
    }
}
