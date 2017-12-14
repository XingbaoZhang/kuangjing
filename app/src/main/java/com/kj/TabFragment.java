package com.kj;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by zq on 2017/1/12.
 */
public class TabFragment extends Fragment {

    public static Fragment newInstance() {
        TabFragment fragment = new TabFragment();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.common_list, container, false);

        return rootView;
    }
}
