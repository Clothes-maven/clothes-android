package com.cloth.clothes.home.homefragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cloth.clothes.R;
import com.cloth.clothes.home.HomeContract;
import com.cloth.clothes.home.domain.model.ClothesBean;
import com.cloth.kernel.base.BaseFragment;
import com.cloth.kernel.base.utils.ToastUtil;
import com.cloth.kernel.service.LcEventBusWrapper;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

/**
 * 商品主页
 */
public class StoreFragment extends BaseFragment implements HomeContract.IStoreViewFrg {


    public StoreFragment() {
    }

    private RecyclerView mRecyclerView;
    private RefreshLayout mRefreshLayout;
    private StoreAdapter mHomeAdapter;
    private HomeContract.IPresenter mIPresenter;

    public static StoreFragment newInstance() {
        return new StoreFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LcEventBusWrapper.getInstance().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LcEventBusWrapper.getInstance().unregister(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mHomeAdapter !=null) {
            mHomeAdapter.notifyDataSetChanged();
        }
    }

    protected void init(View root) {
        mRecyclerView = root.findViewById(R.id.fragment_home_rv);
        mRefreshLayout = root.findViewById(R.id.refreshLayout);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        DividerItemDecoration decoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (mIPresenter !=null) {
                    mIPresenter.getClothes(StoreFragment.this);
                }
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                if (mIPresenter !=null) {
                    mIPresenter.getClothes(StoreFragment.this);
                }
            }
        });
        mIPresenter.getClothes(this);
    }

    @Override
    public void refresh(List<ClothesBean> clothBeans, boolean isSuccess,String msg) {
        mRefreshLayout.finishRefresh(isSuccess);
        mRefreshLayout.finishLoadMore(isSuccess);
        if (!isSuccess) {
            ToastUtil.showShortMsg(getActivity(),msg);
        }
        if (clothBeans ==null) return;
        if (mHomeAdapter ==null) {
            mHomeAdapter = new StoreAdapter(clothBeans);
            mRecyclerView.setAdapter(mHomeAdapter);
        } else {
            mHomeAdapter.refreshItem(clothBeans);
        }
        mHomeAdapter.setListener(new StoreAdapter.Listener() {
            @Override
            public void delete(ClothesBean clothesBean, int position) {
                mIPresenter.deleteClothes(StoreFragment.this, String.valueOf(clothesBean.id),position);
            }
        });
        mHomeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void setPresenter(HomeContract.IPresenter presenter) {
        mIPresenter =presenter;
    }

    @Override
    public void refreshItem(int position, ClothesBean clothesBean) {
        mHomeAdapter.refreshItem(position,clothesBean);
    }

}
