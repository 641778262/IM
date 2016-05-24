package com.jihao.assistant.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jihao.imkit.common.BaseFragment;
import com.jihao.assistant.R;

/**
 * Created by jiahao on 16/5/18.
 * 随访fragment
 */
public class FlupFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.test,container,false);
        TextView tv = (TextView) view.findViewById(R.id.tv_title);
        tv.setText("Flup");
        return view;
    }
}
