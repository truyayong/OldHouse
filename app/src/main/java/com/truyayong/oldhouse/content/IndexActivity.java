package com.truyayong.oldhouse.content;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.truyayong.oldhouse.R;
import com.truyayong.oldhouse.content.adapter.FragmentTabAdapter;
import com.truyayong.oldhouse.content.fragments.HomeFragment;
import com.truyayong.oldhouse.content.fragments.NotificationFragment;
import com.truyayong.oldhouse.content.fragments.UserCenterFragment;

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
        Log.e("activity_index", "oncreate enter");
        initView();
    }

    private void initView() {
        content = (FrameLayout) findViewById(R.id.content);
        index_app_bar = (AppBarLayout) findViewById(R.id.index_app_bar);
        rgs = (RadioGroup) findViewById(R.id.tabs_rg);
        index_tab = (RadioButton) findViewById(R.id.home_tab);
        fragments.add(new HomeFragment());
        fragments.add(new NotificationFragment());
        fragments.add(new UserCenterFragment());

        Log.e("activity_index", "oncreate initView");

        FragmentTabAdapter tabAdapter = new FragmentTabAdapter(this, fragments, R.id.content, rgs);
        tabAdapter.setOnRgsExtraCheckedChangedListener(new FragmentTabAdapter.OnRgsExtraCheckedChangedListener() {
            @Override
            public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
                super.OnRgsExtraCheckedChanged(radioGroup, checkedId, index);
                Log.e("CheckedChanged", "-----" + index);
                currentIndex = index;
                resetView();
                switch (index) {
                    case 0:
                        index_app_bar.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    default:
                        break;

                }

            }
        });

    }

    private void resetView() {
        index_app_bar.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (currentIndex != 0) {
            index_tab.setChecked(true);
        } else {
            super.onBackPressed();
        }
    }
}
