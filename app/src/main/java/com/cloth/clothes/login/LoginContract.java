package com.cloth.clothes.login;

import com.cloth.clothes.model.Role;
import com.cloth.kernel.base.mvpclean.IBasePresenter;
import com.cloth.kernel.base.mvpclean.IBaseView;

public interface LoginContract {

     interface IPresenter extends IBasePresenter{
         void login(String name,String pass);
     }

     interface IView extends IBaseView<IPresenter>{
         void jumpHomeAct(@Role.ROLE long role ,long id);
         void toastStr(String msg);
     }
}
