package com.cloth.kernel.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.cloth.kernel.R;

import butterknife.ButterKnife;

/**
 * Activity 的基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected TextView mTitleTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        init();
        initHeader();
    }

    protected abstract @LayoutRes int getLayoutId();

    protected @Nullable
    String getTitleStr() {
        return "";
    }

    protected void init() {
    }

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    public void onBackClick(View view) {
        finish();
    }

    private void initHeader() {
        mTitleTv = findViewById(R.id.header_view_title_tv);
        if (mTitleTv != null) {
            mTitleTv.setText(getTitleStr());
        }
    }

    protected void setTitle(@Nullable String title) {
        if (mTitleTv != null) {
            mTitleTv.setText(title);
        }
    }

}
