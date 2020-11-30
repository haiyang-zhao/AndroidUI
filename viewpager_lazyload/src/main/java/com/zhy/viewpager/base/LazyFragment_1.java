package com.zhy.viewpager.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zhy.viewpager.FragmentDelegate;

public abstract class LazyFragment_1 extends Fragment {
    private FragmentDelegate mFragmentDelegate;
    private View rootView = null;
    private boolean isViewCreated = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        E("onCreateView: ");
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutRes(),container, false);
        }
        isViewCreated  = true; // TODO 解决奔溃1.1
        initView(rootView); // 初始化控件
        return rootView;
    }


    // 让子类完成，初始化布局，初始化控件
    protected abstract void initView(View rootView);

    protected abstract int getLayoutRes();

    // -->>>停止网络数据请求
    public void onFragmentLoadStop() {
        E("onFragmentLoadStop");
    }

    // -->>>加载网络数据请求
    public void onFragmentLoad() {
        E("onFragmentLoad");
    }

    @Override
    public void onResume() {
        super.onResume();
        E("onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        E("onPause");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        E("onDestroyView");
    }

    // 工具相关而已
    public void setFragmentDelegate(FragmentDelegate fragmentDelegater) {
        mFragmentDelegate = fragmentDelegater;
    }

    private void E(String message) {
        if (mFragmentDelegate != null) {
            mFragmentDelegate.dumpLifeCycle(message);
        }
    }
}
