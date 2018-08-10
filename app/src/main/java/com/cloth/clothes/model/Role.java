package com.cloth.clothes.model;

import android.support.annotation.LongDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Role {
    public static final long OWNER = 3;   //老板
    public static final long PURCHASE = 2; //采购员
    public static final long EMPLOYEE = 1; //店员

    @LongDef({OWNER, PURCHASE, EMPLOYEE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ROLE {
    }

    public static boolean isOwner(@ROLE long role) {
        return (role & OWNER) == OWNER;
    }

    public static boolean isPurchase(@ROLE long role) {
        return (role & PURCHASE) == PURCHASE;
    }

    public static boolean isEmployee(@ROLE long role) {
        return (role & EMPLOYEE) == EMPLOYEE;
    }

}
