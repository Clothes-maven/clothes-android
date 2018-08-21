package com.cloth.clothes.home;

import com.cloth.clothes.home.domain.model.ClothesBean;
import com.cloth.clothes.home.salelist.domain.model.SaleBean;
import com.cloth.kernel.base.mvpclean.IBasePresenter;
import com.cloth.kernel.base.mvpclean.IBaseView;

import java.util.List;

public interface HomeContract {
    interface IStoreView extends IBaseView<IPresenter> {
        void refresh(List<ClothesBean> clothBeans,boolean isSuccess);
        void setPresenter(IPresenter presenter);
    }

    interface IPresenter extends IBasePresenter {
        void getClothes(HomeContract.IStoreView iStoreView);
        void saleList(HomeContract.ISaleView iSaleView,String time);
    }

    interface ISaleView extends IBaseView<IPresenter>{
        void success(List<SaleBean> list);
        void error(String msg);
        void setPresenter(IPresenter presenter);
    }
}
