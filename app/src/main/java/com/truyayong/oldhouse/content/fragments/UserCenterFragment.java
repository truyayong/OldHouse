package com.truyayong.oldhouse.content.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.truyayong.oldhouse.R;
import com.truyayong.oldhouse.data.User;

import org.w3c.dom.Text;

import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by alley_qiu on 2017/2/24.
 */

public class UserCenterFragment extends Fragment {

    private View rootView;
    private Context mContext;
    private CollapsingToolbarLayout collapsing_toolbar;
    private FloatingActionButton fab;
    private User mUser;
    private static final String picUrl = "http://img1.imgtn.bdimg.com/it/u=3743691986,2983459167&fm=21&gp=0.jpg";

    private View llFollowArticleUsercenter;
    private TextView tvFollowArticleUsercenter;
    private View llFollowUserUsercenter;
    private TextView tvFollowUserUsercenter;
    private View llFollowedUserUsercenter;
    private TextView tvFollowedUserUsercenter;

    private TextView tvUserDescriptionUsercenter;
    private TextView tvUserLocationUsercenter;
    private TextView tvUserGenderUsercenter;

    private View  rlUserWriteArticleUsercenter;
    private  View rlUserWriteArticleItemUsercenter;
    private View rlUserRecentReadUsercenter;
    private View rlUserAboutTruyayongUsercenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_usercenter, container, false);
        mUser = BmobUser.getCurrentUser(User.class);
        InitView();
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    private void InitView() {
        collapsing_toolbar = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
        collapsing_toolbar.setTitle(mUser.getUserName());
        fab = (FloatingActionButton) rootView.findViewById(R.id.btn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "编辑", Toast.LENGTH_SHORT).show();
            }
        });
        CircleImageView view = (CircleImageView) rootView.findViewById(R.id.headview);
        Picasso.with(mContext).load(mUser.getUserHeadUrl()).placeholder(R.drawable.profile).into(view);
        llFollowArticleUsercenter = rootView.findViewById(R.id.ll_follow_artcile_usercenter);
        llFollowArticleUsercenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tvFollowArticleUsercenter = (TextView) rootView.findViewById(R.id.tv_follow_article_count_usercenter);

        llFollowUserUsercenter = rootView.findViewById(R.id.ll_follow_user_usercenter);
        llFollowUserUsercenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tvFollowUserUsercenter = (TextView) rootView.findViewById(R.id.tv_follow_user_usercenter);
        tvFollowUserUsercenter.setText("" + mUser.getFollowUserCount());

        llFollowedUserUsercenter = rootView.findViewById(R.id.ll_followed_user_usercenter);
        llFollowedUserUsercenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tvFollowedUserUsercenter = (TextView) rootView.findViewById(R.id.tv_followed_user_usercenter);
        tvFollowedUserUsercenter.setText("" + mUser.getFollowedUserCount());

        tvUserDescriptionUsercenter = (TextView) rootView.findViewById(R.id.tv_user_description_usercenter);
        tvUserDescriptionUsercenter.setText(mUser.getUserDescription());

        tvUserLocationUsercenter = (TextView) rootView.findViewById(R.id.tv_user_location_usercenter);
        tvUserLocationUsercenter.setText(mUser.getUserLocation());

        tvUserGenderUsercenter = (TextView) rootView.findViewById(R.id.tv_user_gender_usercenter);
        if (mUser.getGender() == true) {
            tvUserGenderUsercenter.setText("Boy");
        } else {
            tvUserGenderUsercenter.setText("Girl");
        }

        rlUserWriteArticleUsercenter = rootView.findViewById(R.id.rl_user_write_article_usercenter);
        rlUserWriteArticleUsercenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        rlUserWriteArticleItemUsercenter = rootView.findViewById(R.id.rl_user_write_article_item_usercenter);
        rlUserWriteArticleItemUsercenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        rlUserRecentReadUsercenter = rootView.findViewById(R.id.rl_user_recent_read_usercenter);
        rlUserRecentReadUsercenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        rlUserAboutTruyayongUsercenter = rootView.findViewById(R.id.rl_user_about_truyayong_usercenter);
        rlUserAboutTruyayongUsercenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}