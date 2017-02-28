package com.truyayong.oldhouse.content.fragments;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.truyayong.oldhouse.R;
import com.truyayong.oldhouse.content.AddArticleTitleActivity;
import com.truyayong.oldhouse.content.adapter.HomeLiveListAdapter;
import com.truyayong.oldhouse.content.adapter.HomeRecyclerViewAdapter;
import com.truyayong.oldhouse.data.Article;
import com.truyayong.oldhouse.data.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

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
    private FloatingActionButton fabWriteAritecle;
    private FloatingActionButton fabFollowArticle;

    private View headView;

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
        headView = LayoutInflater.from(mContext).inflate(R.layout.home_list_headview, null);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) (mContext.getResources().getDisplayMetrics().density * 64));
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(manager);
        Log.e("HomeFragment", " initView");
        final List<Article> datas = new ArrayList<>();
        BmobQuery<Article> query = new BmobQuery<>();
        User mUser = BmobUser.getCurrentUser(User.class);
        query.addWhereEqualTo("author", mUser);
        query.findObjects(new FindListener<Article>() {
            @Override
            public void done(List<Article> list, BmobException e) {
                if (e == null) {
                    Log.e("article", "list size : " + list.size());
                    for (Article a : list) {
                        datas.add(a);
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

                } else {
                    Toast.makeText(getActivity(), "find empty", Toast.LENGTH_SHORT).show();
                }
            }
        });


        fabWriteAritecle = (FloatingActionButton) rootView.findViewById(R.id.fab_write_article);
        fabFollowArticle = (FloatingActionButton) rootView.findViewById(R.id.fab_follow_article);

        fabWriteAritecle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddArticleTitleActivity.class);
                startActivity(intent);
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
