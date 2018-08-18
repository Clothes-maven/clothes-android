package com.cloth.clothes.detail;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cloth.clothes.R;
import com.cloth.clothes.detail.domain.usecase.HttpFixClothesUseCase;
import com.cloth.clothes.detail.domain.usecase.HttpSellClothesUseCase;
import com.cloth.clothes.home.domain.model.ClothesBean;
import com.cloth.clothes.model.Role;
import com.cloth.clothes.model.UserManager;
import com.cloth.kernel.base.BaseActivity;
import com.cloth.kernel.base.mvpclean.UseCaseHandler;
import com.cloth.kernel.base.utils.ToastUtil;
import com.cloth.kernel.service.DialogWrapper;
import com.cloth.kernel.service.LcRouterWrapper;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = DetailActivity.PATH)
public class DetailActivity extends BaseActivity implements DetailContranct.IView{
    public static final String PATH = "/main/detail";
    private static String CLOTHES = "clothes_bean";

    public static void jump(ClothesBean clothesBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(CLOTHES, clothesBean);
        LcRouterWrapper.getLcRouterWrapper().jumpActWithBundle(PATH,bundle);
    }

    @BindView(R.id.activity_detail_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.activity_detail_fix_img)
    ImageView mFixClothes;


    private DetailContranct.IPresenter mIPresenter;
    private ClothesBean clothesBean;
    private DetailClothesAdapter mDetailClothesAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Nullable
    @Override
    protected String getTitleStr() {
        return "商品详情";
    }

    @OnClick(R.id.activity_detail_sell_img)
    public void sell(View view) {
        DialogWrapper.inputDialog(this, "验证", "请填写您的姓名",  new DialogWrapper.InputDialogOkButtonClickListener() {
            @Override
            public void onClick(Dialog dialog, String inputText) {
                mIPresenter.sellClothes(clothesBean,inputText);
                ToastUtil.showShortMsg(DetailActivity.this, inputText);
                dialog.dismiss();
            }
        });
    }

    @OnClick(R.id.activity_detail_fix_img)
    public void fix(View view) {
        switchRightTv(View.VISIBLE,null);
        mDetailClothesAdapter.setEnable(true);
    }



    @Override
    public void onRightClick(View view) {
        DialogWrapper.waitDialog(this);
        mIPresenter.fixClothes(clothesBean);
    }

    @Override
    public void onBackClick(View view) {
        if (mRightTv.getVisibility() == View.VISIBLE) {
            switchRightTv(View.GONE,null);
            mDetailClothesAdapter.setEnable(false);
            return;
        }
        super.onBackClick(view);
    }

    @Override
    protected void init() {
        mIPresenter = new DetailPresenter(this, UseCaseHandler.getInstance(),new HttpSellClothesUseCase(),new HttpFixClothesUseCase());
        if (Role.isOwner(UserManager.getInstance().getId())) {
            mFixClothes.setVisibility(View.VISIBLE);
        }

        Bundle extras = getIntent().getExtras();
        if (extras == null) return;

        clothesBean = (ClothesBean) extras.getSerializable(CLOTHES);
        if (clothesBean ==null) return;

        mDetailClothesAdapter = new DetailClothesAdapter(this,clothesBean);
        mDetailClothesAdapter.setEnable(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        DividerItemDecoration decoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mDetailClothesAdapter);

    }

    @Override
    public void success() {
        switchRightTv(View.GONE,null);
        DialogWrapper.dismissWaitDialog();
        DialogWrapper.tipSuccessDialog(this);
        mDetailClothesAdapter.setEnable(false);
    }

    @Override
    public void error(String msg) {
        DialogWrapper.dismissWaitDialog();
        DialogWrapper.tipErrorDialog(this,msg);
    }

}
