package com.cloth.clothes.home;

import com.cloth.clothes.home.domain.model.ClothesBean;
import com.cloth.kernel.base.mvpclean.IBasePresenter;
import com.cloth.kernel.base.mvpclean.IBaseView;

import java.util.List;

public interface HomeContract {
    interface IView extends IBaseView<IPresenter> {
        void refresh(List<ClothesBean> clothBeans,boolean isSuccess);
    }

    interface IPresenter extends IBasePresenter {
        void getClothes();
    }
}
