package com.example.belon.project_2018.base;

import android.content.Intent;
import android.os.Bundle;

import butterknife.ButterKnife;

/**
 * Created by zhouyanan on 2018/2/9.
 */

public abstract class AppActivity extends BaseActivity {
    //获取第一个fragment
    protected abstract BaseFragment getFirstFragment();

    //获取Intent,接收数据的Activity实现这个方法
    protected void handleIntent(Intent intent) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        if (null != getIntent()) {
            handleIntent(getIntent());
        }
        //避免重复添加Fragment
        if (null == getSupportFragmentManager().getFragments()) {
            BaseFragment firstFragment = getFirstFragment();
            if (null != firstFragment) {
                addFragment(firstFragment);
            }
        }

    }

    @Override
    protected int getContentViewId() {
        return 0;
    }

    @Override
    protected int getFragmentContentId() {
        return 0;
    }
}
