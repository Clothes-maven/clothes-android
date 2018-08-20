package com.cloth.clothes.home.salelist;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cloth.clothes.R;
import com.cloth.clothes.home.HomeContract;
import com.cloth.clothes.home.salelist.domain.model.SaleBean;
import com.cloth.kernel.base.BaseFragment;
import com.cloth.kernel.base.utils.ToastUtil;

import java.util.List;

public class SaleListFragment extends BaseFragment implements HomeContract.ISaleView{

    public static SaleListFragment newInstance() {
        return new SaleListFragment();
    }


    private RecyclerView mRecyclerView;
    private SaleListFragmentAdapter mAdapter;
    private HomeContract.IPresenter mIPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sale_list;
    }

    @Override
    protected void init(View view) {
        mRecyclerView = view.findViewById(R.id.fragment_sell_list_salelist_rc);
        mAdapter = new SaleListFragmentAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        mIPresenter.saleList(this);
    }


    @Override
    public void success(List<SaleBean> list) {
        mAdapter.setSaleBeanList(list);
    }

    @Override
    public void error(String msg) {
        ToastUtil.showShortMsg(getActivity(),msg);
    }

    @Override
    public void setPresenter(HomeContract.IPresenter presenter) {
        mIPresenter  = presenter;
    }
}
