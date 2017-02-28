package com.truyayong.oldhouse.content;

/**
 * Created by alley_qiu on 2017/2/28.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.truyayong.oldhouse.R;
import com.truyayong.oldhouse.data.Article;
import com.truyayong.oldhouse.data.ArticleItem;
import com.truyayong.oldhouse.data.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import jp.wasabeef.richeditor.RichEditor;

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

public class AddArticleItemActivity extends AppCompatActivity {

    private static final int PUBLISH_MENU_ITEM_ID = 1;

    private Intent intent;
    private String title;

    private RichEditor articleTitleRichText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article_item);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("新文章");

        articleTitleRichText = (RichEditor)findViewById(R.id.rich_editor_article_title);
        articleTitleRichText.setEditorHeight(200);
        articleTitleRichText.setPlaceholder("在这里开始你的故事...");

        intent = getIntent();
        title = intent.getStringExtra("title");
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
            final String content = articleTitleRichText.getHtml();
            final User mUser = BmobUser.getCurrentUser(User.class);
            ArticleItem articleItem = new ArticleItem();
            articleItem.setContent(content);
            articleItem.setAuthor(mUser);
            articleItem.setTitle(title);
            articleItem.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    Intent tempIntent = new Intent(AddArticleItemActivity.this, ShowArticleActivity.class);
                    tempIntent.putExtra("content", content);
                    setResult(1, tempIntent);
                    finish();
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }
}

