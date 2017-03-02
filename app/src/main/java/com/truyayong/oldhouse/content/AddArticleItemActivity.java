package com.truyayong.oldhouse.content;

/**
 * Created by alley_qiu on 2017/2/28.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.truyayong.oldhouse.R;
import com.truyayong.oldhouse.data.Article;
import com.truyayong.oldhouse.data.ArticleItem;
import com.truyayong.oldhouse.data.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
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

import net.bither.util.NativeUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import jp.wasabeef.richeditor.RichEditor;

public class AddArticleItemActivity extends AppCompatActivity {

    private static final int PUBLISH_MENU_ITEM_ID = 1;

    private static final int CHOOSE_HEAD_LOCAL = 0;
    private static final int CHOOSE_HEAD_TAKE = 1;
    private static final int CROP_SMALL_HEAD = 2;
    private Uri tempUri = null;

    private Intent intent;
    private String title;

    private RichEditor articleTitleRichText;
    private ImageButton imbPhotoAddArticleItem;
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

        imbPhotoAddArticleItem = (ImageButton)findViewById(R.id.imb_photo_add_article_item);
        imbPhotoAddArticleItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChooseHead();
            }
        });

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

    private void showChooseHead() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("插入图片");
        String[] items = {"选择本地图片", "拍照"};
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_HEAD_LOCAL:
                        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        startActivityForResult(openAlbumIntent, CHOOSE_HEAD_LOCAL);
                        break;
                    case CHOOSE_HEAD_TAKE:
                        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        tempUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg"));
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                        startActivityForResult(openCameraIntent, CHOOSE_HEAD_TAKE);
                        break;
                }
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CHOOSE_HEAD_LOCAL:
                    startImageZoom(data.getData());
                    break;
                case CHOOSE_HEAD_TAKE:
                    startImageZoom(tempUri);
                    break;
                case CROP_SMALL_HEAD:
                    setImageToView(data);
                default:
                    break;
            }
        }
    }

    private void startImageZoom(final Uri uri) {
        if (uri == null) {
            throw new IllegalArgumentException("Uri can not be null");
        }
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, proj, // Which
                // columns
                // to return
                null, // WHERE clause; which rows to return (all rows)
                null, // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        String path = cursor.getString(column_index);
        Bitmap photo = BitmapFactory.decodeFile(path);
        Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
        File file = new File(Environment.getExternalStorageDirectory(), "temp1.png");

        BitmapFactory.Options newOps = new BitmapFactory.Options();
        newOps.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, newOps);
        int bew = 1;
        int beh = 1;
        bew = newOps.outWidth / 300;
        beh = newOps.outHeight / 1466;
        Log.e("BEnew", " be = " + bew);
        Log.e("BEnew", " be = " + beh);
        float rate = 30000.0f / (float)newOps.outWidth ;
        int rateInt = (int)rate;
        newOps.inJustDecodeBounds = false;
        newOps.inSampleSize = bew;
        bitmap = BitmapFactory.decodeFile(path, newOps);
        try{
            if (!file.exists()) {
                file.createNewFile();
            }
//            FileOutputStream stream = new FileOutputStream(file);
//            if (bitmap.compress(Bitmap.CompressFormat.WEBP, 100, stream)) {
//                stream.flush();
//                stream.close();
//            }
//            NativeUtil.compressBitmap(bitmap, file.getAbsolutePath(), true);
            NativeUtil.compressBitmap(bitmap, 100, file.getAbsolutePath(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final BmobFile bmobFile = new BmobFile(file);
        bmobFile.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    String url = bmobFile.getFileUrl();
                    articleTitleRichText.insertImage(url, "xiong");
                }
            }
        });
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(uri, "image/*");
//        intent.putExtra("return-data", true);
//        startActivityForResult(intent, CROP_SMALL_HEAD);
    }

    private void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            File file = new File(Environment.getExternalStorageDirectory(), "temp1.png");
            try{
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream stream = new FileOutputStream(file);
                if (photo.compress(Bitmap.CompressFormat.PNG, 100, stream)) {
                    stream.flush();
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            final BmobFile bmobFile = new BmobFile(file);
            bmobFile.upload(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        String url = bmobFile.getFileUrl();
                        articleTitleRichText.insertImage(url, "xiong");
                    }
                }
            });
        }
    }
}

