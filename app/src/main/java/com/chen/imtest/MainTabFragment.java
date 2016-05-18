package com.chen.imtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chen.imtest.fragment.BaseFragment;
import com.chen.imtest.fragment.FlupFragment;
import com.chen.imtest.fragment.GroupFragment;
import com.chen.imtest.fragment.SessionFragment;
import com.chen.imtest.viewpager.PagerSlidingTabStrip;

/**
 * Created by jiahao on 16/5/18.
 */
public class MainTabFragment extends BaseFragment implements ViewPager.OnPageChangeListener{
    private View mRootView;

    private PagerSlidingTabStrip mTabs;
    private ViewPager mViewPager;
    private MainTabPagerAdapter mAdapter;

    private Fragment[] mFragments = {new SessionFragment(),new FlupFragment(),new GroupFragment()};
    private String[] tabNames = {"最新","随访","分组"};

    private int scrollState;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.main, container, false);
        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTabs = findView(R.id.tabs);
        mViewPager = findView(R.id.main_tab_pager);

        mAdapter = new MainTabPagerAdapter(getChildFragmentManager());
        mViewPager.setOffscreenPageLimit(mFragments.length);
        // page swtich animation
//        pager.setPageTransformer(true, new FadeInOutPageTransformer());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(this);

        mTabs.setOnCustomTabListener(new PagerSlidingTabStrip.OnCustomTabListener() {
            @Override
            public int getTabLayoutResId(int position) {
                return R.layout.tab_layout_main;
            }

            @Override
            public boolean screenAdaptation() {
                return true;
            }
        });
        mTabs.setViewPager(mViewPager);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mTabs.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        mTabs.onPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        mTabs.onPageScrollStateChanged(state);
        scrollState = state;
    }

    class MainTabPagerAdapter extends FragmentStatePagerAdapter{

        public MainTabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments[position];
        }

        @Override
        public int getCount() {
            return mFragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabNames[position];
        }
    }

}
