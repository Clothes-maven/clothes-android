package com.cloth.clothes.home.salelist;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cloth.clothes.R;
import com.cloth.clothes.home.HomeContract;
import com.cloth.clothes.home.homefragment.StoreFragment;
import com.cloth.clothes.home.salelist.domain.model.SaleBean;
import com.cloth.clothes.utils.StringUtils;
import com.cloth.kernel.base.BaseFragment;
import com.cloth.kernel.base.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class SaleListFragment extends BaseFragment implements HomeContract.ISaleView {

    public static SaleListFragment newInstance() {
        return new SaleListFragment();
    }


    private EditText mYear, mMonth, mDay, mUserName;
    private TextView mFindTv;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefreshLayout;
    private SaleListFragmentAdapter mAdapter;
    private HomeContract.IPresenter mIPresenter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sale_list;
    }

    @Override
    protected void init(View view) {
        mRecyclerView = view.findViewById(R.id.fragment_sell_list_salelist_rc);
        mYear = view.findViewById(R.id.fragment_sale_list_year_ed);
        mMonth = view.findViewById(R.id.fragment_sale_list_month_ed);
        mDay = view.findViewById(R.id.fragment_sale_list_day_ed);
        mUserName = view.findViewById(R.id.fragment_sale_list_name_ed);
        mRefreshLayout = view.findViewById(R.id.fragment_sale_list_refresh_layout);
        mFindTv = view.findViewById(R.id.fragment_sale_list_find_tv);
        mFindTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSaleList();
            }
        });

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getSaleList();
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                getSaleList();
            }
        });
        mAdapter = new SaleListFragmentAdapter(new ArrayList<SaleBean>());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        DividerItemDecoration decoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        setNowTime();
        mIPresenter.saleList(this, new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date()),null);
    }


    @Override
    public void success(List<SaleBean> list) {
        mRefreshLayout.finishRefresh(true);
        mRefreshLayout.finishLoadMore(true);
        mAdapter.setSaleBeanList(list);
    }

    @Override
    public void error(String msg) {
        mRefreshLayout.finishRefresh(false);
        mRefreshLayout.finishLoadMore(false);
        ToastUtil.showShortMsg(getActivity(), msg);
    }

    @Override
    public void setPresenter(HomeContract.IPresenter presenter) {
        mIPresenter = presenter;
    }

    private void getSaleList() {
        String yearStr = mYear.getText().toString().trim();
        if (TextUtils.isEmpty(yearStr) || !StringUtils.isInteger(yearStr)) {
            ToastUtil.showLongMsg(getActivity(), "年份用纯数字表示，例如：2018");
            return;
        }
        String monthStr = mMonth.getText().toString().trim();
        if (TextUtils.isEmpty(monthStr) || !StringUtils.isInteger(monthStr)) {
            ToastUtil.showLongMsg(getActivity(), "月份用纯数字表示，例如：09");
            return;
        }
        String dayStr = mDay.getText().toString().trim();
        if (TextUtils.isEmpty(dayStr) || !StringUtils.isInteger(dayStr)) {
            ToastUtil.showLongMsg(getActivity(), "日/天用纯数字表示，例如：02");
            return;
        }
        String namrStr = mUserName.getText().toString().trim();

        if (mIPresenter != null) {
            mIPresenter.saleList(this, yearStr + "-" + monthStr + "-" + dayStr,namrStr);
        }

    }

    private void setNowTime() {
        Calendar calendar = Calendar.getInstance();
        mYear.setText(String.valueOf(calendar.get(GregorianCalendar.YEAR)));
        mYear.setSelection(mYear.getText().length());
        mMonth.setText(String.valueOf(calendar.get(GregorianCalendar.MONTH) + 1));
        mDay.setText(String.valueOf(calendar.get(GregorianCalendar.DAY_OF_MONTH)));
    }
}
