package com.cloth.clothes.clothessecondlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cloth.clothes.R;
import com.cloth.clothes.clothesdetail.DetailActivity;
import com.cloth.clothes.clothessecondlist.domain.model.ClothesSecondModel;
import com.cloth.clothes.model.UserManager;

import java.util.List;

public class ClothesSecondAdapter extends RecyclerView.Adapter<ClothesSecondAdapter.ViewHolder> {

    private Listener mListener;
    private List<ClothesSecondModel> mData;

    public ClothesSecondAdapter(@NonNull List<ClothesSecondModel> data) {
        mData = data;
    }

    public void refresh(List<ClothesSecondModel> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void setListener(Listener listener) {
        mListener = listener;
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
        holder.number.setText(String.valueOf(mData.get(position).number));
        holder.store.setText(mData.get(position).store.address);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivity.jump(mData.get(holder.getAdapterPosition()), holder.getAdapterPosition());
            }
        });
        holder.mButton.setSelected(mData.get(position).isStopSell.equals("1"));
        holder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.clickListener(mData.get(holder.getAdapterPosition()).id,
                        mData.get(holder.getAdapterPosition()).isStopSell.equals("1") ? "0" : "1",
                        holder.getAdapterPosition());
            }
        });

        if (UserManager.getInstance().isOwner()) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mListener.longClickListener(mData.get(holder.getAdapterPosition()).id, holder.getAdapterPosition());
                    return true;
                }
            });
            holder.mButton.setVisibility(View.VISIBLE);
        } else {
            holder.itemView.setOnLongClickListener(null);
            holder.mButton.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView color, size, store, number;
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


    public interface Listener {
        void longClickListener(String cid, int postion);

        void clickListener(String cid, String isStopSell, int position);
    }
}
