package com.cloth.clothes.addclothes;

import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cloth.clothes.R;
import com.cloth.clothes.detail.DetailClothesAdapter;
import com.cloth.clothes.detail.domain.usecase.HttpFixClothesUseCase;
import com.cloth.clothes.home.domain.model.ClothesBean;
import com.cloth.clothes.model.BaseHttpRepository;
import com.cloth.kernel.base.BaseActivity;
import com.cloth.kernel.base.mvpclean.UseCaseHandler;
import com.cloth.kernel.base.utils.ToastUtil;
import com.cloth.kernel.service.DialogWrapper;
import com.cloth.kernel.service.LcRouterWrapper;
import com.kongzue.dialog.v2.WaitDialog;

import butterknife.BindView;

@Route(path = AddClothesActivity.PATH)
public class AddClothesActivity extends BaseActivity implements AddContract.IView{
    public static final String PATH = "/main/addclothes";

    public static void jump() {
        LcRouterWrapper.getLcRouterWrapper().jumpActWithPath(PATH);
    }

    @BindView(R.id.activity_add_clothes_recyl)
    RecyclerView mRecyclerView;
    private DetailClothesAdapter mClothesAdapter;
    private AddContract.IPresenter mIPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_clothes;
    }

    @Nullable
    @Override
    protected String getTitleStr() {
        return "添加批次";
    }

    @Override
    protected void init() {
        switchRightTv(View.VISIBLE,"添加");
        mIPresenter = new AddPresenter(this, UseCaseHandler.getInstance(), new HttpFixClothesUseCase(BaseHttpRepository.getBaseHttpRepository()));
        mClothesAdapter = new DetailClothesAdapter(this,new ClothesBean(),true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        DividerItemDecoration decoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mClothesAdapter);
    }

    @Override
    public void onRightClick(View view) {
        mIPresenter.addClothes(mClothesAdapter.getClothesBean());
        DialogWrapper.waitDialog(this);
    }

    @Override
    public void success() {
        DialogWrapper.dismissWaitDialog();
        DialogWrapper.tipSuccessDialog(this,"添加成功");
        finish();
    }

    @Override
    public void error(String msg) {
        DialogWrapper.dismissWaitDialog();
        DialogWrapper.tipErrorDialog(this,msg);
    }
}
