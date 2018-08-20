package com.cloth.clothes.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cloth.clothes.R;
import com.cloth.clothes.addclothes.AddClothesActivity;
import com.cloth.clothes.home.homefragment.StoreFragment;
import com.cloth.clothes.home.homefragment.domain.usecase.HttpGetClothesUseCase;
import com.cloth.clothes.home.salelist.SaleListFragment;
import com.cloth.clothes.home.salelist.domain.usecase.HttpSaleListUseCase;
import com.cloth.clothes.model.BaseHttpRepository;
import com.cloth.clothes.model.LcBaseDataRepository;
import com.cloth.clothes.model.Role;
import com.cloth.clothes.model.UserManager;
import com.cloth.kernel.base.BaseActivity;
import com.cloth.kernel.base.mvpclean.UseCaseHandler;
import com.cloth.kernel.base.utils.ToastUtil;
import com.cloth.kernel.service.LcRouterWrapper;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = HomeActivity.PATH)
public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String PATH = "/main/home";
    private static final String ROLE = "params_role";
    private static final String ID = "params_id";
    private static final String NAME = "params_name";

    public static void jump(@Role.ROLE long role, long id) {
        Bundle bundle = new Bundle();
        bundle.putLong(ROLE, role);
        bundle.putLong(ID, id);
        LcRouterWrapper.getLcRouterWrapper().jumpActWithPath(PATH);
    }

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    MenuItem mSellListItem;

    TextView mNameTv;
    TextView mAddressTv;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    private long mRole;
    private long mId;
    private HomeContract.IPresenter mIPresenter;

    private StoreFragment mStoreFragment;
    private SaleListFragment mSaleListFragment;
    private Fragment currentFragm;

    @Override
    protected void init() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mRole = extras.getLong(ROLE);
            mId = extras.getLong(ID);
        }
        mSellListItem = mNavigationView.getMenu().findItem(R.id.nav_sell_list);
        mNameTv = mNavigationView.getHeaderView(0).findViewById(R.id.nav_header_main_name_tv);
        mAddressTv = mNavigationView.getHeaderView(0).findViewById(R.id.nav_header_main_address_tv);
        if (Role.isOwner(mRole)) {
            mSellListItem.setVisible(false);
        }
        if (Role.isEmployee(mRole)) {

        }
        if (Role.isPurchase(mRole)) {

        }
        mNameTv.setText(UserManager.getInstance().getUser().name);
        mAddressTv.setText(UserManager.getInstance().getUser().address);


        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("仓库");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);

        mIPresenter = new HomeFrgPresenter(UseCaseHandler.getInstance(),
                new HttpGetClothesUseCase(BaseHttpRepository.getBaseHttpRepository(), LcBaseDataRepository.getLcBaseRepository()),
                new HttpSaleListUseCase(BaseHttpRepository.getBaseHttpRepository()));


        mStoreFragment = StoreFragment.newInstance();
        mStoreFragment.setPresenter(mIPresenter);
        addFragmentToActivity(getSupportFragmentManager(), mStoreFragment, R.id.contentFrame);

    }

    @OnClick(R.id.fab_add_task)
    public void fabClick() {
        AddClothesActivity.jump();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_about) {
            ToastUtil.showShortMsg(this, "功能开发中。。。");
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return true;
        } else if (id == R.id.nav_sell_list) {
            if (mSaleListFragment ==null) {
                mSaleListFragment = SaleListFragment.newInstance();
                mSaleListFragment.setPresenter(mIPresenter);
            }
            switchContent(getSupportFragmentManager(),R.id.contentFrame,currentFragm,mSaleListFragment);
        } else if (id ==R.id.nav_store) {
            switchContent(getSupportFragmentManager(),R.id.contentFrame,currentFragm,mStoreFragment);
        }

        getSupportActionBar().setTitle(item.getTitle());
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected boolean isSetStatusBarColor() {
        return false;
    }

    @Override
    protected void addFragmentToActivity(@NonNull FragmentManager fragmentManager, @NonNull android.support.v4.app.Fragment fragment, int frameId) {
        super.addFragmentToActivity(fragmentManager, fragment, frameId);
        currentFragm = fragment;
    }

    @Override
    protected void switchContent(@NonNull FragmentManager fragmentManager, int frameId, @NonNull android.support.v4.app.Fragment from, @NonNull android.support.v4.app.Fragment to) {
        super.switchContent(fragmentManager, frameId, from, to);
        currentFragm = to;
    }



    //============================================================================
    private long clickTime = 0; //记录第一次点击的时间
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void exit() {
        if ((System.currentTimeMillis() - clickTime) > 2000) {
            ToastUtil.showLongMsg(getApplicationContext(), "再按一次后退键退出程序");
            clickTime = System.currentTimeMillis();
        } else {
            this.finish();
        }
    }
}
