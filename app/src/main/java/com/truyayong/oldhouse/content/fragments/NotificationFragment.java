package com.truyayong.oldhouse.content.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.truyayong.oldhouse.R;

/**
 * Created by alley_qiu on 2017/2/24.
 */

public class NotificationFragment extends Fragment {
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_notification, container, false);
        InitView();
        return rootView;
    }

    private void InitView() {
        final CoordinatorLayout layout = (CoordinatorLayout) rootView.findViewById(R.id.layout);
        rootView.findViewById(R.id.btnFloatingAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(layout, "网络连接错误", Snackbar.LENGTH_LONG).setAction("再试一次", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();
            }
        });
    }
}
