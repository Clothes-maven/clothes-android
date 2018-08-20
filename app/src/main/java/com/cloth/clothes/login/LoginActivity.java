package com.cloth.clothes.login;

import android.os.Bundle;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cloth.clothes.R;
import com.cloth.clothes.model.Role;
import com.cloth.clothes.model.BaseHttpRepository;
import com.cloth.clothes.home.HomeActivity;
import com.cloth.clothes.login.usecase.HttpLoginUseCase;
import com.cloth.kernel.base.BaseActivity;
import com.cloth.clothes.model.LcBaseDataRepository;
import com.cloth.kernel.base.mvpclean.UseCaseHandler;
import com.cloth.kernel.base.utils.ToastUtil;
import com.cloth.kernel.service.LcRouterWrapper;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = LoginActivity.PATH)
public class LoginActivity extends BaseActivity implements LoginContract.IView {
    public static final String PATH = "/main/login";
    private static final String ROLE = "login_role";

    public static void jump(@Role.ROLE long role) {
        Bundle bundle = new Bundle();
        bundle.putLong(ROLE, role);
        LcRouterWrapper.getLcRouterWrapper().jumpActWithBundle(LoginActivity.PATH, bundle);
    }

    private @Role.ROLE
    long mRole;
    @BindView(R.id.activity_login_name_et)
    EditText mName;

    @BindView(R.id.activity_login_pass_et)
    EditText mPass;

    private LoginContract.IPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mRole = bundle.getLong(ROLE);
        }
        mPresenter = new LoginPresenter(this, UseCaseHandler.getInstance(),
                new HttpLoginUseCase(BaseHttpRepository.getBaseHttpRepository(),
                        LcBaseDataRepository.getLcBaseRepository()));
    }

    @OnClick(R.id.activity_login_login_img)
    public void loginImg() {
        mPresenter.login(mName.getText().toString().trim(), mPass.getText().toString().trim());
    }

    @Override
    public void jumpHomeAct(@Role.ROLE long role ,long id) {
        HomeActivity.jump(role,id);
        finish();
    }

    @Override
    public void toastStr(String msg) {
        ToastUtil.showShortMsg(this, msg);
    }

}
