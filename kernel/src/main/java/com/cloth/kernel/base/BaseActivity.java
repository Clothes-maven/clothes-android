package com.cloth.kernel.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.cloth.kernel.R;

import butterknife.ButterKnife;

/**
 * Activity 的基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected TextView mRightTv;
    protected TextView mTitleTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        if (isSetStatusBarColor() && android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initHeader();
        init();
    }

    protected abstract @LayoutRes
    int getLayoutId();

    protected boolean isSetStatusBarColor() {
        return true;
    }

    protected @Nullable
    String getTitleStr() {
        return "";
    }

    protected void init() {
    }

    protected   void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (!fragment.isAdded()) {
            transaction.add(frameId, fragment).commit();
        } else {
            transaction.show(fragment).commit();
        }
    }

    protected void switchContent(@NonNull FragmentManager fragmentManager,
                              @IdRes int frameId, @NonNull Fragment from, @NonNull Fragment to) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (!to.isAdded()) {
            transaction.hide(from).add(frameId, to).commit();
        } else {
            transaction.hide(from).show(to).commit();
        }
    }

    public void onBackClick(View view) {
        finish();
    }

    private void initHeader() {
        mTitleTv = findViewById(R.id.header_view_title_tv);
        mRightTv = findViewById(R.id.header_view_right_tv);
        if (mTitleTv == null) return;

        mTitleTv.setText(getTitleStr());
    }

    protected void setTitle(@Nullable String title) {
        if (mTitleTv != null) {
            mTitleTv.setText(title);
        }
    }

    protected void switchRightTv(int visibility, String msg) {
        if (mRightTv == null) return;
        if (!TextUtils.isEmpty(msg)) {
            mRightTv.setText(msg);
        }
        mRightTv.setVisibility(visibility);
    }


    public void onRightClick(View view) {

    }
}
