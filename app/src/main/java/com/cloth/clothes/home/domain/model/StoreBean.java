package com.cloth.clothes.home.domain.model;


public class StoreBean {
    public String  id;
    public String name;
    public String address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StoreBean)) return false;

        StoreBean storeBean = (StoreBean) o;

        return id.equals(storeBean.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
