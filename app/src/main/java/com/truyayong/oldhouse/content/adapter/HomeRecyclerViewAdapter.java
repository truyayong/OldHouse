package com.truyayong.oldhouse.content.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by alley_qiu on 2017/2/24.
 */

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.Holder> {

    public class Holder extends RecyclerView.ViewHolder {

        TextView text;
        ImageView menu;
        CircleImageView profile_pic;
        ImageView pic;

        public Holder(View itemView) {
            super(itemView);
        }
    }
}
