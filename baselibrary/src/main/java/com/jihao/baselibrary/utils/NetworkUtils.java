package com.jihao.baselibrary.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {

	public static final int NETWORK_NONE = -1;
	public static final int NETWORK_WIFI = 1;
	public static final int NETWORK_MOBILE = 0;
	public static int NETWORK_TYPE = NETWORK_MOBILE;

	@Deprecated
	public static int getAPNType(Context context) {
		return NETWORK_TYPE;
	}

	public static void initAPNType(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo == null || !networkInfo.isAvailable()) {
			NETWORK_TYPE = NETWORK_NONE;
			return;
		}
		int nType = networkInfo.getType();
		if (nType == ConnectivityManager.TYPE_MOBILE) {
			NETWORK_TYPE = NETWORK_MOBILE;
		} else if (nType == ConnectivityManager.TYPE_WIFI) {
			NETWORK_TYPE = NETWORK_WIFI;
		}
	}
}
