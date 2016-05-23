package com.jihao.imtest.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.jihao.baselibrary.utils.ToastUtil;


/**
 * Created by json on 15/8/4.
 */
public class BaseActivity extends FragmentActivity {
    protected Activity mActivity;
    protected ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
    }



//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (!APIConstant.DEV) {
//            Bugtags.onDispatchTouchEvent(this, ev);
//        }
//        return super.dispatchTouchEvent(ev);
//    }

    public int getPixelByDIP(int dp) {
        return (int) (getResources().getDisplayMetrics().density * dp + 0.5f);
    }

    public int getPixelBySP(int sp) {
        return (int) (getResources().getDisplayMetrics().scaledDensity * sp);
    }

    public int getScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public int getScreenHeight() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    public void collapseSoftInputMethod() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive())
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void showSoftInputMethod(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive())
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public void hideSoftInputMethod(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive())
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void showToast(String str) {
        ToastUtil.showToast(mActivity, str);
    }

    public void showToast(final int resId) {
        if (!Thread.currentThread().getName().equals("main")) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.showToast(mActivity, resId);
                }
            });
        } else {
            ToastUtil.showToast(mActivity, resId);
        }
    }

    public void showProgressDialog(int resId) {
        showProgressDialog(getString(resId), true);
    }

    public void showProgressDialog(String message) {
        showProgressDialog(message, true);
    }

    public void showProgressDialog(int resId, boolean cancelable) {
        showProgressDialog(getString(resId), cancelable);
    }

    public void showProgressDialog(String message, boolean cancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setCancelable(cancelable);
            }
            mProgressDialog.setMessage(message);
            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissProgressDialog() {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getStringByResId(int resId) {
        return getString(resId);
    }


    public boolean isEditTextEmpty(EditText et) {
        return TextUtils.isEmpty(getEditTextString(et));
    }

    public String getEditTextString(EditText et) {
        return et.getText().toString().trim();
    }

}
