package com.jihao.assistant;

import android.app.Application;

import com.jihao.baselibrary.http.OkHttpUtils;
import com.jihao.baselibrary.preference.Preferences;
import com.jihao.imkit.NimUIKit;
import com.jihao.assistant.constant.HttpConstants;

/**
 * Created by jiahao on 16/5/18.
 */
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpUtils.getInstance().setContext(this).debug("OkHttpUtils").setBaseUrl(HttpConstants
                .BASE_URL);
        NimUIKit.initIMKit(this, Preferences.getIMAccount(),Preferences.getIMToken());
    }
}
