package com.truyayong.oldhouse.content;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.truyayong.oldhouse.R;

import java.util.ArrayList;
import java.util.List;

public class IndexActivity extends FragmentActivity {

    private final String TAG = IndexActivity.class.getSimpleName();
    private Context mContext;

    private FrameLayout content;
    private AppBarLayout index_app_bar;

    private List<Fragment> fragments = new ArrayList<>();

    private RadioGroup rgs;
    private RadioButton index_tab;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_index);
        initView();
    }

    private void initView() {
        content = (FrameLayout) findViewById(R.id.content);
        index_app_bar = (AppBarLayout) findViewById(R.id.index_app_bar);
        rgs = (RadioGroup) findViewById(R.id.tabs_rg);
        index_tab = (RadioButton) findViewById(R.id.home_tab);
    }
}
