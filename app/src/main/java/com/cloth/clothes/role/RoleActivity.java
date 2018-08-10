package com.cloth.clothes.role;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cloth.clothes.R;
import com.cloth.clothes.model.Role;
import com.cloth.clothes.login.LoginActivity;
import com.cloth.kernel.base.BaseActivity;
import com.cloth.kernel.service.LcRouterWrapper;

import butterknife.OnClick;


@Route(path = RoleActivity.PATH)
public class RoleActivity extends BaseActivity {
    public static final String PATH = "/main/role";

    public static void jump() {
        LcRouterWrapper.getLcRouterWrapper().jumpActWithPath(PATH);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_role;
    }

    @OnClick(R.id.activity_role_owner_ll)
    public void ownerListener(){
        LoginActivity.jump(Role.OWNER);
    }

    @OnClick(R.id.activity_role_purchase_ll)
    public void purchaseListener(){
        LoginActivity.jump(Role.PURCHASE);
    }

    @OnClick(R.id.activity_role_employee_ll)
    public void employeeListener(){
        LoginActivity.jump(Role.EMPLOYEE);
    }


}
