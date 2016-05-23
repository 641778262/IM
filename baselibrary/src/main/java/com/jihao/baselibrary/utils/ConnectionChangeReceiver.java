package com.jihao.baselibrary.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by json on 15/8/24.
 */
public class ConnectionChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                int nType = networkInfo.getType();
                if (nType == ConnectivityManager.TYPE_MOBILE) {
                    NetworkUtils.NETWORK_TYPE = NetworkUtils.NETWORK_MOBILE;

                } else if (nType == ConnectivityManager.TYPE_WIFI) {
                    NetworkUtils.NETWORK_TYPE = NetworkUtils.NETWORK_WIFI;

                } else {
                    NetworkUtils.NETWORK_TYPE = NetworkUtils.NETWORK_NONE;

                }
            } else {
                NetworkUtils.NETWORK_TYPE = NetworkUtils.NETWORK_NONE;
            }
        }
    }
}