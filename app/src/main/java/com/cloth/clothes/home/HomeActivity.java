package com.cloth.clothes.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cloth.clothes.R;
import com.cloth.clothes.model.Role;
import com.cloth.kernel.base.BaseActivity;
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    private long mRole;
    private long mId;

    @Override
    protected void init() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mRole = extras.getLong(ROLE);
            mId = extras.getLong(ID);
        }
        if (Role.isOwner(mRole)) {

        }
        if (Role.isEmployee(mRole)) {

        }
        if (Role.isPurchase(mRole)) {

        }

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("dddsakdjsakfsj");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);

        HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (homeFragment == null) {
            // Create the fragment
            homeFragment = HomeFragment.newInstance();
            addFragmentToActivity(
                    getSupportFragmentManager(), homeFragment, R.id.contentFrame);
        }

    }

    @OnClick(R.id.fab_add_task)
    public void fabClick() {
        ToastUtil.showShortMsg(this,"add");
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
