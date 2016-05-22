package com.jihao.baselibrary.http.builder;


import com.jihao.baselibrary.http.OkHttpUtils;
import com.jihao.baselibrary.http.request.OtherRequest;
import com.jihao.baselibrary.http.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers).build();
    }
}
