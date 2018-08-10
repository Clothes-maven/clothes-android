package com.cloth.kernel.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.cloth.kernel.R;

import butterknife.ButterKnife;

/**
 * Activity 的基类
 */
public abstract class BaseActivity extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        init();
    }

    protected abstract @LayoutRes int  getLayoutId();

    protected   void init() {}

}
