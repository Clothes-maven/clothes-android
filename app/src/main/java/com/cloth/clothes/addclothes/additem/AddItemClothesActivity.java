package com.cloth.clothes.addclothes.additem;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cloth.clothes.R;
import com.cloth.clothes.addclothes.additem.domian.usecase.HttpAddItemClothesUseCase;
import com.cloth.clothes.addclothes.additem.domian.usecase.HttpGetStoreListUseCase;
import com.cloth.clothes.model.BaseHttpRepository;
import com.cloth.clothes.model.UserManager;
import com.cloth.clothes.clothessecondlist.widget.SingleChoiceDialogFragment;
import com.cloth.kernel.base.BaseActivity;
import com.cloth.kernel.base.mvpclean.UseCaseHandler;
import com.cloth.kernel.base.utils.ToastUtil;
import com.cloth.kernel.service.DialogWrapper;
import com.cloth.kernel.service.LcRouterWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@Route( path = AddItemClothesActivity.PATH)
public class AddItemClothesActivity extends BaseActivity implements AddItemClothesContract.IView{
    public static final String PATH = "/main/additemclothe";
    public static final String CLOTHES_ID = "clothes_id";

    public static void jump(String clothesId) {
        Bundle bundle = new Bundle();
        bundle.putString(CLOTHES_ID, clothesId);
        LcRouterWrapper.getLcRouterWrapper().jumpActWithBundle(PATH,bundle);
    }

    @BindView(R.id.activity_add_item_clothes_color_edt)
    EditText mColorEt;
    @BindView(R.id.activity_add_item_clothes_size_edt)
    EditText mSizeEt;
    @BindView(R.id.activity_add_item_clothes_number_edt)
    EditText mNumberEt;
    @BindView(R.id.activity_add_item_clothes_select_tv)
    TextView mStoreTv;

    private AddItemClothesContract.IPresenter mIPresenter;
    private List<String> mStoreNames = new ArrayList<>();
    private String mClothesId;
    private int mSelectItem;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_item_clothes;
    }

    @Nullable
    @Override
    protected String getTitleStr() {
        return "添加条目";
    }

    @OnClick(R.id.activity_add_item_clothes_select_tv)
    public void select() {
        new SingleChoiceDialogFragment().show("选择门店", mStoreNames, mSelectItem,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mSelectItem = which;
                mStoreTv.setText(mStoreNames.get(which));
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        },getSupportFragmentManager());
    }

    @Override
    protected void init() {
        Bundle bundle;
        if ((bundle = getIntent().getExtras()) != null) {
            mClothesId = bundle.getString(CLOTHES_ID);
        }
        mIPresenter = new AddItemClothesPresenter(UseCaseHandler.getInstance(),this,
                new HttpAddItemClothesUseCase(BaseHttpRepository.getBaseHttpRepository()),
                new HttpGetStoreListUseCase(BaseHttpRepository.getBaseHttpRepository()));
        mIPresenter.getStoreList(String.valueOf(UserManager.getInstance().getId()));
        switchRightTv(View.VISIBLE,"完成");
    }

    @Override
    public void onRightClick(View view) {
        String color = mColorEt.getText().toString().trim();
        if (TextUtils.isEmpty(color)) {
            ToastUtil.showShortMsg(this,"颜色不能为空");
            return;
        }
        String size = mSizeEt.getText().toString().trim();
        if (TextUtils.isEmpty(size)) {
            ToastUtil.showShortMsg(this,"尺寸不能为空");
            return;
        }
        String number = mNumberEt.getText().toString().trim();
        if (TextUtils.isEmpty(number)) {
            ToastUtil.showShortMsg(this,"数量不能为空");
            return;
        }
        String store = mStoreTv.getText().toString().trim();
        if (TextUtils.isEmpty(store)) {
            ToastUtil.showShortMsg(this,"请选择门店");
            return;
        }

        mIPresenter.addItemClothes(color,size,number,mClothesId,store);
        DialogWrapper.waitDialog(this);
    }

    @Override
    public void setStores(List<String> stores) {
        mStoreNames.clear();
        mStoreNames.addAll(stores);
    }

    @Override
    public void addSuccess() {
//        DialogWrapper.tipSuccessDialog(this);
        DialogWrapper.dismissWaitDialog();
        ToastUtil.showShortMsg(this,"添加成功");
        finish();
    }

    @Override
    public void error(String msg) {
        ToastUtil.showShortMsg(this,msg);
        DialogWrapper.dismissWaitDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIPresenter.onDetach();
    }
}
