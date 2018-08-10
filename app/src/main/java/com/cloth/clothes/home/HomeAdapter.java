package com.cloth.clothes.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cloth.clothes.R;
import com.cloth.clothes.home.domain.model.ClothBean;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{


    private List<ClothBean> mClothBeanList ;
    public HomeAdapter(List<ClothBean> beanList) {
        mClothBeanList = beanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_fragment_adapter_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mTextView.setText(mClothBeanList.get(position).name);
    }

    @Override
    public int getItemCount() {
        return mClothBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        ViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.home_fragment_adapter_item_text_tv);
        }
    }

}
