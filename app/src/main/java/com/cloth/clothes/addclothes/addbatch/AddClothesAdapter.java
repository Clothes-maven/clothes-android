package com.cloth.clothes.addclothes.addbatch;

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
import com.cloth.kernel.service.DialogWrapper;

import java.util.ArrayList;
import java.util.List;

import static com.cloth.clothes.utils.StringUtils.isDouble;

/**
 * 添加clothes
 */
public class AddClothesAdapter extends RecyclerView.Adapter<AddClothesAdapter.ViewHolder> {


    private List<Item> mItemList;
    private Context mContext;
    private ClothesBean mClothesBean;

    public AddClothesAdapter(Context context, ClothesBean clothesBean){
        this.mContext  = context;
        mItemList = new ArrayList<>();
        this.mClothesBean = clothesBean;
        initList(context, clothesBean);
    }

    public void refresh(ClothesBean clothesBean) {
       initList(mContext,clothesBean);
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
        holder.value.setEnabled(true);
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

    private void initList(Context context, ClothesBean clothesBean) {
        mItemList.clear();
        mItemList.add(new Item(context.getResources().getString(R.string.clothes_name), clothesBean.name));
        mItemList.add(new Item(context.getResources().getString(R.string.clothes_feature), clothesBean.feature));
        mItemList.add(new Item(context.getResources().getString(R.string.clothes_brand), clothesBean.brand));
        mItemList.add(new Item(context.getResources().getString(R.string.clothes_type), clothesBean.type));
        mItemList.add(new Item(context.getResources().getString(R.string.clothes_texture), clothesBean.texture));
        mItemList.add(new Item(context.getResources().getString(R.string.clothes_collar), clothesBean.couar));
        mItemList.add(new Item(context.getResources().getString(R.string.clothes_sleeve), clothesBean.sleeve));
        mItemList.add(new Item(context.getResources().getString(R.string.clothes_batch), clothesBean.batch));
        mItemList.add(new Item(context.getResources().getString(R.string.clothes_cost), String.valueOf(clothesBean.cost)));
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
                mClothesBean.texture = item.value;
                break;
            case 5:
                mClothesBean.couar = item.value;
                break;
            case 6:
                mClothesBean.sleeve = item.value;
                break;
            case 7:
                mClothesBean.batch = item.value;
                break;
            case 8:
            default:
                if (isDouble(item.value)) {
                    mClothesBean.cost = Double.valueOf(item.value);
                } else {
                    DialogWrapper.tipWarning(mContext,"原价需要填写数字");
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

        public Item(String desc, String value) {
            this.desc = desc;
            this.value = value;
        }
    }
}
