package com.truyayong.oldhouse.user;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.truyayong.oldhouse.R;

public class UserInfoActivity extends AppCompatActivity {

    public static Handler mHandler;
    private RelativeLayout rlHead;
    private TextView tvToolUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("hello");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        tvToolUserName = (TextView)findViewById(R.id.tv_tool_username);
        tvToolUserName.setText("Qiuyay");

        rlHead = (RelativeLayout)findViewById(R.id.rl_head_userinfo);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                float alpha = ((float) msg.what) / 100f;
                if (alpha < 0.5f) {
                    rlHead.setAlpha(alpha * 0.5f);
                } else {
                    rlHead.setAlpha(alpha * 0.5f);
                }
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler = null;
    }
}
