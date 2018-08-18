package com.cloth.clothes.detail;

import com.cloth.clothes.home.domain.model.ClothesBean;
import com.cloth.kernel.base.mvpclean.IBasePresenter;
import com.cloth.kernel.base.mvpclean.IBaseView;

public interface DetailContranct {
    interface IView extends IBaseView<IPresenter>{
        void success();
        void error(String msg);
    }
    interface IPresenter extends IBasePresenter {
        /**
         * 出售商品
         * @param clothesBean 商品
         * @param userName 用户名
         */
        void sellClothes(ClothesBean clothesBean,String userName);

        /**
         * 修改商品被容
         * @param clothesBean 商品
         */
        void fixClothes(ClothesBean clothesBean);
    }
}
