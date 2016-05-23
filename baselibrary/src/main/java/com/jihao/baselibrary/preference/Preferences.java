package com.jihao.baselibrary.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.jihao.baselibrary.http.OkHttpUtils;


/**
 * Created by hzxuwen on 2015/4/13.
 */
public class Preferences {
    private static final String KEY_IM_ACCOUNT = "im_account";
    private static final String KEY_IM_TOKEN = "im_token";
    private static final String KEY_APP_SID = "app_sid";
    private static final String KEY_APP_TOKEN = "app_token";
    public static final String SP_NAME = "user_info";



    public static void saveSid(String sid) {
        saveString(KEY_APP_SID,sid);
    }

    public static String getSid() {
        return getString(KEY_APP_SID);
    }

    public static void saveToken(String token) {
        saveString(KEY_APP_TOKEN,token);
    }

    public static String getToken() {
        return getString(KEY_APP_TOKEN);
    }

    /***
     * 云信相关
     * @return
     */

    public static void saveIMAccount(String account) {
        saveString(KEY_IM_ACCOUNT, account);
    }

    public static String getIMAccount() {
        return getString(KEY_IM_ACCOUNT);
    }

    public static void saveIMToken(String token) {
        saveString(KEY_IM_TOKEN, token);
    }

    public static String getIMToken() {
        return getString(KEY_IM_TOKEN);
    }

    private static void saveString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    private static String getString(String key) {
        return getSharedPreferences().getString(key, null);
    }

    static SharedPreferences getSharedPreferences() {
        return OkHttpUtils.mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }
}
