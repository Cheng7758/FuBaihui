package com.example.zhanghao.woaisiji.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return initBaseFragmentView(inflater,container,savedInstanceState);
    }

    public abstract View initBaseFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
}
