package com.cloth.clothes.clothessecondlist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.cloth.clothes.clothessecondlist.domain.model.ClothesSecondModel;
import com.cloth.clothes.clothessecondlist.domain.usecase.HttpDeleteItemUseCase;
import com.cloth.clothes.clothessecondlist.domain.usecase.HttpStoreListUseCase;
import com.cloth.clothes.clothessecondlist.domain.usecase.HttpSwitchStateUseCase;
import com.cloth.clothes.clothessecondlist.widget.SingleChoiceDialogFragment;
import com.cloth.kernel.base.BaseActivity;
import com.cloth.kernel.base.mvpclean.UseCaseHandler;
import com.cloth.kernel.base.utils.ToastUtil;
import com.cloth.kernel.service.LcRouterWrapper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


@Route(path = ClothesSecondActivity.PATH)
public class ClothesSecondActivity extends BaseActivity implements ClothesSecondContract.IView {

    public static final String PATH = "/main/storedetaillist";
    public static final String SID = "store_sid";
    public static final String CLOTHES_ID = "clothes_id";


    public static void jump(String sid, String clothesId) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(SID, sid);
        bundle.putSerializable(CLOTHES_ID, clothesId);
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

    private static final String ALL ="全部";
    private ClothesSecondPresenter mPresenter;
    private ClothesSecondAdapter mAdapter;
    private String mSid, mClothesId;
    private Listener listenerStore ;
    private Listener listenerSize ;
    private Listener listenerColor ;


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
            mClothesId = bundle.getString(CLOTHES_ID);
        }
        if (UserManager.getInstance().isOwner()) {
            switchRightTv(View.VISIBLE, "添加商品");
        }
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                select();
            }
        });
        mRefreshLayout.setEnableLoadMore(false);
        mPresenter = new ClothesSecondPresenter(UseCaseHandler.getInstance(), this,
                new HttpStoreListUseCase(BaseHttpRepository.getBaseHttpRepository()),
                new HttpSwitchStateUseCase(BaseHttpRepository.getBaseHttpRepository()),
                new HttpDeleteItemUseCase(BaseHttpRepository.getBaseHttpRepository()));
        mPresenter.getNumberList(null, null, mSid,mClothesId);
        mAdapter = new ClothesSecondAdapter(new ArrayList<ClothesSecondModel>());
        mAdapter.setListener(new ClothesSecondAdapter.Listener() {
            @Override
            public void longClickListener(final String cid, final int postion) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ClothesSecondActivity.this);
                builder.setTitle("提示");
                builder.setMessage("请确认是否删除此条目");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.deleteItem(cid, postion);
                    }
                });
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {

                    }
                });
                builder.create().show();
            }

            @Override
            public void clickListener(final String cid, final String isStopSell, final int position) {
                String message;
                if (isStopSell.equals("1")) {
                    message = "请确认打开出售";
                } else {
                    message = "请确认关闭出售";
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(ClothesSecondActivity.this);
                builder.setTitle("提示");
                builder.setMessage(message);
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.switchState(cid, isStopSell, position);
                    }
                });
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {

                    }
                });
                builder.create().show();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        listenerStore = new Listener(mStoreTv);
        listenerSize = new Listener(mSizeTv);
        listenerColor = new Listener(mColorTv);
    }


    @OnClick(R.id.activity_store_list_size_tv)
    public void size() {
        final List<String> sizes = mPresenter.getSizes();
        sizes.add(0, ALL);
        SingleChoiceDialogFragment single = new SingleChoiceDialogFragment();
        listenerSize.setData(sizes);
        single.show("尺码", sizes, listenerSize.getSelected(),listenerSize, listenerSize, getSupportFragmentManager());
    }

    @OnClick(R.id.activity_store_list_color_tv)
    public void color() {
        final List<String> colors = mPresenter.getColors();
        colors.add(0, ALL);
        SingleChoiceDialogFragment single = new SingleChoiceDialogFragment();
        listenerColor.setData(colors);
        single.show("颜色", colors, listenerColor.getSelected(),listenerColor, listenerColor, getSupportFragmentManager());
    }


    @OnClick(R.id.activity_store_list_store_tv)
    public void store() {
        final List<String> stores = mPresenter.getStores();
        stores.add(0, ALL);
        SingleChoiceDialogFragment single = new SingleChoiceDialogFragment();
        listenerStore.setData(stores);
        single.show("门店", stores, listenerStore.getSelected(),listenerStore, listenerStore, getSupportFragmentManager());
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
        if (ALL.equals(color)) {
            color = null;
        }
        String size = mSizeTv.getText().toString().trim();
        if (ALL.equals(size)) {
            size = null;
        }
        if (TextUtils.isEmpty(name) || ALL.equals(name)) {
            mPresenter.getNumberList(color, size, mSid,mClothesId);
        } else {
            mPresenter.findList(color, size, name,mClothesId);
        }
    }

    @Override
    public void success(List<ClothesSecondModel> list) {
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
    public void stateOk(String msg) {
        ToastUtil.showShortMsg(this, "已关闭");
    }

    @Override
    public void onRightClick(View view) {
        super.onRightClick(view);
        AddItemClothesActivity.jump(mClothesId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDetach();
    }

    class Listener implements DialogInterface.OnClickListener {
        private TextView mTextView;
        private List<String> data;
        private int mSelected;

        public int getSelected() {
            return mSelected;
        }

        public void setData(List<String> data) {
            this.data = data;
        }

        public Listener(TextView textView) {
            mTextView = textView;

        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which >= 0) {
                mTextView.setText(data.get(which));
                mSelected = which;
            }
        }
    }

}
