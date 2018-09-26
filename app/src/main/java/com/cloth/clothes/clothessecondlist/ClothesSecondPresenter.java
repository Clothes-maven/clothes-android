package com.cloth.clothes.clothessecondlist;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.cloth.clothes.home.domain.model.StoreBean;
import com.cloth.clothes.model.UserManager;
import com.cloth.clothes.clothessecondlist.domain.model.ClothesSecondModel;
import com.cloth.clothes.clothessecondlist.domain.usecase.HttpDeleteItemUseCase;
import com.cloth.clothes.clothessecondlist.domain.usecase.HttpStoreListUseCase;
import com.cloth.clothes.clothessecondlist.domain.usecase.HttpSwitchStateUseCase;
import com.cloth.kernel.base.mvpclean.UseCase;
import com.cloth.kernel.base.mvpclean.UseCaseHandler;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class ClothesSecondPresenter implements ClothesSecondContract.IPresenter {

    private final UseCaseHandler mUseCaseHandler;
    private final HttpStoreListUseCase mHttpStoreListUseCase;
    private final HttpDeleteItemUseCase mHttpDeleteItemUseCase;
    private final HttpSwitchStateUseCase mHttpSwitchStateUseCase;
    private ClothesSecondContract.IView mIView;
    private List<ClothesSecondModel> mNumberModels;
    private List<StoreBean> mStoreBeans;

    public ClothesSecondPresenter(@NonNull UseCaseHandler useCaseHandler, @NonNull ClothesSecondContract.IView mIView, @NonNull HttpStoreListUseCase httpStoreListUseCase ,
                                  @NonNull HttpSwitchStateUseCase switchStateUseCase ,
                                  @NonNull HttpDeleteItemUseCase httpDeleteItemUseCase) {
        mUseCaseHandler = useCaseHandler;
        mHttpStoreListUseCase = httpStoreListUseCase;
        mHttpSwitchStateUseCase = switchStateUseCase;
        mHttpDeleteItemUseCase = httpDeleteItemUseCase;
        this.mIView = mIView;
    }

    @Override
    public void getNumberList(final String color, final String size, final String sid,String cid) {
        mUseCaseHandler.execute(mHttpStoreListUseCase, new HttpStoreListUseCase.RequestValue(cid, sid), new UseCase.UseCaseCallback<HttpStoreListUseCase.ResponseValue>() {
            @Override
            public void onSuccess(HttpStoreListUseCase.ResponseValue response) {
                if (mIView == null) return;
                mNumberModels = response.clothdetails;
                List<ClothesSecondModel> list = new ArrayList<>();
                for (ClothesSecondModel numberModel : mNumberModels) {
                    if (check(numberModel, color, size, sid)) {
                        list.add(numberModel);
                    }
                }
                mIView.success(list);
            }

            @Override
            public void onError(int code, String msg) {
                if (mIView != null) {
                    mIView.error(msg);
                }
            }
        });
    }

    @Override
    public void findList(String color, String size, @NonNull String store,String cid) {

        if (mStoreBeans != null && mStoreBeans.size() > 0) {
            for (StoreBean storeBean : mStoreBeans) {
                if (store.equals(storeBean.name)) {
                    getNumberList(color, size, storeBean.id,cid);
                    break;
                }
            }
        }
    }

    @Override
    public void switchState(String cid, String isStopSell,int position) {
        mNumberModels.get(position).isStopSell = isStopSell;
        mUseCaseHandler.execute(mHttpSwitchStateUseCase, new HttpSwitchStateUseCase.RequestValue(cid, isStopSell), new UseCase.UseCaseCallback<HttpSwitchStateUseCase.ResponseValue>() {
            @Override
            public void onSuccess(HttpSwitchStateUseCase.ResponseValue response) {
                if (mIView !=null) {
                    mIView.success(mNumberModels);
                }
            }

            @Override
            public void onError(int code, String msg) {
                if (mIView !=null) {
                    mIView.error(msg);
                }
            }
        });
    }

    @Override
    public void deleteItem(String cid,final int position) {
        mUseCaseHandler.execute(mHttpDeleteItemUseCase, new HttpDeleteItemUseCase.RequestValue(cid), new UseCase.UseCaseCallback<HttpDeleteItemUseCase.ResponseValue>() {
            @Override
            public void onSuccess(HttpDeleteItemUseCase.ResponseValue response) {
                mNumberModels.remove(position);
                if (mIView!=null) {
                    mIView.success(mNumberModels);
                }
            }

            @Override
            public void onError(int code, String msg) {
                if (mIView !=null) {
                    mIView.error(msg);
                }
            }
        });
    }

    @Override
    public List<String> getColors() {
        if (mNumberModels == null || mNumberModels.size() == 0) {
            return null;
        }
        List<String> list = new ArrayList<>();
        for (ClothesSecondModel numberModel : mNumberModels) {
            String str = numberModel.color;
            if (!list.contains(str)) {
                list.add(str);
            }
        }
        return list;
    }


    @Override
    public List<String> getSizes() {
        if (mNumberModels == null || mNumberModels.size() == 0) {
            return null;
        }
        List<String> list = new ArrayList<>();
        for (ClothesSecondModel numberModel : mNumberModels) {
            String str = numberModel.size;
            if (!list.contains(str)) {
                list.add(str);
            }
        }
        return list;
    }

    @Override
    public List<String> getStores() {
        if (mNumberModels == null || mNumberModels.size() == 0) {
            return null;
        }
        LinkedHashSet<StoreBean> set = new LinkedHashSet<StoreBean>(mNumberModels.size());
        LinkedHashSet<String> setName = new LinkedHashSet<>(mNumberModels.size());
        for (ClothesSecondModel numberModel : mNumberModels) {
            if (numberModel.store == null) continue;
            set.add(numberModel.store);
            setName.add(numberModel.store.name);
        }
        mStoreBeans = new ArrayList<>(set);
        return new ArrayList<>(setName);
    }




    /**
     * check 此bean是否符合条件
     */
    private boolean check(@NonNull ClothesSecondModel numberModel, @Nullable String color, @Nullable String size, @Nullable String sid) {
        if (!TextUtils.isEmpty(color)) {
            if (!color.equals(numberModel.color)) {
                return false;
            }
        }
        if (!TextUtils.isEmpty(size)) {
            if (!size.equals(numberModel.size)) {
                return false;
            }
        }
        if (!TextUtils.isEmpty(sid)) {
            if (numberModel.store != null && !sid.equals(numberModel.store.id)) {
                return false;
            }
        }
        return true;
    }


    @Override
    public void onDetach() {
        mIView = null;
    }
}
