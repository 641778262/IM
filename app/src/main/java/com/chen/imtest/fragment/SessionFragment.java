package com.chen.imtest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chen.imtest.R;

/**
 * Created by jiahao on 16/5/18.
 * 最新会话列表
 */
public class SessionFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test,container,false);
        TextView tv = (TextView) view.findViewById(R.id.tv_title);
        tv.setText("Session");
        return view;
    }
}
