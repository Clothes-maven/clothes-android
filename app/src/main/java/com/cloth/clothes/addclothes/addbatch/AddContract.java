package com.cloth.clothes.addclothes.addbatch;

import com.cloth.clothes.home.domain.model.ClothesBean;
import com.cloth.kernel.base.mvpclean.IBasePresenter;
import com.cloth.kernel.base.mvpclean.IBaseView;

public interface AddContract {

    interface IView extends IBaseView<IPresenter> {
        void success();
        void error(String msg);
    }

    interface IPresenter extends IBasePresenter {
        void addClothes(ClothesBean clothesBean);
    }
}
