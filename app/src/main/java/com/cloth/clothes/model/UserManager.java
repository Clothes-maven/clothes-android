package com.cloth.clothes.model;

import com.alibaba.fastjson.JSON;
import com.cloth.kernel.base.mvpclean.IDataRepository;
import com.cloth.kernel.base.utils.SharedUtil;

import java.io.Serializable;

public class UserManager implements Serializable{

    private final static String USER_INFO = "user_info";

    private volatile static UserManager sUserManager;
    private User mUser;


    public static UserManager getInstance() {
        if (sUserManager ==null) {
            synchronized (UserManager.class) {
                if (sUserManager ==null) {
                    sUserManager = new UserManager();
                }
            }
        }
        return sUserManager;
    }

    public long getId() {
        if (mUser ==null) {
           mUser =  SharedUtil.getObject(USER_INFO,User.class);
        }
        if (mUser !=null) {
            return mUser.id;
        }
        return 0;
    }

    public long getRole() {
        if (mUser ==null) {
            mUser =  SharedUtil.getObject(USER_INFO,User.class);
        }
        if (mUser !=null) {
            return mUser.role;
        }
        return 0;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user, IDataRepository dataRepository) {
        mUser = user;
        dataRepository.saveLocalData(USER_INFO, JSON.toJSONString(user));
    }

    public static class User implements Serializable{
        public long role;
        public String name;
        public String sex;
        public String address;
        public String phone;
        public String qq;
        public long id;
    }
}
