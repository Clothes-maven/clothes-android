package com.cloth.clothes.login;

import com.cloth.clothes.model.Role;

public interface LoginContract {

     interface IPresenter {
         void login(String name,String pass);
     }

     interface IView {
         void jumpHomeAct(@Role.ROLE long role ,long id);
         void toastStr(String msg);
     }
}
