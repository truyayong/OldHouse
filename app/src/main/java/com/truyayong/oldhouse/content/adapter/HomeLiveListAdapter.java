package com.truyayong.oldhouse.content.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.truyayong.oldhouse.R;

import java.util.List;

/**
 * Created by alley_qiu on 2017/2/24.
 */

public class HomeLiveListAdapter extends RecyclerView.Adapter<HomeLiveListAdapter.MyHolder> {


    private List<String> datas;
    private Context mContext;

    public HomeLiveListAdapter(List<String> datas) {
        this.datas = datas;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.live_list_item, null);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return datas.size() > 5 ? 5 : datas.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        public MyHolder(View itemView) {
            super(itemView);
        }
    }
}
