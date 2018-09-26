package com.cloth.clothes.home.homefragment;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloth.clothes.R;
import com.cloth.clothes.clothessecondlist.ClothesSecondActivity;
import com.cloth.clothes.home.domain.model.ClothesBean;
import com.cloth.clothes.model.UserManager;
import com.cloth.kernel.service.DialogWrapper;

import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {


    private List<ClothesBean> mClothBeanList;
    private Listener mListener;

    public StoreAdapter(@NonNull List<ClothesBean> beanList) {
        mClothBeanList = beanList;
    }

    public void refreshItem(@NonNull List<ClothesBean> clothBeanList) {
        mClothBeanList.clear();
        mClothBeanList.addAll(clothBeanList);
    }

    public void refreshItem(int position, @Nullable ClothesBean clothesBean) {
        mClothBeanList.set(position, clothesBean);
        notifyItemChanged(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_adapter_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.desc.setText(mClothBeanList.get(position).name);
        holder.brand.setText(String.valueOf(mClothBeanList.get(position).brand));
        holder.type.setText(String.valueOf(mClothBeanList.get(position).type));
        holder.batch.setText(mClothBeanList.get(position).batch);
//        holder.clothesImg
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClothesSecondActivity.jump(UserManager.getInstance().getUser().address.id, String.valueOf(mClothBeanList.get(holder.getAdapterPosition()).id));
//                DetailActivity.jump(mClothBeanList.get(holder.getAdapterPosition()),holder.getAdapterPosition());
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!UserManager.getInstance().isOwner()) {
                    return false;
                }
                new AlertDialog.Builder(holder.itemView.getContext())
                        .setTitle("警告")
                        .setMessage("请确认是否删除此批次？")
                        .setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {

                            }
                        })
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (mListener != null) {
                                    mListener.delete(mClothBeanList.get(holder.getAdapterPosition()), holder.getAdapterPosition());
                                }
                            }
                        }).create().show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mClothBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView desc;
        TextView brand;
        TextView batch;
        TextView type;
        ImageView clothesImg;

        ViewHolder(View itemView) {
            super(itemView);
            clothesImg = itemView.findViewById(R.id.home_fragment_adapter_item_cloth_img);
            desc = itemView.findViewById(R.id.home_fragment_adapter_item_desc_tv);
            brand = itemView.findViewById(R.id.home_fragment_adapter_item_brand_tv);
            type = itemView.findViewById(R.id.home_fragment_adapter_item_type_tv);
            batch = itemView.findViewById(R.id.home_fragment_adapter_item_batch_tv);
        }
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    public interface Listener {
        void delete(ClothesBean clothesBean, int position);
    }

}
