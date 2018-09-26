package com.cloth.clothes.clothessecondlist.domain.model;

import com.cloth.clothes.home.domain.model.ClothesBean;
import com.cloth.clothes.home.domain.model.StoreBean;

import java.io.Serializable;

//item 的model
public class ClothesSecondModel implements Serializable{
    public String  id;
    public String size;
    public String color;
    public long number;
    public ClothesBean clothe;
    public StoreBean store;
    public String isStopSell;//isStopSell: 是否停用，传0为停用 1为启用，默认启用

    public ClothesSecondModel() {
        clothe = new ClothesBean();
        store = new StoreBean();
    }
}
