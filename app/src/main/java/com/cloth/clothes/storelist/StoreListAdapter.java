package com.cloth.clothes.storelist;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cloth.clothes.R;
import com.cloth.clothes.clothesdetail.DetailActivity;
import com.cloth.clothes.storelist.domain.model.NumberModel;

import java.util.List;

public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.ViewHolder>{


    private List<NumberModel> mData;

    public StoreListAdapter(@NonNull List<NumberModel> data) {
        mData = data;
    }

    public void refresh(List<NumberModel> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_store_list_adapter, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.color.setText(mData.get(position).color);
        holder.size.setText(mData.get(position).size);
        holder.number.setText(mData.get(position).number);
        holder.store.setText(mData.get(position).store.address);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivity.jump(mData.get(holder.getAdapterPosition()).clothe,holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView color,size,store,number;
        Button mButton;
        public ViewHolder(View itemView) {
            super(itemView);
            color = itemView.findViewById(R.id.actitity_store_list_adapter_item_color_tv);
            size = itemView.findViewById(R.id.actitity_store_list_adapter_item_size_tv);
            store = itemView.findViewById(R.id.actitity_store_list_adapter_item_store_tv);
            number = itemView.findViewById(R.id.actitity_store_list_adapter_item_number_tv);
            mButton = itemView.findViewById(R.id.item_activity_store_list_selection_btn);
        }
    }

}
