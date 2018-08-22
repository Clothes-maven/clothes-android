package com.cloth.clothes.saledetail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.cloth.clothes.R;
import com.cloth.clothes.home.domain.model.ClothesBean;
import com.cloth.clothes.home.salelist.domain.model.SaleBean;
import com.cloth.kernel.service.DialogWrapper;

import java.util.ArrayList;
import java.util.List;

import static com.cloth.clothes.utils.StringUtils.isDouble;
import static com.cloth.clothes.utils.StringUtils.isInteger;

public class SaleDetailClothesAdapter extends RecyclerView.Adapter<SaleDetailClothesAdapter.ViewHolder> {


    private List<Item> mItemList;

    public SaleDetailClothesAdapter(Context context, SaleBean clothesBean){
        mItemList = new ArrayList<>();
        initList(context, clothesBean,false);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_clothes_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Item item = mItemList.get(position);
        holder.desc.setText(item.desc);
        holder.value.setText(item.value);
        holder.value.setEnabled(item.enabled);
        if (item.enabled) {
            holder.value.setSelection(holder.value.getText().length());
        }
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    private void initList(Context context, SaleBean saleBean,boolean enabled) {
        ClothesBean clothesBean = saleBean.clothes;
        mItemList.clear();
        mItemList.add(new Item(context.getResources().getString(R.string.clothes_name), clothesBean.name,enabled));
        mItemList.add(new Item(context.getResources().getString(R.string.clothes_feature), clothesBean.feature,enabled));
        mItemList.add(new Item(context.getResources().getString(R.string.clothes_brand), clothesBean.brand,enabled));
        mItemList.add(new Item(context.getResources().getString(R.string.clothes_type), clothesBean.type,enabled));
        mItemList.add(new Item(context.getResources().getString(R.string.clothes_size), String.valueOf(clothesBean.size),enabled));
        mItemList.add(new Item(context.getResources().getString(R.string.clothes_texture), clothesBean.texture,enabled));
        mItemList.add(new Item(context.getResources().getString(R.string.clothes_collar), clothesBean.collar,enabled));
        mItemList.add(new Item(context.getResources().getString(R.string.clothes_sleeve), clothesBean.sleeve,enabled));
        mItemList.add(new Item(context.getResources().getString(R.string.clothes_batch), clothesBean.batch,enabled));
        mItemList.add(new Item(context.getResources().getString(R.string.clothes_cost), String.valueOf(clothesBean.cost),enabled));
        mItemList.add(new Item(context.getResources().getString(R.string.clothes_profit), String.valueOf(clothesBean.profit),enabled));
        mItemList.add(new Item(context.getResources().getString(R.string.clothes_number), String.valueOf(clothesBean.number),enabled));
        mItemList.add(new Item(context.getResources().getString(R.string.clothes_sale_name), String.valueOf(saleBean.user.name),enabled));
        mItemList.add(new Item(context.getResources().getString(R.string.clothes_sale_time), String.valueOf(saleBean.createDate),enabled));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView desc;
        EditText value;

        public ViewHolder(View itemView) {
            super(itemView);
            desc = itemView.findViewById(R.id.recyclerview_clothes_item_desc);
            value = itemView.findViewById(R.id.recyclerview_clothes_item_value);
        }
    }


    class Item {
        String desc;
        String value;
        boolean enabled;

        public Item(String desc, String value, boolean enabled) {
            this.desc = desc;
            this.value = value;
            this.enabled = enabled;
        }
    }
}
