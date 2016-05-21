package com.jihao.baselibrary.http;

import com.google.gson.Gson;

/**
 * Created by jiahao on 16/5/21.
 */
public class GsonUtils {
    private static Gson mGson;

    public static synchronized Gson getInstance() {
        if(mGson == null) {
            mGson = new Gson();
        }
        return mGson;
    }
}
