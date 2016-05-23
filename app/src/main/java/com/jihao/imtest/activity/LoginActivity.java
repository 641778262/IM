package com.jihao.imtest.activity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jihao.baselibrary.http.OkHttpUtils;
import com.jihao.baselibrary.http.callback.StringCallback;
import com.jihao.baselibrary.utils.SystemUtil;
import com.jihao.imkit.utils.LogUtil;
import com.jihao.imtest.R;
import com.jihao.imtest.base.BaseTopActivity;
import com.jihao.imtest.constant.HttpConstans;
import com.jihao.imtest.constant.HttpParams;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import okhttp3.Call;

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
        if(!isEmpty(phoneNum)) {
            mPhoneEt.setText(phoneNum);
            mPhoneEt.setSelection(phoneNum.length());
        }

    }

    @OnClick({R.id.bt_login})
    public void OnClickView(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                login();
                break;
        }
    }


    @OnTextChanged({R.id.et_phone,R.id.et_pwd})
    public void afterTextChanged(Editable editable) {
        if(mPwdEt.isFocused() || mPhoneEt.isFocused()) {
            if(validLogin(false)) {
                mLoginBtn.setEnabled(true);
            } else {
                mLoginBtn.setEnabled(false);
            }
        }
    }


    public void login() {
        if(validLogin(true)) {
            showProgressDialog(R.string.login_ing);
            OkHttpUtils.post().url(HttpConstans.LOGIN).params(getParams()).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e) {

                }

                @Override
                public void onResponse(String response) {
                    LogUtil.e("response",response);
                }

                @Override
                public void onAfter() {
                    dismissProgressDialog();
                }
            });
        }
    }

    public HashMap<String,String> getParams() {
        HashMap<String,String> map = new HashMap<>();
        map.put(HttpParams.ACCOUNT,getEditTextString(mPhoneEt));
        map.put(HttpParams.PASSWORD,getEditTextString(mPwdEt));
        map.put(HttpParams.USER_TYPE,"1");
        return map;
    }

    public boolean validLogin(boolean showToast) {
        if(isEditTextEmpty(mPhoneEt)) {
            if (showToast) {
                showToast(R.string.phone_num_hint);
            }
            return false;
        }

        if(getEditTextString(mPhoneEt).length() != 11 || !getEditTextString(mPhoneEt).startsWith("1")) {
            if(showToast) {
                showToast(R.string.phone_num_error);
            }
            return false;
        }

        if(isEditTextEmpty(mPwdEt)) {
            if(showToast) {
                showToast(R.string.password_hint);
            }
            return false;
        }

        return true;
    }
}
