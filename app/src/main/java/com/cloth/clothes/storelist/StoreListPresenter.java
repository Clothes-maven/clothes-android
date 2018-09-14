package com.cloth.clothes.storelist;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.cloth.clothes.home.domain.model.StoreBean;
import com.cloth.clothes.model.UserManager;
import com.cloth.clothes.storelist.domain.model.NumberModel;
import com.cloth.clothes.storelist.domain.usecase.HttpStoreListUseCase;
import com.cloth.kernel.base.mvpclean.UseCase;
import com.cloth.kernel.base.mvpclean.UseCaseHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

public class StoreListPresenter implements StoreListContract.IPresenter {

    private final UseCaseHandler mUseCaseHandler;
    private final HttpStoreListUseCase mHttpStoreListUseCase;
    private StoreListContract.IView mIView;
    private List<NumberModel> mNumberModels;
    private List<StoreBean> mStoreBeans;

    public StoreListPresenter(@NonNull UseCaseHandler useCaseHandler, @NonNull StoreListContract.IView mIView, @NonNull HttpStoreListUseCase httpStoreListUseCase) {
        mUseCaseHandler = useCaseHandler;
        mHttpStoreListUseCase = httpStoreListUseCase;
        this.mIView = mIView;
    }

    @Override
    public void getNumberList(final String color, final String size, final String sid) {
        mUseCaseHandler.execute(mHttpStoreListUseCase, new HttpStoreListUseCase.RequestValue(UserManager.getInstance().getUser().address.id, sid), new UseCase.UseCaseCallback<HttpStoreListUseCase.ResponseValue>() {
            @Override
            public void onSuccess(HttpStoreListUseCase.ResponseValue response) {
                if (mIView == null) return;
                mNumberModels = response.clothdetails;
                List<NumberModel> list = new ArrayList<>();
                for (NumberModel numberModel : mNumberModels) {
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
    public void findList(String color, String size, @NonNull String store) {
        if (mStoreBeans != null && mStoreBeans.size() > 0) {
            for (StoreBean storeBean : mStoreBeans) {
                if (store.equals(storeBean.name)) {
                    getNumberList(color, size, storeBean.id);
                    break;
                }
            }
        }
    }

    @Override
    public List<String> getColors() {
        if (mNumberModels == null || mNumberModels.size() == 0) {
            return null;
        }
        List<String> list = new ArrayList<>();
        for (NumberModel numberModel : mNumberModels) {
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
        for (NumberModel numberModel : mNumberModels) {
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
        for (NumberModel numberModel : mNumberModels) {
            if (numberModel.store == null) continue;
            set.add(numberModel.store);
            setName.add(numberModel.store.name);
        }
        mStoreBeans = new ArrayList<>(set);
        return new ArrayList<>(setName);
    }

    public List removeDuplicate(List<StoreBean> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).equals(list.get(i))) {
                    list.remove(j);
                }
            }
        }
        return list;
    }


    /**
     * check 此bean是否符合条件
     */
    private boolean check(@NonNull NumberModel numberModel, @Nullable String color, @Nullable String size, @Nullable String sid) {
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
