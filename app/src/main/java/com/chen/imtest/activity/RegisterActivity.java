package com.chen.imtest.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.chen.imtest.MainActivity;
import com.chen.imtest.R;
import com.chen.imtest.preferences.Preferences;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by jiahao on 16/5/18.
 */
public class RegisterActivity extends Activity {
    @Bind(R.id.et_name)
    EditText nameET;
    @Bind(R.id.et_pwd)
    EditText pwdEt;
    @Bind(R.id.et_nickname)
    EditText nickEt;

    private String token;
    private String pwd;
    private String account;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.register);
        if(!TextUtils.isEmpty(Preferences.getUserAccount()) && !TextUtils.isEmpty(Preferences.getUserToken())) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @OnClick({R.id.bt_login})
    public void onClick(View view) {
        if(view.getId() == R.id.bt_login) {
            if(TextUtils.isEmpty(account)) {

            } else {

            }

        }
    }
}
