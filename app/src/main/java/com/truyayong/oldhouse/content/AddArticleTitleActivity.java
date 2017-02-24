package com.truyayong.oldhouse.content;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.truyayong.oldhouse.R;
import com.truyayong.oldhouse.utils.KeyboardUtil;

import jp.wasabeef.richeditor.RichEditor;

public class AddArticleTitleActivity extends AppCompatActivity {

    private static final int PUBLISH_MENU_ITEM_ID = 1;

    private RichEditor articleTitleRichText;
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
            finish();
        }

        if (item.getItemId() == PUBLISH_MENU_ITEM_ID) {

        }
        return super.onOptionsItemSelected(item);
    }
}
