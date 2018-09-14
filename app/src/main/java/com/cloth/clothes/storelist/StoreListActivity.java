package com.cloth.clothes.storelist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cloth.clothes.R;
import com.cloth.clothes.addclothes.additem.AddItemClothesActivity;
import com.cloth.clothes.model.BaseHttpRepository;
import com.cloth.clothes.model.UserManager;
import com.cloth.clothes.storelist.domain.model.NumberModel;
import com.cloth.clothes.storelist.domain.usecase.HttpStoreListUseCase;
import com.cloth.clothes.storelist.weiget.SingleChoiceDialogFragment;
import com.cloth.kernel.base.BaseActivity;
import com.cloth.kernel.base.mvpclean.UseCaseHandler;
import com.cloth.kernel.base.utils.ToastUtil;
import com.cloth.kernel.service.LcRouterWrapper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


@Route(path = StoreListActivity.PATH)
public class StoreListActivity extends BaseActivity implements StoreListContract.IView {

    public static final String PATH = "/main/storedetaillist";
    public static final String SID = "store_sid";

    public static void jump(String sid) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(SID, sid);
        LcRouterWrapper.getLcRouterWrapper().jumpActWithBundle(PATH, bundle);
    }

    @BindView(R.id.activity_store_list_refresh_layout_rf)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.activity_store_list_rcv)
    RecyclerView mRecyclerView;
    @BindView(R.id.activity_store_list_size_tv)
    TextView mSizeTv;
    @BindView(R.id.activity_store_list_color_tv)
    TextView mColorTv;
    @BindView(R.id.activity_store_list_store_tv)
    TextView mStoreTv;

    private StoreListPresenter mPresenter;
    private StoreListAdapter mAdapter;
    private String mSid;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_store_list;
    }


    @Nullable
    @Override
    protected String getTitleStr() {
        return "商品列表";
    }

    @Override
    protected void init() {
        Bundle bundle;
        if ((bundle = getIntent().getExtras()) != null) {
            mSid = bundle.getString(SID);
        }
        if (UserManager.getInstance().isOwner()) {
            switchRightTv(View.VISIBLE,"添加商品");
        }
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                select();
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                select();
            }
        });
        mPresenter = new StoreListPresenter(UseCaseHandler.getInstance(), this, new HttpStoreListUseCase(BaseHttpRepository.getBaseHttpRepository()));
        mPresenter.getNumberList(null, null, mSid);
        mAdapter = new StoreListAdapter(new ArrayList<NumberModel>());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.activity_store_list_size_tv)
    public void size() {
        final List<String> sizes = mPresenter.getSizes();
        sizes.add(0, "全部");
        SingleChoiceDialogFragment single = new SingleChoiceDialogFragment();
        Listener listener = new Listener(mSizeTv, sizes);
        single.show("尺码", sizes, listener, listener, getSupportFragmentManager());
    }

    @OnClick(R.id.activity_store_list_color_tv)
    public void color() {
        final List<String> colors = mPresenter.getColors();
        colors.add(0, "全部");
        SingleChoiceDialogFragment single = new SingleChoiceDialogFragment();
        Listener listener = new Listener(mColorTv, colors);
        single.show("颜色", colors, listener, listener, getSupportFragmentManager());
    }

    @OnClick(R.id.activity_store_list_store_tv)
    public void store() {
        final List<String> stores = mPresenter.getStores();
        stores.add(0, "全部");
        SingleChoiceDialogFragment single = new SingleChoiceDialogFragment();
        Listener listener = new Listener(mStoreTv, stores);
        single.show("门店", stores, listener, listener, getSupportFragmentManager());
    }

    @Override
    protected void onResume() {
        super.onResume();
        select();
    }

    @OnClick(R.id.activity_store_list_find_tv)
    public void select() {
        String name = mStoreTv.getText().toString().trim();
        String color = mColorTv.getText().toString().trim();
        String size = mSizeTv.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            mPresenter.getNumberList(color, size, mSid);
        } else {
            mPresenter.findList(color,size , name);
        }
    }

    @Override
    public void success(List<NumberModel> list) {
        mAdapter.refresh(list);
        mRefreshLayout.finishRefresh(true);
        mRefreshLayout.finishLoadMore(true);
    }

    @Override
    public void error(String msg) {
        ToastUtil.showLongMsg(this, msg);
        mRefreshLayout.finishRefresh(false);
        mRefreshLayout.finishLoadMore(false);
    }

    @Override
    public void onRightClick(View view) {
        super.onRightClick(view);
        AddItemClothesActivity.jump();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDetach();
    }

    class Listener implements DialogInterface.OnClickListener {
        private TextView mTextView;
        private List<String> data;

        public Listener(TextView textView, List<String> data) {
            mTextView = textView;
            this.data = data;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which >=0) {
                mTextView.setText(data.get(which));
            }
        }
    }

}
