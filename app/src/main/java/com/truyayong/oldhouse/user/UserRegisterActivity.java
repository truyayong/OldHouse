package com.truyayong.oldhouse.user;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.truyayong.oldhouse.R;
import com.truyayong.oldhouse.data.User;
import com.truyayong.oldhouse.utils.UserUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

public class UserRegisterActivity extends AppCompatActivity {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserRegisterTask mAuthTask = null;

    String mPhone = "";
    String mPassword = "";
    String mVerifyCode = "";

    // UI references.
    private AutoCompleteTextView actvPhoneView;
    private EditText mPasswordView;
    private EditText etVerifyCode;
    private TextView tvGetVerifyCode;
    private View mProgressView;
    private View mRegisterFormView;

    CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            if (millisUntilFinished / 1000 == 60) {
                millisUntilFinished = 59 * 1000;
            }
            tvGetVerifyCode.setText(getString(R.string.register_reget_veriofy_code) + "(" + millisUntilFinished / 1000 + ")");
            tvGetVerifyCode.setEnabled(false);
        }

        @Override
        public void onFinish() {
            tvGetVerifyCode.setText(getString(R.string.register_get_veriofy_code));
            tvGetVerifyCode.setEnabled(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        // Set up the login form.
        actvPhoneView = (AutoCompleteTextView) findViewById(R.id.actv_user_register_phone);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.register_password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        etVerifyCode = (EditText)findViewById(R.id.et_user_register_verifycode);
        tvGetVerifyCode = (TextView)findViewById(R.id.tv_get_verifycode);
        tvGetVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhone = actvPhoneView.getText().toString();
                if (!UserUtil.isPhoneValid(mPhone)) {
                    actvPhoneView.setError(getString(R.string.error_invalid_phone));
                    return;
                }
                BmobSMS.requestSMSCode(mPhone, "OldHouse", new QueryListener<Integer>() {
                    @Override
                    public void done(Integer integer, BmobException e) {
                        if (e == null) {
                            Log.e("smile", "短信id："+integer);//用于后续的查询本次短信发送状态
                        } else {
                            Log.e("smile", "短信发送失败id："+integer);
                        }
                    }
                });
                countDownTimer.start();
            }
        });

        Button btnRegisterLogin = (Button) findViewById(R.id.btn_phone_register);
        btnRegisterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("phone_history", MODE_APPEND);
                Set<String> historys = sp.getStringSet("history", new HashSet<String>());
                historys.add(actvPhoneView.getText().toString());
                SharedPreferences.Editor editor = sp.edit();
                editor.putStringSet("history", historys).commit();
                attemptLogin();
            }
        });

        mRegisterFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);
    }

    private void populateAutoComplete() {
        SharedPreferences sp = getSharedPreferences("phone_history", MODE_PRIVATE);
        Set<String> historys = sp.getStringSet("history", new HashSet<String>());
        addPhonesToAutoComplete(new ArrayList<String>(historys));
        return;
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        actvPhoneView.setError(null);
        mPasswordView.setError(null);
        etVerifyCode.setError(null);

        // Store values at the time of the login attempt.
        mPhone = actvPhoneView.getText().toString();
        mPassword = mPasswordView.getText().toString();
        mVerifyCode = etVerifyCode.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(mPassword) && !UserUtil.isPasswordValid(mPassword)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(mPhone)) {
            actvPhoneView.setError(getString(R.string.error_field_required));
            focusView = actvPhoneView;
            cancel = true;
        } else if (!UserUtil.isPhoneValid(mPhone)) {
            actvPhoneView.setError(getString(R.string.error_invalid_phone));
            focusView = actvPhoneView;
            cancel = true;
        }

        if (!UserUtil.isVerifyCodeValid(mVerifyCode)) {
            etVerifyCode.setError(getString(R.string.error_invalid_verifycode));
            focusView = etVerifyCode;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserRegisterTask(mPhone, mPassword, mVerifyCode);
            mAuthTask.execute((Void) null);
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void addPhonesToAutoComplete(List<String> phoneNumberCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_dropdown_item_1line, phoneNumberCollection);
        actvPhoneView.setAdapter(adapter);
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final String mTaskPhone;
        private final String mTaskPassword;
        private final String mTaskVerifycode;

        UserRegisterTask(String phone, String password, String verifycode) {
            mTaskPhone = phone;
            mTaskPassword = password;
            mTaskVerifycode = verifycode;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                User mUser = new User();
                mUser.setMobilePhoneNumber(mTaskPhone);
                mUser.setPassword(mTaskPassword);
                mUser.signOrLogin(mTaskVerifycode, new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                    }
                });
                // Simulate network access.
                Thread.sleep(2000);
                Intent intent = new Intent(UserRegisterActivity.this, UserLoginActivity.class);
                startActivity(intent);
            } catch (InterruptedException e) {
                return false;
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }
}
