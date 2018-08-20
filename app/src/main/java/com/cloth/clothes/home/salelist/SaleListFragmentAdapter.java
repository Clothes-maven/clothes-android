package com.cloth.clothes.home.salelist;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloth.clothes.R;
import com.cloth.clothes.home.salelist.domain.model.SaleBean;

import java.util.List;

public class SaleListFragmentAdapter extends RecyclerView.Adapter<SaleListFragmentAdapter.ViewHolder>{

    private List<SaleBean> mSaleBeanList;

    public SaleListFragmentAdapter(@Nullable List<SaleBean> saleBeanList) {
        mSaleBeanList = saleBeanList;
    }

    public void setSaleBeanList(@NonNull List<SaleBean> saleBeanList) {
        mSaleBeanList = saleBeanList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_sale_list_adapter_item, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SaleBean saleBean = mSaleBeanList.get(position);
//        holder.imgUrl
        holder.name.setText(saleBean.name);
        holder.cost.setText(saleBean.cost);
        holder.sale.setText(saleBean.sale);
        holder.profit.setText(saleBean.profit);
        holder.employee.setText(saleBean.employee);
    }

    @Override
    public int getItemCount() {
        if (mSaleBeanList ==null) {
            return 0;
        }
        return mSaleBeanList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgUrl;
        TextView name;
        TextView cost;
        TextView sale;
        TextView profit;
        TextView employee;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.fragment_sale_list_adapter_item_desc_tv);
            cost = itemView.findViewById(R.id.fragment_sale_list_adapter_item_cost_tv);
            sale = itemView.findViewById(R.id.fragment_sale_list_adapter_item_sale_tv);
            profit = itemView.findViewById(R.id.fragment_sale_list_adapter_item_profit_tv);
            employee = itemView.findViewById(R.id.fragment_sale_list_adapter_item_employee_tv);
        }
    }
}
