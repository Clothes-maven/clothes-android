package com.cloth.clothes.clothesdetail;

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
import com.cloth.clothes.clothesdetail.domain.usecase.HttpFixClothesUseCase;
import com.cloth.clothes.clothesdetail.domain.usecase.HttpSellClothesUseCase;
import com.cloth.clothes.clothesdetail.widget.InputDialogBuilder;
import com.cloth.clothes.home.HomeContract;
import com.cloth.clothes.home.domain.model.ClothesBean;
import com.cloth.clothes.model.BaseHttpRepository;
import com.cloth.clothes.model.Role;
import com.cloth.clothes.model.UserManager;
import com.cloth.clothes.clothessecondlist.domain.model.ClothesSecondModel;
import com.cloth.clothes.utils.StringUtils;
import com.cloth.kernel.base.BaseActivity;
import com.cloth.kernel.base.mvpclean.UseCaseHandler;
import com.cloth.kernel.base.utils.ToastUtil;
import com.cloth.kernel.service.DialogWrapper;
import com.cloth.kernel.service.LcEventBusWrapper;
import com.cloth.kernel.service.LcRouterWrapper;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = DetailActivity.PATH)
public class DetailActivity extends BaseActivity implements DetailContranct.IView{
    public static final String PATH = "/main/detail";
    private static String CLOTHES = "clothes_group_bean";
    private static String POSITION = "clothes_position";

    public static void jump(ClothesSecondModel numberModel, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(CLOTHES, numberModel);
        bundle.putInt(POSITION,position);
        LcRouterWrapper.getLcRouterWrapper().jumpActWithBundle(PATH,bundle);
    }

    @BindView(R.id.activity_detail_recycler_view)
    RecyclerView mRecyclerView;
//    @BindView(R.id.activity_detail_fix_img)
//    ImageView mFixClothes;


    private DetailContranct.IPresenter mIPresenter;
//    private ClothesBean clothesBean;
    private ClothesSecondModel mClothesGroupModel;
    private DetailClothesAdapter mDetailClothesAdapter;
    private int mPosition;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Nullable
    @Override
    protected String getTitleStr() {
        return "商品详情";
    }

    @Override
    protected void init() {
        mIPresenter = new DetailPresenter(this, UseCaseHandler.getInstance(),new HttpSellClothesUseCase(BaseHttpRepository.getBaseHttpRepository()),new HttpFixClothesUseCase(BaseHttpRepository.getBaseHttpRepository()));
        if (Role.isOwner(UserManager.getInstance().getRole())) {
//            mFixClothes.setVisibility(View.VISIBLE);
        }

        Bundle extras = getIntent().getExtras();
        if (extras == null) return;

        mClothesGroupModel = (ClothesSecondModel) extras.getSerializable(CLOTHES);
        mPosition =extras.getInt(POSITION);
        if (mClothesGroupModel ==null) return;

        mDetailClothesAdapter = new DetailClothesAdapter(this,mClothesGroupModel,false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        DividerItemDecoration decoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mDetailClothesAdapter);

    }

    @OnClick(R.id.activity_detail_sell_img)
    public void sell(View view) {
        new InputDialogBuilder(this)
                .setTitle("价格")
                .setMessage("请填写卖出衣服的价格")
                .setListener(new InputDialogBuilder.Listener() {
                    @Override
                    public void numberCost(String number, String sell) {
                        if (!StringUtils.isDouble(sell)) {
                            ToastUtil.showLongMsg(DetailActivity.this,"价格为纯数字");
                            return;
                        }
                        mIPresenter.sellClothes(mClothesGroupModel.id,UserManager.getInstance().getId(),sell,number);
                    }
                }).showDialog();

    }

    @OnClick(R.id.activity_detail_fix_img)
    public void fix(View view) {
        switchRightTv(View.VISIBLE,null);
        mDetailClothesAdapter.setEnable(true);
    }


    @Override
    public void onRightClick(View view) {
        ClothesBean clothesBean = mDetailClothesAdapter.getClothesBean();
        if (clothesBean !=null) {
            DialogWrapper.waitDialog(this);
            mIPresenter.fixClothes(clothesBean);
        }
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
    public void finish() {
        super.finish();
        LcEventBusWrapper.getInstance().doEvent(HomeContract.IStoreViewFrg.class).refreshItem(mPosition,mClothesGroupModel.clothe);
    }

    @Override
    public void sellSuccess() {
        mClothesGroupModel.number--;
        DialogWrapper.tipSuccessDialog(this);
        mDetailClothesAdapter.refresh(mClothesGroupModel);
    }

    @Override
    public void fixSuccess() {
        DialogWrapper.tipSuccessDialog(this);
        switchRightTv(View.GONE,null);
        DialogWrapper.dismissWaitDialog();
        mDetailClothesAdapter.setEnable(false);
        mDetailClothesAdapter.refresh(mClothesGroupModel);
    }

    @Override
    public void error(String msg) {
        DialogWrapper.dismissWaitDialog();
        DialogWrapper.tipErrorDialog(this,msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIPresenter.onDetach();
    }
}
