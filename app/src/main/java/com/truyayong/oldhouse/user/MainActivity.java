package com.truyayong.oldhouse.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.truyayong.oldhouse.R;
import com.truyayong.oldhouse.data.User;

import net.bither.util.NativeUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import jp.wasabeef.richeditor.RichEditor;

public class MainActivity extends AppCompatActivity {

    private RichEditor mEditor;
    private TextView mPreview;
    private ImageButton ibSave;

    private static final int CHOOSE_HEAD_LOCAL = 0;
    private static final int CHOOSE_HEAD_TAKE = 1;
    private static final int CROP_SMALL_HEAD = 2;
    private Uri tempUri = null;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditor = (RichEditor) findViewById(R.id.editor);
        mEditor.setEditorHeight(200);
        mEditor.setEditorFontSize(22);
        mEditor.setEditorFontColor(Color.RED);
        //mEditor.setEditorBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundResource(R.drawable.bg);
        mEditor.setPadding(10, 10, 10, 10);
        //    mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        mEditor.setPlaceholder("Insert text here...");

        mPreview = (TextView) findViewById(R.id.preview);
        ibSave = (ImageButton)findViewById(R.id.action_save);
        ibSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mEditor.getHtml();
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
                User mUser = BmobUser.getCurrentUser(User.class);
                User user = new User();
                user.setTextUrl(text);
                //user.setUserName("qyy");
                user.update(mUser.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Log.e("MainActivity", "done success");
                        } else {
                            Log.e("MainActivity", " exception: " + e.toString());
                        }
                    }
                });
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("text", text);
                startActivity(intent);
            }
        });
        mEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override public void onTextChange(String text) {
                mPreview.setText(text);
            }
        });

        findViewById(R.id.action_undo).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.undo();
            }
        });

        findViewById(R.id.action_redo).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.redo();
            }
        });

        findViewById(R.id.action_bold).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setBold();
            }
        });

        findViewById(R.id.action_italic).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setItalic();
            }
        });

        findViewById(R.id.action_subscript).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setSubscript();
            }
        });

        findViewById(R.id.action_superscript).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setSuperscript();
            }
        });

        findViewById(R.id.action_strikethrough).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setStrikeThrough();
            }
        });

        findViewById(R.id.action_underline).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setUnderline();
            }
        });

        findViewById(R.id.action_heading1).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(1);
            }
        });

        findViewById(R.id.action_heading2).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(2);
            }
        });

        findViewById(R.id.action_heading3).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(3);
            }
        });

        findViewById(R.id.action_heading4).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(4);
            }
        });

        findViewById(R.id.action_heading5).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(5);
            }
        });

        findViewById(R.id.action_heading6).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(6);
            }
        });

        findViewById(R.id.action_txt_color).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override public void onClick(View v) {
                mEditor.setTextColor(isChanged ? Color.BLACK : Color.RED);
                isChanged = !isChanged;
            }
        });

        findViewById(R.id.action_bg_color).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override public void onClick(View v) {
                mEditor.setTextBackgroundColor(isChanged ? Color.TRANSPARENT : Color.YELLOW);
                isChanged = !isChanged;
            }
        });

        findViewById(R.id.action_indent).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setIndent();
            }
        });

        findViewById(R.id.action_outdent).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setOutdent();
            }
        });

        findViewById(R.id.action_align_left).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setAlignLeft();
            }
        });

        findViewById(R.id.action_align_center).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setAlignCenter();
            }
        });

        findViewById(R.id.action_align_right).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setAlignRight();
            }
        });

        findViewById(R.id.action_blockquote).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setBlockquote();
            }
        });

        findViewById(R.id.action_insert_bullets).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setBullets();
            }
        });

        findViewById(R.id.action_insert_numbers).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setNumbers();
            }
        });

        findViewById(R.id.action_insert_image).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
//                mEditor.insertImage("http://www.1honeywan.com/dachshund/image/7.21/7.21_3_thumb.JPG",
//                        "dachshund");
//                mEditor.insertImage("http://bmob-cdn-7969.b0.upaiyun.com/2017/02/05/b3be989e07f448c8be2af2baef8c7fa6.png",
//                        "xiong");
                showChooseHead();
            }
        });

        findViewById(R.id.action_insert_link).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.insertLink("https://github.com/wasabeef", "wasabeef");
            }
        });
        findViewById(R.id.action_insert_checkbox).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.insertTodo();
            }
        });
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
                    mEditor.insertImage(url, "xiong");
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
                        mEditor.insertImage(url, "xiong");
                    }
                }
            });
        }
    }
}
