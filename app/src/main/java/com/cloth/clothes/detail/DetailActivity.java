package com.cloth.clothes.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cloth.clothes.R;
import com.cloth.clothes.home.domain.model.ClothesBean;
import com.cloth.kernel.base.BaseActivity;
import com.cloth.kernel.service.LcRouterWrapper;

import butterknife.BindView;

@Route(path = DetailActivity.PATH)
public class DetailActivity extends BaseActivity {
    public static final String PATH = "/main/detail";
    private static String CLOTHES = "clothes_bean";

    public static void jump(ClothesBean clothesBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(CLOTHES, clothesBean);
        LcRouterWrapper.getLcRouterWrapper().jumpActWithBundle(PATH,bundle);
    }

    @BindView(R.id.activity_detail_clothes_name_tv)
    TextView mName;
    @BindView(R.id.activity_detail_clothes_feature_tv)
    TextView mFeature;
    @BindView(R.id.activity_detail_clothes_brand_tv)
    TextView mBrand;
    @BindView(R.id.activity_detail_clothes_type_tv)
    TextView mType;
    @BindView(R.id.activity_detail_clothes_size_tv)
    TextView mSize;
    @BindView(R.id.activity_detail_clothes_texture_tv)
    TextView mTexture;
    @BindView(R.id.activity_detail_clothes_collar_tv)
    TextView mCollar;
    @BindView(R.id.activity_detail_clothes_sleeve_tv)
    TextView mSleeve;
    @BindView(R.id.activity_detail_clothes_batch_tv)
    TextView mBatch;
    @BindView(R.id.activity_detail_clothes_cost_tv)
    TextView mCost;
    @BindView(R.id.activity_detail_clothes_profit_tv)
    TextView mProfit;
    @BindView(R.id.activity_detail_clothes_number_tv)
    TextView mNumber;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Nullable
    @Override
    protected String getTitleStr() {
        return "详情";
    }

    @Override
    protected void init() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) return;

        ClothesBean clothesBean = (ClothesBean) extras.getSerializable(CLOTHES);
        if (clothesBean ==null) return;

        mName.setText(clothesBean.name);
        mFeature.setText(clothesBean.feature);
        mBrand.setText(clothesBean.brand);
        mType.setText(clothesBean.type);
        mSize.setText(String.valueOf(clothesBean.size));
        mTexture.setText(clothesBean.texture);
        mCollar.setText(clothesBean.collar);
        mSleeve.setText(clothesBean.sleeve);
        mBatch.setText(clothesBean.batch);
        mCost.setText(String.valueOf(clothesBean.cost));
        mProfit.setText(String.valueOf(clothesBean.profit));
        mNumber.setText(String.valueOf(clothesBean.number));
    }
}
