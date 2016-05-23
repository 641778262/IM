package com.jihao.baselibrary.http.callback;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jihao.baselibrary.R;
import com.jihao.baselibrary.http.OkHttpUtils;
import com.jihao.baselibrary.utils.NetworkUtils;
import com.jihao.baselibrary.utils.ToastUtil;

import org.json.JSONObject;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public abstract class Callback<T> {

    public static Handler mHandler = new Handler(Looper.getMainLooper());
    /**
     * UI Thread
     *
     * @param request
     */
    public void onBefore(Request request) {
    }

    /**
     * UI Thread
     *
     * @param
     */
    public void onAfter() {
    }

    /**
     * UI Thread
     *
     * @param progress
     */
    public void inProgress(float progress) {

    }

    /**
     * Thread Pool Thread
     *
     * @param response
     */
    public abstract T parseNetworkResponse(Response response) throws Exception;

    public void onError(Call call, Exception e){
        if(NetworkUtils.NETWORK_TYPE == NetworkUtils.NETWORK_NONE) {
            showToast(R.string.net_error);
        } else {
            showToast(R.string.data_error);
        }
    }

    public abstract void onResponse(T response);



    public static Callback CALLBACK_DEFAULT = new Callback() {

        @Override
        public Object parseNetworkResponse(Response response) throws Exception {
            return null;
        }

        @Override
        public void onError(Call call, Exception e) {

        }

        @Override
        public void onResponse(Object response) {

        }
    };

    /**
     * 因为response.body().string()方法只会返回一次数据(取数据流),
     * 这个方法是对返回数据格式做判断，方便取data字段的数据
     * @param response
     * @param type
     * @return
     */
    public T handleResponse(Response response,Type type) {
        T t = null;
        try {
            String json = response.body().string();
            JSONObject jsonObject = new JSONObject(json);
            if (!TextUtils.isEmpty(json)) {
                if (jsonObject.optInt("state") == 200) {
                    String data = jsonObject.optString("data");
                    t = new Gson().fromJson(data, type);
                    sendSuccessResultCallback(t);
                } else {
                    String msg = jsonObject.optString("message");
                    showToast(!TextUtils.isEmpty(msg) ? msg : OkHttpUtils.mContext.getString(R.string.data_error));
                    sendAfterResultCallback();
                }
            } else {
                //TODO 判断网络是否正常
                showToast(R.string.data_error);
                sendAfterResultCallback();
            }
        } catch (Exception e) {
            showToast(R.string.data_error);
            e.printStackTrace();
            sendFailResultCallback(e);
        }
        return t;
    }

    public void showToast(final int resId) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showToast(OkHttpUtils.mContext.getString(resId));
            }
        });
    }

    public void showToast(final String msg) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showToast(msg);
            }
        });
    }

    public void sendFailResultCallback(final Exception e)
    {

        mHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
               onError(null, e);
               onAfter();
            }
        });
    }

    public void sendSuccessResultCallback(final T object)
    {
        mHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                onResponse(object);
                onAfter();
            }
        });
    }

    public void sendAfterResultCallback()
    {
        mHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                onAfter();
            }
        });
    }

}