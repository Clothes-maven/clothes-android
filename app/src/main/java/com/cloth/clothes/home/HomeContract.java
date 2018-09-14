package com.cloth.clothes.home;

import com.cloth.clothes.home.domain.model.ClothesBean;
import com.cloth.clothes.home.salelist.domain.model.SaleBean;
import com.cloth.kernel.base.eventbus.IOKEvent;
import com.cloth.kernel.base.mvpclean.IBasePresenter;
import com.cloth.kernel.base.mvpclean.IBaseView;

import java.util.List;

public interface HomeContract {
    interface IStoreViewFrg extends IBaseView<IPresenter> ,IOKEvent{
        void refresh(List<ClothesBean> clothBeans,boolean isSuccess);
        void setPresenter(IPresenter presenter);
        void refreshItem(int position,ClothesBean clothesBean);
    }

    interface IPresenter extends IBasePresenter {
        void getClothes(HomeContract.IStoreViewFrg iStoreView);
        void saleList(HomeContract.ISaleViewFrg iSaleView,String time,String name);
    }

    interface ISaleViewFrg extends IBaseView<IPresenter>{
        void success(List<SaleBean> list);
        void error(String msg);
        void setPresenter(IPresenter presenter);
    }
}
