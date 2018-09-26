package com.cloth.clothes.home.salelist.domain.model;

import com.cloth.clothes.model.UserManager;
import com.cloth.clothes.clothessecondlist.domain.model.ClothesSecondModel;

import java.io.Serializable;

public class SaleBean implements Serializable{
    public long subId;
    public ClothesSecondModel clothdetail;
    public double sell;
    public UserManager.User user;
    public String createDate;
}
