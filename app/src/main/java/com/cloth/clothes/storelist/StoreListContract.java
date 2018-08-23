package com.cloth.clothes.storelist;

import com.cloth.clothes.storelist.domain.model.NumberModel;
import com.cloth.kernel.base.mvpclean.IBasePresenter;
import com.cloth.kernel.base.mvpclean.IBaseView;

import java.util.List;

public interface StoreListContract {
    interface IView extends IBaseView<IPresenter>{
        void success(List<NumberModel> list);
        void error(String msg);
    }
    interface IPresenter extends IBasePresenter {
        void getNumberList(String color,String size,String store,boolean is);
    }
}