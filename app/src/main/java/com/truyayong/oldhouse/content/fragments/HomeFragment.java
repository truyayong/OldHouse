package com.truyayong.oldhouse.content.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionMenu;
import com.truyayong.oldhouse.R;
import com.truyayong.oldhouse.content.adapter.HomeLiveListAdapter;
import com.truyayong.oldhouse.content.adapter.HomeRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alley_qiu on 2017/2/24.
 */

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private Context mContext;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private HomeRecyclerViewAdapter adapter;
    //1111111
    private View rootView;
    private FloatingActionMenu fam;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, null);
        InitView();
        Log.e("HomeFragment", "oncreate initView");
        return rootView;
    }

    private void InitView() {
        fam = (FloatingActionMenu) rootView.findViewById(R.id.menu_yellow);
        View headView = LayoutInflater.from(mContext).inflate(R.layout.home_list_headview, null);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) (mContext.getResources().getDisplayMetrics().density * 64));
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(manager);
        Log.e("HomeFragment", " initView");
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 100; i++) { 
            datas.add("This is item " + i); 
        } 
        adapter = new HomeRecyclerViewAdapter(mContext, datas );
        adapter.setHeadView(headView);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (Math.abs(dy) > 5) {
                    if (dy > 0) {
                        fam.hideMenu(true);
                    } else {
                        fam.showMenu(true);
                    }
                }
            }
        });


    }


    @Override
    public void onRefresh() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);

    }


}
