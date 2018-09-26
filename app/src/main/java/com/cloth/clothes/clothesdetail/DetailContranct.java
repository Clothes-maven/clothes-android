package com.cloth.clothes.clothesdetail;

import com.cloth.clothes.home.domain.model.ClothesBean;
import com.cloth.kernel.base.mvpclean.IBasePresenter;
import com.cloth.kernel.base.mvpclean.IBaseView;

public interface DetailContranct {
    interface IView extends IBaseView<IPresenter>{
        void sellSuccess();
        void fixSuccess();
        void error(String msg);
    }
    interface IPresenter extends IBasePresenter {
        /**
         * 出售商品
         * @param userId 用户名
         */
        void sellClothes(String cid ,long userId,String  sell,String number);

        /**
         * 修改商品被容
         * @param clothesBean 商品
         */
        void fixClothes(ClothesBean clothesBean);
    }
}
