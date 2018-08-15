package com.cloth.clothes.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloth.clothes.R;
import com.cloth.clothes.home.domain.model.ClothBean;
import com.cloth.kernel.base.BaseFrament;

import java.util.ArrayList;

public class HomeFragment extends BaseFrament{


    public HomeFragment() {
    }

    private RecyclerView mRecyclerView;
    private HomeAdapter mHomeAdapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home,container,false);
        mRecyclerView = root.findViewById(R.id.fragment_home_rv);
        init();
        return root;
    }



    private void init() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        ArrayList<ClothBean> clothBeans = new ArrayList<>();
        clothBeans.add(new ClothBean("nihao1"));
        clothBeans.add(new ClothBean("nihao2"));
        clothBeans.add(new ClothBean("nihao3"));
        clothBeans.add(new ClothBean("nihao4"));
        mHomeAdapter = new HomeAdapter(clothBeans);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mHomeAdapter);

    }

}
