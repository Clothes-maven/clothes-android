package com.cloth.clothes.saledetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cloth.clothes.R;
import com.cloth.clothes.clothesdetail.DetailClothesAdapter;
import com.cloth.clothes.home.salelist.domain.model.SaleBean;
import com.cloth.kernel.base.BaseActivity;
import com.cloth.kernel.service.LcRouterWrapper;

import java.io.Serializable;

import butterknife.BindView;

@Route(path = SaleDetailActivity.PATH)
public class SaleDetailActivity extends BaseActivity {
    public static final String PATH = "/main/sale";
    public static final String SALE_BEAN = "sale_bean";

    public static void jump(SaleBean saleBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(SALE_BEAN, saleBean);
        LcRouterWrapper.getLcRouterWrapper().jumpActWithBundle(PATH,bundle);
    }

    @BindView(R.id.activity_sale_detail_recycler_view)
    RecyclerView mRecyclerView;


    @Nullable
    @Override
    protected String getTitleStr() {
        return "卖出商品详情";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sale_detail;
    }

    @Override
    protected void init() {

        Bundle bundle = getIntent().getExtras();
        if (bundle ==null) return;

        SaleBean saleBean = (SaleBean) bundle.getSerializable(SALE_BEAN);
        SaleDetailClothesAdapter mSaleDetailClothesAdapter = new SaleDetailClothesAdapter(this, saleBean);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mSaleDetailClothesAdapter);

    }
}
