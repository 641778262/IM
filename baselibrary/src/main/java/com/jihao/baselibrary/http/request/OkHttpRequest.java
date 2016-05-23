package com.jihao.baselibrary.http.request;


import android.text.TextUtils;

import com.jihao.baselibrary.http.OkHttpUtils;
import com.jihao.baselibrary.http.callback.Callback;
import com.jihao.baselibrary.http.utils.Exceptions;
import com.jihao.baselibrary.preference.Preferences;
import com.jihao.baselibrary.utils.SystemUtil;

import java.util.Map;

import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by zhy on 15/11/6.
 */
public abstract class OkHttpRequest
{
    protected String url;
    protected Object tag;
    protected Map<String, String> params;
    protected Map<String, String> headers;

    protected Request.Builder builder = new Request.Builder();

    protected OkHttpRequest(String url, Object tag,
                            Map<String, String> params, Map<String, String> headers)
    {
        this.url = url;
        this.tag = tag;
        this.params = params;
        this.headers = headers;

        if (url == null)
        {
            Exceptions.illegalArgument("url can not be null.");
        }

        initBuilder();
    }



    /**
     * 初始化一些基本参数 url , tag , headers
     */
    private void initBuilder()
    {
        builder.url(OkHttpUtils.mBaseUrl+url).tag(tag);
        appendHeaders();
    }

    protected abstract RequestBody buildRequestBody();

    protected RequestBody wrapRequestBody(RequestBody requestBody, final Callback callback)
    {
        return requestBody;
    }

    protected abstract Request buildRequest(RequestBody requestBody);

    public RequestCall build()
    {
        return new RequestCall(this);
    }


    public Request generateRequest(Callback callback)
    {
        RequestBody requestBody = buildRequestBody();
        RequestBody wrappedRequestBody = wrapRequestBody(requestBody, callback);
        Request request = buildRequest(wrappedRequestBody);
        return request;
    }


    protected void appendHeaders()
    {
        Headers.Builder headerBuilder = new Headers.Builder();

        //TODO 增加默认header
        if(!TextUtils.isEmpty(Preferences.getSid())) {
            headerBuilder.add("sid",Preferences.getSid());
        }
        if(!TextUtils.isEmpty(Preferences.getToken())) {
            headerBuilder.add("token",Preferences.getToken());
        }
        headerBuilder.add("user-agent","JiahaoApp");
        headerBuilder.add("version", SystemUtil.getVersionName(OkHttpUtils.mContext));
        headerBuilder.add("sysname",android.os.Build.VERSION.RELEASE);

        if(headers != null && headers.size() > 0) {
            for (String key : headers.keySet())
            {
                headerBuilder.add(key, headers.get(key));
            }
        }

        builder.headers(headerBuilder.build());
    }



}
