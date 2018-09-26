package com.cloth.clothes.clothesdetail;

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
import com.cloth.clothes.clothessecondlist.domain.model.ClothesSecondModel;
import com.cloth.clothes.model.UserManager;
import com.cloth.kernel.service.DialogWrapper;

import java.util.ArrayList;
import java.util.List;

import static com.cloth.clothes.utils.StringUtils.isDouble;
import static com.cloth.clothes.utils.StringUtils.isInteger;

public class DetailClothesAdapter extends RecyclerView.Adapter<DetailClothesAdapter.ViewHolder> {


    private List<Item> mItemList;
    private Context mContext;
    private ClothesSecondModel mNumberModel;
    private ClothesBean mClothesBean;
    private boolean enabled ;

    public DetailClothesAdapter(Context context, ClothesSecondModel numberModel, boolean enabled ){
        this.mContext  = context;
        mItemList = new ArrayList<>();
        this.mNumberModel = numberModel;
        this.mClothesBean = numberModel.clothe;
        if (!UserManager.getInstance().isOwner()) {
            mClothesBean.cost = 0;
        }
        this.enabled = enabled;
        initList(context, numberModel,enabled);
    }

    public void refresh(ClothesSecondModel numberModel) {
       initList(mContext,numberModel,enabled);
       notifyDataSetChanged();
    }

    public ClothesBean getClothesBean() {
        boolean isHttp = true;
        for (int i = 0; i < mItemList.size(); i++) {
           isHttp = setValue(mItemList.get(i),i);
        }
        if (!isHttp) {
            return null;
        }
        return mClothesBean;
    }

    public void setEnable(boolean enable) {
        this.enabled = enable;
        for (Item item : mItemList) {
            item.enabled = enable;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_clothes, parent, false);
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
        holder.value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String trim = holder.value.getText().toString().trim();
                mItemList.get(holder.getAdapterPosition()).value = trim;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    private void initList(Context context, ClothesSecondModel numberModel, boolean enabled) {
        ClothesBean clothesBean  = numberModel.clothe;
        mItemList.clear();
        mItemList.add(new Item(context.getResources().getString(R.string.clothes_name), clothesBean.name,enabled));
        mItemList.add(new Item(context.getResources().getString(R.string.clothes_feature), clothesBean.feature,enabled));
        mItemList.add(new Item(context.getResources().getString(R.string.clothes_brand), clothesBean.brand,enabled));
        mItemList.add(new Item(context.getResources().getString(R.string.clothes_type), clothesBean.type,enabled));
        mItemList.add(new Item(context.getResources().getString(R.string.clothes_size), String.valueOf(numberModel.size),enabled));
        mItemList.add(new Item(context.getResources().getString(R.string.clothes_color), String.valueOf(numberModel.color),enabled));
        mItemList.add(new Item(context.getResources().getString(R.string.clothes_texture), clothesBean.texture,enabled));
        mItemList.add(new Item(context.getResources().getString(R.string.clothes_collar), clothesBean.couar,enabled));
        mItemList.add(new Item(context.getResources().getString(R.string.clothes_sleeve), clothesBean.sleeve,enabled));
        mItemList.add(new Item(context.getResources().getString(R.string.clothes_batch), clothesBean.batch,enabled));
        mItemList.add(new Item(context.getResources().getString(R.string.clothes_store), numberModel.store.name,enabled));
        mItemList.add(new Item(context.getResources().getString(R.string.clothes_cost), String.valueOf(clothesBean.cost),enabled));
        mItemList.add(new Item(context.getResources().getString(R.string.clothes_number), String.valueOf(numberModel.number),enabled));
    }

    private boolean setValue(Item item,int postion) {
        switch (postion) {
            case 0:
                mClothesBean.name = item.value;
                break;
            case 1:
                mClothesBean.feature = item.value;
                break;
            case 2:
                mClothesBean.brand = item.value;
                break;
            case 3:
                mClothesBean.type = item.value;
                break;
            case 4:
                if (isDouble(item.value)) {
                    mNumberModel.size = item.value;
                } else {
                    DialogWrapper.tipWarning(mContext,"尺码需要填写数字");
                    return false;
                }
                break;
            case 5:
                mNumberModel.color = item.value;
                break;
            case 6:
                mClothesBean.texture = item.value;
                break;
            case 7:
                mClothesBean.couar = item.value;
                break;
            case 8:
                mClothesBean.sleeve = item.value;
                break;
            case 9:
                mClothesBean.batch = item.value;
                break;
            case 10:
                mNumberModel.store.name = item.value;
                break;
            case 11:
                if (isDouble(item.value)) {
                    mClothesBean.cost = Double.valueOf(item.value);
                } else {
                    DialogWrapper.tipWarning(mContext,"原价需要填写数字");
                    return false;
                }
                break;
//            case 12:
//                if (isDouble(item.value)) {
//                    mClothesBean.profit = Double.valueOf(item.value);
//                } else {
//                    DialogWrapper.tipWarning(mContext,"利润需要填写数字");
//                    return false;
//                }
//                break;
            case 12:
                if (isInteger(item.value)) {
                    mNumberModel.number = Long.parseLong(item.value);
                } else {
                    DialogWrapper.tipWarning(mContext,"剩余数量需要填写数字");
                    return false;
                }
                break;
        }
        return true;
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
