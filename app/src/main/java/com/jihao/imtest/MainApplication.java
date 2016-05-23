package com.jihao.imtest;

import android.app.Application;

import com.jihao.baselibrary.http.OkHttpUtils;
import com.jihao.imkit.NimUIKit;
import com.jihao.imkit.cache.DemoCache;

/**
 * Created by jiahao on 16/5/18.
 */
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DemoCache.setContext(this);
        NimUIKit.initIMKit(this);
        OkHttpUtils.getInstance().setContext(this);
    }
}
