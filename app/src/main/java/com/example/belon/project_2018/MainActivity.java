package com.example.belon.project_2018;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.belon.project_2018.base.AppActivity;
import com.example.belon.project_2018.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends AppActivity {

    List<Fragment> fragmentList;
    List<String> nameList;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @Override
    protected BaseFragment getFirstFragment() {
        return null;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentList = new ArrayList();
        nameList = new ArrayList();
        initData();
        viewpager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewpager);
    }


    private void initData() {
        fragmentList.add(PictureFragment.newInstance());
        fragmentList.add(RxAndReFragment.newInstance());
        fragmentList.add(RxjavaFragment.newInstance());
        nameList.add("图片");
        nameList.add("网络请求");
        nameList.add("RxJava");
    }


    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected int getFragmentContentId() {
        return 0;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return nameList.get(position);
        }
    }
}
