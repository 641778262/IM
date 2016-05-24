package com.jihao.assistant.activity;

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
import com.jihao.imkit.NimUIKit;
import com.jihao.assistant.MainActivity;
import com.jihao.assistant.R;
import com.jihao.assistant.base.BaseTopActivity;
import com.jihao.assistant.bean.UserInfo;
import com.jihao.assistant.constant.HttpConstants;
import com.jihao.assistant.constant.HttpParams;

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
        Preferences.saveSid(userInfo.getSid());
        Preferences.saveToken(userInfo.getToken());

        final String account = userInfo.getAccId();
        final String token = userInfo.getYunxinToken();

        Preferences.saveIMAccount(account);
        Preferences.saveIMToken(token);

        NimUIKit.initLoginData(account,token,mActivity,MainActivity.class);
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
