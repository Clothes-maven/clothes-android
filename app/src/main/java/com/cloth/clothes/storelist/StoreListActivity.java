package com.cloth.clothes.storelist;

import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.cloth.clothes.R;
import com.cloth.clothes.home.domain.model.ClothesBean;
import com.cloth.clothes.model.BaseHttpRepository;
import com.cloth.clothes.storelist.domain.model.NumberModel;
import com.cloth.clothes.storelist.domain.usecase.HttpStoreListUseCase;
import com.cloth.clothes.utils.StringUtils;
import com.cloth.kernel.base.BaseActivity;
import com.cloth.kernel.base.mvpclean.UseCaseHandler;
import com.cloth.kernel.service.DialogWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class StoreListActivity extends BaseActivity implements StoreListContract.IView{


    @BindView(R.id.activity_store_list_color_ed)
    EditText mColorEt;
    @BindView(R.id.activity_store_list_size_ed)
    EditText mSizeEt;
    @BindView(R.id.activity_store_list_store_ed)
    EditText mStoreEt;
    @BindView(R.id.activity_store_list_rcv)
    RecyclerView mRecyclerView;

    private StoreListPresenter mPresenter;
    private StoreListAdapter mAdapter;

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
        mPresenter = new StoreListPresenter(UseCaseHandler.getInstance(),new HttpStoreListUseCase(BaseHttpRepository.getBaseHttpRepository()));
        mPresenter.getNumberList("","","",true);
        mAdapter = new StoreListAdapter(new ArrayList<NumberModel>());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        DividerItemDecoration decoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.activity_store_list_find_tv)
    public void find() {
        String color = mColorEt.getText().toString().trim();
        String size = mSizeEt.getText().toString().trim();
        String store = mStoreEt.getText().toString().trim();

        if (!StringUtils.isDouble(size)) {
            DialogWrapper.tipWarning(this,"尺码为纯数字");
            return;
        }
        mPresenter.getNumberList(color,size,store,true);
    }

    @Override
    public void success(List<NumberModel> list) {

    }

    @Override
    public void error(String msg) {

    }
}
