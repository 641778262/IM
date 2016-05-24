package com.jihao.assistant.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jihao.baselibrary.http.OkHttpUtils;
import com.jihao.baselibrary.http.callback.Callback;
import com.jihao.baselibrary.preference.Preferences;
import com.jihao.imkit.UserPreferences;
import com.jihao.imkit.cache.DataCacheManager;
import com.jihao.imkit.cache.DemoCache;
import com.jihao.imkit.http.ContactHttpClient;
import com.jihao.imkit.utils.string.MD5;
import com.jihao.assistant.MainActivity;
import com.jihao.assistant.R;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

import java.io.Serializable;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

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
    private String account;

    private AbortableFuture<LoginInfo> loginRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ButterKnife.bind(this);
        if(!TextUtils.isEmpty(Preferences.getIMAccount()) && !TextUtils.isEmpty(Preferences.getIMToken())) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }


    @OnClick({R.id.bt_login})
    public void onClickView(View view) {
        if(view.getId() == R.id.bt_login) {
//            if(TextUtils.isEmpty(account)) {
//                register();
//            } else {
//
//            }
//            login();

            getUser();
        }
    }

    public void getUser()
    {
        String url = "/gists/c2a7c39532239ff261be";
        OkHttpUtils
                .get()//
                .url(url)//
                .build()//
                .execute(new Callback<Gist>(){

                    @Override
                    public Gist parseNetworkResponse(Response response) throws Exception {
                        return handleResponse(response,Gist.class);
                    }

                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(Gist response) {
                        Toast.makeText(getApplicationContext(),response.getId(),Toast.LENGTH_LONG).show();

                    }
                });
    }
    public class Gist implements Serializable {
        private String url;
        private String forks_url;
        private String commits_url;
        private String id;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getForks_url() {
            return forks_url;
        }

        public void setForks_url(String forks_url) {
            this.forks_url = forks_url;
        }

        public String getCommits_url() {
            return commits_url;
        }

        public void setCommits_url(String commits_url) {
            this.commits_url = commits_url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public void register() {
        account = nameET.getText().toString().trim();
        final String pwd = pwdEt.getText().toString().trim();
        final String nickName = nickEt.getText().toString().trim();


        ContactHttpClient.getInstance().register(account, nickName, pwd, new ContactHttpClient.ContactHttpCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
//                Toast.makeText(RegisterActivity.this, R.string.register_success, Toast.LENGTH_SHORT).show();
//                switchMode();  // 切换回登录
//                loginAccountEdit.setText(account);
//                loginPasswordEdit.setText(password);
//
//                registerAccountEdit.setText("");
//                registerNickNameEdit.setText("");
//                registerPasswordEdit.setText("");
//
//                DialogMaker.dismissProgressDialog();
                Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailed(int code, String errorMsg) {
                Toast.makeText(getApplicationContext(),"注册失败:{"+"错误码："+code+"},{"+"错误信息："+errorMsg+"}",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void login() {

        final String account = nameET.getEditableText().toString().toLowerCase();
        final String token = tokenFromPassword(pwdEt.getEditableText().toString());
        // 登录
        loginRequest = NIMClient.getService(AuthService.class).login(new LoginInfo(account, token));
        loginRequest.setCallback(new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {
//                LogUtil.i(TAG, "login success");

//                onLoginDone();
                DemoCache.setAccount(account);
                saveLoginInfo(account, token);

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
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                finish();
            }

            @Override
            public void onFailed(int code) {
//                onLoginDone();
                if (code == 302 || code == 404) {
                    Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "登录失败: " + code, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onException(Throwable exception) {

                exception.printStackTrace();

            }
        });

    }

    private void saveLoginInfo(final String account, final String token) {
        Preferences.saveIMAccount(account);
        Preferences.saveIMToken(token);
    }

    //DEMO中使用 username 作为 NIM 的account ，md5(password) 作为 token
    //开发者需要根据自己的实际情况配置自身用户系统和 NIM 用户系统的关系
    private String tokenFromPassword(String password) {
        String appKey = readAppKey(this);
        boolean isDemo = "45c6af3c98409b18a84451215d0bdd6e".equals(appKey)
                || "fe416640c8e8a72734219e1847ad2547".equals(appKey);

        return isDemo ? MD5.getStringMD5(password) : password;
    }

    private static String readAppKey(Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo != null) {
                return appInfo.metaData.getString("com.netease.nim.appKey");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
