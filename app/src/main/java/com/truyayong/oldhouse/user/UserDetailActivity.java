package com.truyayong.oldhouse.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.truyayong.oldhouse.HomeActivity;
import com.truyayong.oldhouse.R;
import com.truyayong.oldhouse.data.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Observable;

public class UserDetailActivity extends AppCompatActivity {

    private static final int SAVE_MENU_ITEM_ID = 1;

    private static final int CHOOSE_HEAD_LOCAL = 0;
    private static final int CHOOSE_HEAD_TAKE = 1;
    private static final int CROP_SMALL_HEAD = 2;
    private Uri tempUri = null;
    private CircleImageView civHeadUser;
    private EditText etUserName;
    private EditText etUserDescripe;
    private EditText etUserLocation;
    private RadioGroup rgUserGender;

    private String strUserName = "";
    private String strUserDescripe = "";
    private String strUserLocation = "";
    private Boolean bUserGender = false;
    private String strUserHeadUrl = "";
    private User mUser = BmobUser.getCurrentUser(User.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("个人设置");

        final TextInputLayout tilUserName = (TextInputLayout)findViewById(R.id.til_user_name);
        //tilUserName.setHint("用户名");
        civHeadUser = (CircleImageView)findViewById(R.id.civ_head_user);
        Picasso.with(getApplicationContext()).load(mUser.getUserHeadUrl()).into(civHeadUser);
        View layoutHead = findViewById(R.id.ll_head_userdetail);
        layoutHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UserDetailActivity.this, "choose head", Toast.LENGTH_SHORT).show();
                showChooseHead();
            }
        });
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        etUserName = (EditText)findViewById(R.id.et_user_name);
        etUserName.setText(mUser.getUserName());
        etUserDescripe = (EditText)findViewById(R.id.et_user_descripe);
        etUserDescripe.setText(mUser.getUserDescription());
        etUserLocation = (EditText)findViewById(R.id.et_user_location);
        etUserLocation.setText(mUser.getUserLocation());
        rgUserGender = (RadioGroup)findViewById(R.id.rg_user_gender);
        bUserGender = mUser.getGender();
        if (bUserGender) {
            rgUserGender.check(R.id.rb_user_male);
        } else {
            rgUserGender.check(R.id.rb_user_female);
        }
        rgUserGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_user_male) {
                    bUserGender = true;
                } else {
                    bUserGender = false;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem homeItem = menu.add(0, SAVE_MENU_ITEM_ID, 0, "首页");
        homeItem.setIcon(R.drawable.ic_done_white_24dp);
        homeItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        if (item.getItemId() == SAVE_MENU_ITEM_ID) {
            strUserName = etUserName.getText().toString();
            strUserDescripe = etUserDescripe.getText().toString();
            strUserLocation = etUserLocation.getText().toString();
            if (mUser != null) {
                User newUser = new User();
                if (!"".equals(strUserName.trim())) {
                    newUser.setUserName(strUserName);
                }
                if (!"".equals(strUserDescripe.trim())) {
                    newUser.setUserDescription(strUserDescripe);
                }
                if (!"".equals(strUserLocation.trim())) {
                    newUser.setUserLocation(strUserLocation);
                }
                newUser.setGender(bUserGender);
                if (!"".equals(strUserHeadUrl.trim())) {
                    newUser.setUserHeadUrl(strUserHeadUrl);
                }
                newUser.update(mUser.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(UserDetailActivity.this, "update success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UserDetailActivity.this, HomeActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(UserDetailActivity.this, "update fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void showChooseHead() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置头像");
        String[] items = {"选择本地图片", "拍照"};
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                switch (which) {
                    case CHOOSE_HEAD_LOCAL:
                        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        startActivityForResult(openAlbumIntent, CHOOSE_HEAD_LOCAL);
                        break;
                    case CHOOSE_HEAD_TAKE:
                        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        tempUri = Uri.fromFile(new File(
                                Environment.getExternalStorageDirectory(), "image.jpg"));
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
                    break;
            }
        }
    }

    private void startImageZoom(final Uri uri) {
        Toast.makeText(this, "zoom", Toast.LENGTH_SHORT).show();
        if (uri == null) {
            throw new IllegalArgumentException("Uri can not be null");
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", "1");
        intent.putExtra("aspectY", "1");
        intent.putExtra("outputX", "150");
        intent.putExtra("outputY", "150");
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_HEAD);
    }

    private void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            File file = new File(Environment.getExternalStorageDirectory(), "head.png");
            Log.e("UserDetail", Environment.getExternalStorageDirectory().toString());
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream stream = new FileOutputStream(file);
                if (photo.compress(Bitmap.CompressFormat.PNG, 90, stream)) {
                    stream.flush();
                    stream.close();
                }
                final BmobFile bmobFile = new BmobFile(file);
                bmobFile.upload(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            String url = bmobFile.getFileUrl();
                            strUserHeadUrl = bmobFile.getFileUrl();
                            Log.e("UserDetail", url);
                        }
                    }
                });
            } catch (FileNotFoundException e) {
                Log.e("UserDetail", e.toString());
            } catch (IOException e) {
                Log.e("UserDetail", e.toString());
            }
            civHeadUser.setImageBitmap(photo);
        }
    }
}
