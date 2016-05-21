package com.chen.imtest;

import android.app.Application;

import com.chen.imkit.NimUIKit;
import com.chen.imkit.cache.DemoCache;

/**
 * Created by jiahao on 16/5/18.
 */
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DemoCache.setContext(this);
        NimUIKit.initIMKit(this);
    }
}
