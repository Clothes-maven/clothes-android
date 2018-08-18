package com.cloth.clothes.addclothes;

import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cloth.clothes.R;
import com.cloth.clothes.detail.DetailClothesAdapter;
import com.cloth.clothes.home.domain.model.ClothesBean;
import com.cloth.kernel.base.BaseActivity;
import com.cloth.kernel.base.utils.ToastUtil;
import com.cloth.kernel.service.LcRouterWrapper;

import butterknife.BindView;

@Route(path = AddClothesActivity.PATH)
public class AddClothesActivity extends BaseActivity {
    public static final String PATH = "/main/addclothes";

    public static void jump() {
        LcRouterWrapper.getLcRouterWrapper().jumpActWithPath(PATH);
    }

    @BindView(R.id.activity_add_clothes_recyl)
    RecyclerView mRecyclerView;
    private DetailClothesAdapter mClothesAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_clothes;
    }

    @Nullable
    @Override
    protected String getTitleStr() {
        return "添加批次";
    }

    @Override
    protected void init() {
        switchRightTv(View.VISIBLE,"添加");
        mClothesAdapter = new DetailClothesAdapter(this,new ClothesBean());
        mClothesAdapter.setEnable(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        DividerItemDecoration decoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mClothesAdapter);
    }

    @Override
    public void onRightClick(View view) {
        ToastUtil.showShortMsg(this,"tianjia");
    }
}
