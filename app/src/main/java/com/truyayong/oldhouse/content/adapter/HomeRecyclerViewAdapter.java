package com.truyayong.oldhouse.content.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.truyayong.oldhouse.R;
import com.truyayong.oldhouse.content.ShowArticleActivity;
import com.truyayong.oldhouse.content.widget.ListItemMenu;
import com.truyayong.oldhouse.data.Article;
import com.truyayong.oldhouse.utils.DensityUtil;
import com.zzhoujay.richtext.RichText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by alley_qiu on 2017/2/24.
 */

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.Holder> {

    private static String[] pics = {"http://img5.imgtn.bdimg.com/it/u=1653858330,2839655607&fm=21&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3996559489,3436218348&fm=21&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=2466061736,1474812220&fm=21&gp=0.jpg"};

    private static String[] pics2 = {"http://img5.imgtn.bdimg.com/it/u=3676114267,848416754&fm=21&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=527603723,2697086410&fm=21&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=2897844664,899304846&fm=21&gp=0.jpg"};

    public static List<String> headPics = Arrays.asList(pics);


    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NORMAL = 1;
    private View headView;

    private List<Article> datas = new ArrayList<>();
    private Context mContext;

    private int menuW, menuH;

    public HomeRecyclerViewAdapter(Context mContext, List<Article> datas) {
        this.datas = datas;
        this.mContext = mContext;
        DisplayMetrics display = new DisplayMetrics();
        Activity mActivity = (Activity) mContext;
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(display);
        menuW = display.widthPixels / 2;
        menuH = LinearLayout.LayoutParams.WRAP_CONTENT;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (headView != null && viewType == TYPE_HEADER) {
            return new Holder(headView);
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.home_list_item, null);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        if (headView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) {
            return;
        }

        final int pos = getRealPosition(holder);

        if (pos == -1) {
            holder.liveList.setVisibility(View.VISIBLE);
            holder.normalShell.setVisibility(View.GONE);
        } else {
            holder.liveList.setVisibility(View.GONE);
            holder.normalShell.setVisibility(View.VISIBLE);
        }

        final Article article = datas.get(pos);
        holder.tvUserNameHome.setText(article.getAuthorName());
        Picasso.with(mContext).load(article.getAuthorHeadUrl()).placeholder(R.drawable.profile).into(holder.civHeadUserHome);
        holder.tvArticleTitleHome.setText(article.getTitle());
        RichText.from(article.getContent()).into(holder.tvArticleContenHome);
        holder.tvFavoriteHome.setText("" + article.getFavoriteCount() + " 赞");
        holder.tvFollowHome.setText("" + article.getFollowUserCount() + " 关注");
        holder.llArticleDetailHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShowArticleActivity.class);
                intent.putExtra("title", article.getTitle());
                mContext.startActivity(intent);
            }
        });
        Log.e("HomeRecyclerViewAdapter", " onBindViewHolder");

        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListItemMenu menu = new ListItemMenu(menuW, menuH, mContext);
                menu.update();
                int offx = DensityUtil.dp2px(mContext, 24);
                int offy = DensityUtil.dp2px(mContext, 24);
                menu.setAnimationStyle(R.style.MenuAnim);
                menu.showAsDropDown(holder.menu, -menuW + offx, -offy);
            }
        });

//        LinearLayoutManager manager = new LinearLayoutManager(mContext);
//        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        holder.liveList.setLayoutManager(manager);
//        HomeLiveListAdapter adapter = new HomeLiveListAdapter(datas);
//        holder.liveList.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return headView == null ? datas.size() : datas.size() + 1;
    }


    public class Holder extends RecyclerView.ViewHolder {


        View llArticleDetailHome;
        TextView tvUserNameHome;
        ImageView menu;
        CircleImageView civHeadUserHome;
        LinearLayout normalShell;
        RecyclerView liveList;
        TextView tvArticleTitleHome;
        TextView tvArticleContenHome;
        TextView tvFavoriteHome;
        TextView tvFollowHome;

        public Holder(View itemView) {
            super(itemView);
            if (itemView == headView) return;
            tvUserNameHome = (TextView) itemView.findViewById(R.id.tv_user_name_home);
            menu = (ImageView) itemView.findViewById(R.id.menu);
            civHeadUserHome = (CircleImageView) itemView.findViewById(R.id.civ_head_user_home);
            tvArticleTitleHome = (TextView) itemView.findViewById(R.id.tv_article_title_home);
            tvArticleContenHome = (TextView) itemView.findViewById(R.id.tv_article_content_home);
            tvFavoriteHome = (TextView) itemView.findViewById(R.id.tv_favorite_home);
            tvFollowHome = (TextView) itemView.findViewById(R.id.tv_follow_home);
            normalShell = (LinearLayout) itemView.findViewById(R.id.normalList);
            liveList = (RecyclerView) itemView.findViewById(R.id.liveList);
            llArticleDetailHome = itemView.findViewById(R.id.ll_article_detail_home);
        }
    }

    public void setHeadView(View view) {
        headView = view;
        notifyItemInserted(0);
    }

    private int getRealPosition(RecyclerView.ViewHolder holder) {
        int pos = holder.getLayoutPosition();
        return headView == null ? pos : pos - 1;
    }
}
