package com.cloth.clothes.home.salelist.domain.model;

import com.cloth.clothes.home.domain.model.ClothesBean;
import com.cloth.clothes.model.UserManager;

import java.io.Serializable;

public class SaleBean implements Serializable{
    public long subId;
    public ClothesBean clothes;
    public double sell;
    public UserManager.User user;
    public String createDate;
}
