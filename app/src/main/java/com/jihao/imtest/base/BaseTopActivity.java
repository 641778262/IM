package com.jihao.imtest.base;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.jihao.imtest.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by json on 15/8/4.
 */
public abstract class BaseTopActivity extends BaseActivity {

    protected LinearLayout mLayout;
    protected RelativeLayout mLeftLayout;
    protected TextView mTitleText;
    protected Button mRightBtn;
    protected LinearLayout mTopCenterLayout;
    protected LinearLayout mContentLayout;
    protected RelativeLayout mTopLayout;
    protected ImageView mRightImageView;
    protected RelativeLayout mRightLayout;

    /**
     * 将View添加到content里
     *
     * @param resId
     */
    @Override
    public void setContentView(int resId) {
        super.setContentView(R.layout.base_top);
        mContentLayout = (LinearLayout) findViewById(R.id.top_ll_content);
        View contentView = View.inflate(this, resId, mContentLayout);
        mLayout = findView(R.id.top_root);
        mLeftLayout = findView(R.id.top_rl_left);
        mTitleText = findView(R.id.top_tv_title);
        mRightBtn = findView(R.id.top_bt_right);
        mTopCenterLayout = findView(R.id.top_ll_center);
        mContentLayout = findView(R.id.top_ll_content);
        mTopLayout = findView(R.id.top_layout);
        mRightImageView = findView(R.id.top_iv_right);
        mRightLayout = findView(R.id.top_rl_right);
        ButterKnife.bind(this, mLayout);
        addViewToTopCenterLayout();
        initListView(contentView);

    }


    /**
     * 处理带ListView的情况
     *
     * @param contentView
     */
    protected void initListView(View contentView) {

    }

    @OnClick({R.id.top_rl_left, R.id.top_bt_right, R.id.top_rl_right})
    public void onTopClick(View view) {
        switch (view.getId()) {
            case R.id.top_rl_left:
                clickLeftBtn();
                break;
            case R.id.top_bt_right:
            case R.id.top_rl_right:
                clickRightBtn();
                break;
        }
    }

    public static final int MODE_LEFT_TITLE = 0;//展示左边返回按钮以及标题
    public static final int MODE_TITLE = 1;//只展示标题
    public static final int MODE_LEFT_TITLE_RIGHT = 2;//展示左边返回按钮以及标题
    public static final int MODE_LEFT_CENTER = 3;//展示左边返回按钮以及中部内容
    public static final int MODE_CENTER_RIGHT = 4;//展示中部内容以及右边按钮
    public static final int MODE_LEFT_CENTER_RIGHT = 5;//展示左边返回按钮、中部内容以及右边按钮
    public static final int MODE_NONE = 6;//隐藏topBar
    public static final int MODE_TITLE_RIGHT = 7;//展示标题和右边按钮

    protected void setMode(int mode) {
        switch (mode) {
            case MODE_LEFT_TITLE:
                break;
            case MODE_TITLE:
                hideLeftBtn();
                break;
            case MODE_LEFT_TITLE_RIGHT:
                showRightBtn();
                break;
            case MODE_LEFT_CENTER:
                showTopCenterLayout();
                break;
            case MODE_CENTER_RIGHT:
                hideLeftBtn();
                showTopCenterLayout();
                showRightBtn();
                break;
            case MODE_LEFT_CENTER_RIGHT:
                showTopCenterLayout();
                showRightBtn();
                break;
            case MODE_NONE:
                hideTopBar();
                break;
            case MODE_TITLE_RIGHT:
                hideLeftBtn();
                showRightBtn();
                break;
        }
    }

    /**
     * 点击返回按钮
     */
    public void clickLeftBtn() {
        this.finish();
    }

    /**
     * 点击右侧按钮
     */
    public void clickRightBtn() {

    }

    /**
     * 展示右侧按钮
     */
    protected void showRightBtn() {
        if (mRightBtn.getVisibility() != View.VISIBLE) {
            mRightBtn.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 展示顶部中间内容
     */
    protected void showTopCenterLayout() {
        mTitleText.setVisibility(View.GONE);
        mTopCenterLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏左边按钮
     */
    protected void hideLeftBtn() {
        mLeftLayout.setVisibility(View.GONE);
    }

    /**
     * 设置标题
     *
     * @param str
     */
    protected void setTitleText(String str) {
        mTitleText.setText(str);
    }

    /**
     * 设置标题
     *
     * @param resId
     */
    protected void setTitleText(int resId) {
        mTitleText.setText(resId);
    }

    /**
     * 隐藏顶部栏
     */
    protected void hideTopBar() {
        mTopLayout.setVisibility(View.GONE);
    }

    /**
     * 顶部中间内容
     */
    protected void addViewToTopCenterLayout() {

    }

    /**
     * 设置右边按钮的字
     *
     * @param resId
     */
    public void setRightBtnText(int resId) {
        mRightBtn.setText(resId);
    }

    public void setRightBtnText(String str) {
        mRightBtn.setText(str);
    }

    /**
     * 设置右边按钮字体颜色
     *
     * @param color
     */
    public void setRightBtnTextColor(int color) {
        mRightBtn.setTextColor(color);
    }

//    public void setRightBtnTextBlue() {
//        mRightBtn.setTextColor(getResources().getColorStateList(R.drawable.selector_text_blue));
//    }
//
//    public void setRightBtnTextOrange() {
//        mRightBtn.setTextColor(getResources().getColorStateList(R.drawable.selector_text_orange));
//    }

    /**
     * 设置右边按钮背景
     *
     * @param bg
     */
    public void setRightBtnBg(int bg) {
        mRightBtn.setVisibility(View.GONE);
        mRightLayout.setVisibility(View.VISIBLE);
        mRightImageView.setImageResource(bg);
    }

    protected <T extends View> T findView(int resId) {
        return (T) (findViewById(resId));
    }
}
