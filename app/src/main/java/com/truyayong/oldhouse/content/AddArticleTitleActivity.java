package com.truyayong.oldhouse.content;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.truyayong.oldhouse.R;
import com.truyayong.oldhouse.data.Article;
import com.truyayong.oldhouse.data.ArticleItem;
import com.truyayong.oldhouse.data.User;
import com.truyayong.oldhouse.utils.KeyboardUtil;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import jp.wasabeef.richeditor.RichEditor;

public class AddArticleTitleActivity extends AppCompatActivity {

    private static final int PUBLISH_MENU_ITEM_ID = 1;

    private Intent intent;

    private RichEditor articleTitleRichText;
    private EditText etArticleTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_add_article_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("新文章");

        articleTitleRichText = (RichEditor)findViewById(R.id.rich_editor_article_title);
        articleTitleRichText.setEditorHeight(200);
        articleTitleRichText.setPlaceholder("在这里开始你的故事...");

        etArticleTitle = (EditText) findViewById(R.id.et_article_title);

        intent = getIntent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.add(0, PUBLISH_MENU_ITEM_ID, 0, "发布");
        menuItem.setIcon(R.drawable.ic_send_white_24dp);
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            setResult(0, intent);
            finish();
        }

        if (item.getItemId() == PUBLISH_MENU_ITEM_ID) {
            final String title = etArticleTitle.getText().toString();
            final String content = articleTitleRichText.getHtml();
            final User mUser = BmobUser.getCurrentUser(User.class);
            final Article article = new Article();
            article.setTitle(title);
            article.setAuthor(mUser);
            article.setFavoriteCount(1);
            article.setFollowUserCount(1);
            article.setContent(content);
            article.setAuthorHeadUrl(mUser.getUserHeadUrl());
            article.setAuthorName(mUser.getUserName());
            BmobRelation relation = new BmobRelation();
            relation.add(mUser);
            article.setFollowUser(relation);
            article.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        Log.e("qiuyayong", s);

                        ArticleItem articleItem = new ArticleItem();
                        articleItem.setAuthor(mUser);
                        articleItem.setArticle(article);
                        articleItem.setTitle(article.getTitle());
                        articleItem.setContent(content);
                        articleItem.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    User user = new User();
                                    user.setWriteArticle(article);
                                    user.update(mUser.getObjectId(), new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            Intent intent = new Intent(AddArticleTitleActivity.this, ShowArticleActivity.class);
                                            intent.putExtra("title", title);
                                            intent.putExtra("content", content);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });

                                } else {
                                    Toast.makeText(AddArticleTitleActivity.this, "发布失败2", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    } else {
                        Toast.makeText(AddArticleTitleActivity.this, "发布失败1", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }
}
