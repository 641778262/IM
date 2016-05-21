package com.jihao.baselibrary.http;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

/**
 * Created by jiahao on 16/5/21.
 */
public class GsonRequest<T> extends StringRequest {


    public GsonRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }


}
