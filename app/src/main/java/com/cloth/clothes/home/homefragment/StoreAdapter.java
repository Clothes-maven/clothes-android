package com.cloth.clothes.home.homefragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloth.clothes.R;
import com.cloth.clothes.detail.DetailActivity;
import com.cloth.clothes.home.domain.model.ClothesBean;

import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder>{


    private List<ClothesBean> mClothBeanList ;
    public StoreAdapter(@NonNull List<ClothesBean> beanList) {
        mClothBeanList = beanList;
    }

    public void setClothBeanList(@NonNull List<ClothesBean> clothBeanList) {
        mClothBeanList = clothBeanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_adapter_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.desc.setText(mClothBeanList.get(position).name);
        holder.size.setText(String.valueOf(mClothBeanList.get(position).size));
        holder.batch.setText(mClothBeanList.get(position).batch);
        holder.number.setText(String.valueOf(mClothBeanList.get(position).number));
//        holder.clothesImg
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivity.jump(mClothBeanList.get(holder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mClothBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView desc;
        TextView size;
        TextView batch;
        TextView number;
        ImageView clothesImg;
        ViewHolder(View itemView) {
            super(itemView);
            clothesImg = itemView.findViewById(R.id.home_fragment_adapter_item_cloth_img);
            desc = itemView.findViewById(R.id.home_fragment_adapter_item_desc_tv);
            size = itemView.findViewById(R.id.home_fragment_adapter_item_size_tv);
            batch = itemView.findViewById(R.id.home_fragment_adapter_item_batch_tv);
            number = itemView.findViewById(R.id.home_fragment_adapter_item_number_tv);
        }
    }

}
