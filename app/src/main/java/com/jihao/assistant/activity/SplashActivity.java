package com.jihao.assistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.jihao.baselibrary.preference.Preferences;
import com.jihao.assistant.MainActivity;
import com.jihao.assistant.R;
import com.jihao.assistant.base.BaseTopActivity;

import butterknife.Bind;

/**
 * Created by json on 16/5/23.
 */
public class SplashActivity extends BaseTopActivity {

    @Bind(R.id.iv_logo)
    ImageView mLogoIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setMode(MODE_NONE);
        mLogoIv.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!isEmpty(Preferences.getSid()) && !isEmpty(Preferences.getToken())) {
                    startActivity(new Intent(mActivity, MainActivity.class));
                } else {
                    startActivity(new Intent(mActivity,LoginActivity.class));
                }
                finish();
            }
        },3000);
    }
}
