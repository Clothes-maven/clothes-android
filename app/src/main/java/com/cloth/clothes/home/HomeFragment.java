package com.cloth.clothes.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloth.clothes.R;
import com.cloth.clothes.home.domain.model.ClothesBean;
import com.cloth.clothes.home.domain.usecase.HttpGetClothesUseCase;
import com.cloth.clothes.model.BaseHttpRepository;
import com.cloth.clothes.model.LcBaseDataRepository;
import com.cloth.kernel.base.BaseFrament;
import com.cloth.kernel.base.mvpclean.UseCaseHandler;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;

public class HomeFragment extends BaseFrament implements HomeContract.IView{


    public HomeFragment() {
    }

    private RecyclerView mRecyclerView;
    private RefreshLayout mRefreshLayout;
    private HomeAdapter mHomeAdapter;
    private HomeContract.IPresenter mIPresenter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home,container,false);
        mRecyclerView = root.findViewById(R.id.fragment_home_rv);
        mRefreshLayout = root.findViewById(R.id.refreshLayout);
        init();
        return root;
    }


    private void init() {
        mIPresenter = new HomeFrgPresenter(this,UseCaseHandler.getInstance(),new HttpGetClothesUseCase(BaseHttpRepository.getBaseHttpRepository(), LcBaseDataRepository.getLcBaseRepository()));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        DividerItemDecoration decoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mIPresenter.getClothes();
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                mIPresenter.getClothes();
            }
        });
        mIPresenter.getClothes();
    }

    @Override
    public void refresh(List<ClothesBean> clothBeans, boolean isSuccess) {
        mRefreshLayout.finishRefresh(isSuccess);
        mRefreshLayout.finishLoadMore(isSuccess);
//        if (!isSuccess)
//            return;
        if (mHomeAdapter ==null) {
            mHomeAdapter = new HomeAdapter(clothBeans);
            mRecyclerView.setAdapter(mHomeAdapter);
        } else {
            mHomeAdapter.setClothBeanList(clothBeans);
        }
        mHomeAdapter.notifyDataSetChanged();
    }

}
