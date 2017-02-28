package com.truyayong.oldhouse.content;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.truyayong.oldhouse.R;
import com.truyayong.oldhouse.data.ArticleItem;
import com.truyayong.oldhouse.user.UserInfoActivity;
import com.zzhoujay.richtext.RichText;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator;

public class ShowArticleActivity extends FragmentActivity {

    private View headView;
    private View tailView;

    private ImageButton ibAddArticleItem;

    private MyAdapter adapter;
    private TextView tvArticleTitle;
    private Intent intent;
    private List<String> datas = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_article);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new FadeInUpAnimator());
        recyclerView.getItemAnimator().setAddDuration(500);
        recyclerView.getItemAnimator().setRemoveDuration(500);
        adapter = new MyAdapter();
        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(ShowArticleActivity.this, "gooll : " + position, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);

        headView = LayoutInflater.from(this).inflate(R.layout.show_article_headview, null);
        tvArticleTitle = (TextView) headView.findViewById(R.id.tv_article_title);
        intent = getIntent();
        final String title = intent.getStringExtra("title");
        tvArticleTitle.setText(intent.getStringExtra("title"));
        BmobQuery<ArticleItem> query = new BmobQuery<>();
        query.addWhereEqualTo("title", title);
        query.findObjects(new FindListener<ArticleItem>() {
            @Override
            public void done(List<ArticleItem> list, BmobException e) {
                for (ArticleItem item : list) {
                    datas.add(item.getContent());
                }
                adapter.notifyDataSetChanged();
            }
        });

        tailView = LayoutInflater.from(this).inflate(R.layout.show_article_tailview, null);
        ibAddArticleItem = (ImageButton) findViewById(R.id.ib_add_article_item);
        ibAddArticleItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowArticleActivity.this, AddArticleItemActivity.class);
                intent.putExtra("title", title);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1) {
            String text = data.getStringExtra("content");
            if (adapter != null) {
                adapter.add(text);
            }
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.Holder> {

        private OnRecyclerViewItemClickListener listener = null;

        private final int TYPE_HEAD = 0;
        private final int TYPE_NORMAL = 1;
        private final int TYPE_TAIL = 2;

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

            if (headView != null && viewType == TYPE_HEAD) {
                return new Holder(headView);
            }

            if (tailView != null && viewType == TYPE_TAIL) {
                return new Holder(tailView);
            }
            View view = LayoutInflater.from(ShowArticleActivity.this).inflate(R.layout.item_list, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(Holder holder, final int position) {

            if (getItemViewType(position) == TYPE_HEAD) {
                return;
            }

            if(getItemViewType(position) == TYPE_TAIL) {
                return;
            }


            int pos = getRealPosition(holder);
            Log.e("ShowArticleActivity", " pos = " + pos);
            if (holder instanceof Holder) {
                Holder h = (Holder) holder;
                h.authorHead.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ShowArticleActivity.this, "head", Toast.LENGTH_SHORT).show();
                        Intent inten = new Intent(ShowArticleActivity.this, UserInfoActivity.class);
                        startActivity(inten);
                    }
                });
                h.authorName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent inten = new Intent(ShowArticleActivity.this, UserInfoActivity.class);
                        startActivity(inten);
                    }
                });

                RichText.from(datas.get(pos)).clickable(false).into(h.text);

            }
        }

        @Override
        public int getItemCount() {
            int count = datas.size();
            if (headView != null) {
                count++;
            }
            if (tailView != null) {
                count++;
            }
            return count;
        }

        @Override
        public int getItemViewType(int position) {
            if (headView == null) return TYPE_NORMAL;
            if (position == 0) return TYPE_HEAD;
            if (position == getItemCount() - 1) return TYPE_TAIL;
            return TYPE_NORMAL;
        }

        class Holder extends RecyclerView.ViewHolder {

            public TextView text;
            public TextView authorName;
            public ImageView authorHead;

            public Holder(View itemView) {
                super(itemView);
                if (itemView == headView) return;
                if (itemView == tailView) return;
                text = (TextView) itemView.findViewById(R.id.tv_article_item_content);
                authorHead = (ImageView) itemView.findViewById(R.id.iv_article_item_author_icon);
                authorName = (TextView) itemView.findViewById(R.id.tv_article_item_author_name);
            }
        }

        private int getRealPosition(RecyclerView.ViewHolder holder) {
            int pos = holder.getLayoutPosition();
            return headView == null ? pos : pos - 1;
        }

        public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
            Log.e("gooll", "setOnItemClickListener : " + listener.toString());
            this.listener = listener;
        }
        public void remove(int position) {
            datas.remove(position);
            notifyItemRemoved(position);
        }

        public void add(String text) {
            datas.add(text);
            notifyDataSetChanged();
        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View v, int position);
    }
}
