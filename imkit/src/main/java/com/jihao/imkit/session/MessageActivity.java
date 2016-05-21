package com.jihao.imkit.session;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.jihao.imkit.R;
import com.jihao.imkit.session.constans.Extras;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;

/**
 * Created by jiahao on 16/5/20.
 * 聊天界面
 */
public class MessageActivity extends FragmentActivity {
    public static final int TYPE_TEAM = 0;
    public static final int TYPE_P2P = 1;

    private TextView mNameTv;

    protected String sessionId;

    private SessionCustomization customization;

    private MessageFragment messageFragment;

    public static void start(Context context, String tid, int sessionType) {
        start(context, tid, sessionType, null, null);
    }

    public static void start(Context context, String tid, int sessionType, SessionCustomization customization,
                             Class<? extends Activity> backToClass) {
        Intent intent = new Intent();
        intent.putExtra(Extras.EXTRA_ACCOUNT, tid);
        intent.putExtra(Extras.EXTRA_CUSTOMIZATION, customization);
        intent.putExtra(Extras.EXTRA_BACK_TO_CLASS, backToClass);
        intent.putExtra(Extras.EXTRA_SESSION_TYPE, sessionType);
        intent.setClass(context, MessageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nim_team_message_activity);
        mNameTv = (TextView) findViewById(R.id.tv_name);


        Bundle arguments = getIntent().getExtras();
        int type = arguments.getInt(Extras.EXTRA_SESSION_TYPE);
        arguments.putSerializable(Extras.EXTRA_TYPE, type == TYPE_TEAM ? SessionTypeEnum.Team:SessionTypeEnum.P2P);
        messageFragment = new MessageFragment();
        messageFragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction().add(R.id.message_fragment_container,
                messageFragment).commitAllowingStateLoss();

        sessionId = getIntent().getStringExtra(Extras.EXTRA_ACCOUNT);
        customization = (SessionCustomization) getIntent().getSerializableExtra(Extras.EXTRA_CUSTOMIZATION);


    }

    @Override
    public void onBackPressed() {
        if (messageFragment == null || !messageFragment.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (messageFragment != null) {
            messageFragment.onActivityResult(requestCode, resultCode, data);
        }

        if (customization != null) {
            customization.onActivityResult(this, requestCode, resultCode, data);
        }
    }
}
