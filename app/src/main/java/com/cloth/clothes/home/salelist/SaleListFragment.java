package com.cloth.clothes.home.salelist;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cloth.clothes.R;
import com.cloth.clothes.home.HomeContract;
import com.cloth.clothes.home.salelist.domain.model.SaleBean;
import com.cloth.clothes.utils.StringUtils;
import com.cloth.kernel.base.BaseFragment;
import com.cloth.kernel.base.utils.ToastUtil;

import java.text.SimpleDateFormat;
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
        mFindTv = view.findViewById(R.id.fragment_sale_list_find_tv);
        mFindTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSaleList();
            }
        });
        mAdapter = new SaleListFragmentAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        setNowTime();
        mIPresenter.saleList(this, new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date()));
    }


    @Override
    public void success(List<SaleBean> list) {
        mAdapter.setSaleBeanList(list);
    }

    @Override
    public void error(String msg) {
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
        if (mIPresenter != null) {
            mIPresenter.saleList(this, yearStr + "-" + monthStr + "-" + dayStr);
        }

    }

    private void setNowTime() {
        Calendar calendar = Calendar.getInstance();
        mYear.setText(String.valueOf(calendar.get(GregorianCalendar.YEAR)));
        mMonth.setText(String.valueOf(calendar.get(GregorianCalendar.MONTH) + 1));
        mDay.setText(String.valueOf(calendar.get(GregorianCalendar.DAY_OF_MONTH)));
    }
}
