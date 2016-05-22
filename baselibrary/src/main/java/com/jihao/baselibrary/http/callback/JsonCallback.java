package com.jihao.baselibrary.http.callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by json on 16/5/22.
 */
public class JsonCallback<T> extends Callback{
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
}
