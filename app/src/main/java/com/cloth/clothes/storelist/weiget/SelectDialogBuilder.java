package com.cloth.clothes.storelist.weiget;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.cloth.clothes.R;
import com.cloth.clothes.home.domain.model.StoreBean;

import java.util.ArrayList;
import java.util.List;

public class SelectDialogBuilder extends AlertDialog.Builder {
    private DialogAdapter mSizeAdapter;
    private DialogAdapter mColorAdapter;
    private DialogAdapter mStoreAdapter;
    private  List<StoreBean> mStoreBeans;

    public SelectDialogBuilder(@NonNull Context context) {
        super(context);
        init(context);
    }

    public SelectDialogBuilder(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }


    public void setPositiveCallback (final PositiveCallBack positiveCallback) {
        setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (positiveCallback != null && mSizeAdapter != null && mColorAdapter != null && mStoreAdapter != null) {
                    StoreBean store = new StoreBean();
                    String storeName = mStoreAdapter.getStr();
                    for (StoreBean storeBean : mStoreBeans) {
                        if (storeName.equals(storeBean.name)) {
                            store = storeBean;
                            break;
                        }
                    }
                    positiveCallback.call(mSizeAdapter.getStr(), mColorAdapter.getStr(), store);
                }
            }
        });
    }


    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_select_dialog, null, false);
        setView(view);
        //size list
        RecyclerView mSizeRecyclerView = view.findViewById(R.id.layout_select_dialog_size_rv);
        mSizeAdapter = new DialogAdapter();
        mSizeRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration decoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        mSizeRecyclerView.addItemDecoration(decoration);
        mSizeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mSizeRecyclerView.setAdapter(mSizeAdapter);

        //color list
        RecyclerView mColorRecyclerView = view.findViewById(R.id.layout_select_dialog_color_rv);
        mColorAdapter = new DialogAdapter();
        mColorRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration decorationColor = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        mColorRecyclerView.addItemDecoration(decorationColor);
        mColorRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mColorRecyclerView.setAdapter(mColorAdapter);

        //store list
        RecyclerView mStoreRecyclerView = view.findViewById(R.id.layout_select_dialog_store_rv);
        mStoreAdapter = new DialogAdapter();
        mStoreRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration decorationStore = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        mStoreRecyclerView.addItemDecoration(decorationStore);
        mStoreRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mStoreRecyclerView.setAdapter(mStoreAdapter);
    }

    /**
     * 设置size 数据并刷新
     */
    public void setSizeData(@Nullable List<String> list) {
        mSizeAdapter.setStrings(list);
    }

    /**
     * 设置颜色并刷新
     */
    public void setColorData(@Nullable List<String> list) {
        mColorAdapter.setStrings(list);
    }

    /**
     * 设置门店列表并刷新
     */
    public void setStoreData(@Nullable List<StoreBean> list) {
        List<String> data = new ArrayList<>();
        if (list ==null) return;

        mStoreBeans = list;
        for (StoreBean storeBean : list) {
            data.add(storeBean.name);
        }
        mStoreAdapter.setStrings(data);
    }

    private class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.ViewHolder> {

        List<ContentBean> mContentBeanList;

        public DialogAdapter() {
        }

        public List<String> getStrings() {
            List<String> strings = new ArrayList<>();
            for (ContentBean contentBean : mContentBeanList) {
                strings.add(contentBean.text);
            }
            return strings;
        }

        public String getStr() {
            for (ContentBean contentBean : mContentBeanList) {
                if (contentBean.state) {
                    return contentBean.text;
                }
            }
            return null;
        }

        public void setStrings(List<String> strings) {
            if (strings == null || strings.size() <= 0) {
                return;
            }
            if (mContentBeanList == null) {
                mContentBeanList = new ArrayList<>();
            }
            for (String string : strings) {
                ContentBean bean = new ContentBean();
                bean.text = string;
                mContentBeanList.add(bean);
            }
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_dialog, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
            holder.mTextView.setText(mContentBeanList.get(position).text);
            holder.mRadioButton.setChecked(mContentBeanList.get(position).state);
            holder.mRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        for (ContentBean contentBean : mContentBeanList) {
                            contentBean.state = false;
                        }
                        notifyDataSetChanged();
                    }
                    mContentBeanList.get(position).state = isChecked;
                }
            });
        }

        @Override
        public int getItemCount() {
            if (mContentBeanList != null && mContentBeanList.size() > 0) {
                return mContentBeanList.size();
            }
            return 0;
        }

        final class ViewHolder extends RecyclerView.ViewHolder {
            TextView mTextView;
            AppCompatCheckBox mRadioButton;

            ViewHolder(View itemView) {
                super(itemView);
                mTextView = itemView.findViewById(R.id.item_select_dialog_text_tv);
                mRadioButton = itemView.findViewById(R.id.item_select_dialog_selection_rb);
            }
        }

        public class ContentBean {
            public String text;
            public boolean state;
        }
    }

    public interface PositiveCallBack {
        void call(String size, String color, StoreBean storeBean);
    }

}
