package com.jihao.baselibrary.utils;

import android.content.Context;
import android.widget.Toast;

public final class ToastUtil {
	private static Toast mToast;
	public static void showToast(Context context, int resourceId) {
		if (context == null) {
			return;
		}
		if (mToast == null) {
			mToast = Toast.makeText(context.getApplicationContext(), resourceId, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(resourceId);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}

	public static void showToast(Context context, String message) {
		if (context == null) {
			return;
		}
		if (mToast == null) {
			mToast = Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(message);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}

}
