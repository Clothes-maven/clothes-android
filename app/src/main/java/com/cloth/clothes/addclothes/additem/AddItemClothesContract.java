package com.cloth.clothes.addclothes.additem;

import com.cloth.kernel.base.mvpclean.IBasePresenter;
import com.cloth.kernel.base.mvpclean.IBaseView;

import java.util.List;

public interface AddItemClothesContract {
    interface IPresenter extends IBasePresenter {
        void addItemClothes(String color,String size,String number,String sid);
        void getStoreList(String uid);
    }

    interface IView extends IBaseView<IPresenter> {
        void setStores(List<String> stores);
        void addSuccess();
        void error(String msg);
    }
}
