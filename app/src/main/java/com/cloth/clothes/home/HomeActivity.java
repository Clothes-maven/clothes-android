package com.cloth.clothes.home;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cloth.clothes.R;
import com.cloth.clothes.model.Role;
import com.cloth.kernel.base.BaseActivity;
import com.cloth.kernel.service.LcRouterWrapper;

import butterknife.BindView;

@Route(path = HomeActivity.PATH)
public class HomeActivity extends BaseActivity {
    public static final String PATH = "/main/home";
    private static final String ROLE = "params_role";
    private static final String ID = "params_id";

    public static void jump(@Role.ROLE long role, long id) {
        Bundle bundle = new Bundle();
        bundle.putLong(ROLE, role);
        bundle.putLong(ID, id);
        LcRouterWrapper.getLcRouterWrapper().jumpActWithPath(PATH);
    }

    @BindView(R.id.fab_add_task)
    FloatingActionButton fab;



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

    }




    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }


}
