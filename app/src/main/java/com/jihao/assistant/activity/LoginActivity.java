package com.jihao.assistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jihao.baselibrary.http.OkHttpUtils;
import com.jihao.baselibrary.http.callback.Callback;
import com.jihao.baselibrary.preference.Preferences;
import com.jihao.baselibrary.utils.SystemUtil;
import com.jihao.imkit.UserPreferences;
import com.jihao.imkit.cache.DataCacheManager;
import com.jihao.imkit.cache.DemoCache;
import com.jihao.assistant.MainActivity;
import com.jihao.assistant.R;
import com.jihao.assistant.base.BaseTopActivity;
import com.jihao.assistant.bean.UserInfo;
import com.jihao.assistant.constant.HttpConstants;
import com.jihao.assistant.constant.HttpParams;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import okhttp3.Response;

/**
 * Created by jiahao on 16/5/23.
 */
public class LoginActivity extends BaseTopActivity {

    @Bind(R.id.et_phone)
    EditText mPhoneEt;
    @Bind(R.id.et_pwd)
    EditText mPwdEt;
    @Bind(R.id.bt_login)
    Button mLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setMode(MODE_TITLE);
        setTitleText(R.string.login);

        String phoneNum = SystemUtil.getPhoneNumber(this);
        if (!isEmpty(phoneNum)) {
            mPhoneEt.setText(phoneNum);
            mPhoneEt.setSelection(phoneNum.length());
        }

        mPwdEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    login();
                }
                return true;
            }
        });

    }

    @OnClick({R.id.bt_login})
    public void OnClickView(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                login();
                break;
        }
    }


    @OnTextChanged({R.id.et_phone, R.id.et_pwd})
    public void afterTextChanged(Editable editable) {
        if (mPwdEt.isFocused() || mPhoneEt.isFocused()) {
            if (validLogin(false)) {
                mLoginBtn.setEnabled(true);
            } else {
                mLoginBtn.setEnabled(false);
            }
        }
    }


    public void login() {
        if (validLogin(true)) {
            showProgressDialog(R.string.login_ing);

            OkHttpUtils.post().url(HttpConstants.LOGIN).params(getParams()).build().
                    execute(new Callback<UserInfo>() {

                        @Override
                        public UserInfo parseNetworkResponse(Response response) throws Exception {
                                return handleResponse(response,UserInfo.class);
                        }

                        @Override
                        public void onResponse(UserInfo userInfo) {
                            if(userInfo != null) {
                               saveUserInfo(userInfo);
                            }

                        }

                        @Override
                        public void onAfter() {
                            dismissProgressDialog();
                        }

                    });
        }
    }

    public void saveUserInfo(UserInfo userInfo) {
//        showProgressDialog(R.string.login_ing);

        Preferences.saveSid(userInfo.getSid());
        Preferences.saveToken(userInfo.getToken());

        final String account = userInfo.getAccId();
        final String token = userInfo.getYunxinToken();

        Preferences.saveIMAccount(account);
        Preferences.saveIMToken(token);

        AbortableFuture<LoginInfo>  loginRequest = NIMClient.getService(AuthService.class).
                login(new LoginInfo(account, token));
        loginRequest.setCallback(new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {
                DemoCache.setAccount(account);

                // 初始化消息提醒
                NIMClient.toggleNotification(UserPreferences.getNotificationToggle());

                // 初始化免打扰
                if (UserPreferences.getStatusConfig() == null) {
                    UserPreferences.setStatusConfig(DemoCache.getNotificationConfig());
                }
                NIMClient.updateStatusBarNotificationConfig(UserPreferences.getStatusConfig());

                // 构建缓存
                DataCacheManager.buildDataCacheAsync();

                // 进入主界面
                startActivity(new Intent(mActivity,MainActivity.class));
                finish();
            }

            @Override
            public void onFailed(int code) {
                if (code == 302 || code == 404) {
                    showToast("登录失败");
                } else {
                    showToast("登录失败: " + code);
                }
            }

            @Override
            public void onException(Throwable exception) {

                exception.printStackTrace();

            }
        });

//        mPwdEt.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                dismissProgressDialog();
//                startActivity(new Intent(mActivity,MainActivity.class));
//                finish();
//            }
//        },2000);
    }


    public HashMap<String, String> getParams() {
        HashMap<String, String> map = new HashMap<>();
        map.put(HttpParams.ACCOUNT, getEditTextString(mPhoneEt));
        map.put(HttpParams.PASSWORD, getEditTextString(mPwdEt));
        map.put(HttpParams.USER_TYPE, "1");
        return map;
    }

    public boolean validLogin(boolean showToast) {
        if (isEditTextEmpty(mPhoneEt)) {
            if (showToast) {
                showToast(R.string.phone_num_hint);
            }
            return false;
        }

        if (getEditTextString(mPhoneEt).length() != 11 || !getEditTextString(mPhoneEt).startsWith("1")) {
            if (showToast) {
                showToast(R.string.phone_num_error);
            }
            return false;
        }

        if (isEditTextEmpty(mPwdEt)) {
            if (showToast) {
                showToast(R.string.password_hint);
            }
            return false;
        }

        return true;
    }
}
