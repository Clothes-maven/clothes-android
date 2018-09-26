package com.cloth.clothes.clothessecondlist;

import com.cloth.clothes.clothessecondlist.domain.model.ClothesSecondModel;
import com.cloth.kernel.base.mvpclean.IBasePresenter;
import com.cloth.kernel.base.mvpclean.IBaseView;

import java.util.List;

public interface ClothesSecondContract {
    interface IView extends IBaseView<IPresenter>{
        void success(List<ClothesSecondModel> list);
        void error(String msg);
        void stateOk(String msg);
    }
    interface IPresenter extends IBasePresenter {
        void getNumberList(String color ,String size,String sid,String cid);
        void findList(String color ,String size,String store,String cid);
        void switchState(String cid,String isStopSell,int postion);
        void deleteItem(String cid,int position);
        List<String> getColors();
        List<String> getSizes();
        List<String> getStores();
    }
}
