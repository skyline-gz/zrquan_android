package com.zrquan.mobile.ui.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zrquan.mobile.support.util.LogUtils;

public class CommonFragment extends Fragment {
    protected final String LOG_TAG = getClass().getName();

    public void onActivityCreated(Bundle paramBundle) {
        super.onActivityCreated(paramBundle);
        LogUtils.d(this.LOG_TAG, "onActivityCreated");
    }

    public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
        super.onActivityResult(paramInt1, paramInt2, paramIntent);
        LogUtils.d(this.LOG_TAG, "onActivityResult");
    }

    public void onAttach(Activity paramActivity) {
        super.onAttach(paramActivity);
        LogUtils.d(this.LOG_TAG, "onAttach");
    }

    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        LogUtils.d(this.LOG_TAG, "onCreate");
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        View localView = super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
        LogUtils.d(this.LOG_TAG, "onCreateView");
        return localView;
    }

    public void onDestroy() {
        super.onDestroy();
        LogUtils.d(this.LOG_TAG, "onDestroy");
    }

    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.d(this.LOG_TAG, "onDestroyView");
    }

    public void onDetach() {
        super.onDetach();
        LogUtils.d(this.LOG_TAG, "onDetach");
    }

    public void onPause() {
        super.onPause();
        LogUtils.d(this.LOG_TAG, "onPause");
    }

    public void onResume() {
        super.onResume();
        LogUtils.d(this.LOG_TAG, "onResume");
    }

    public void onSaveInstanceState(Bundle paramBundle) {
        super.onSaveInstanceState(paramBundle);
        LogUtils.d(this.LOG_TAG, "onSaveInstanceState");
    }

    public void onStart() {
        super.onStart();
        LogUtils.d(this.LOG_TAG, "onStart");
    }

    public void onStop() {
        super.onStop();
        LogUtils.d(this.LOG_TAG, "onStop");
    }

    public void onViewCreated(View paramView, Bundle paramBundle) {
        super.onViewCreated(paramView, paramBundle);
        LogUtils.d(this.LOG_TAG, "onViewCreated");
    }

    public void onViewStateRestored(Bundle paramBundle) {
        super.onViewStateRestored(paramBundle);
        LogUtils.d(this.LOG_TAG, "onViewStateRestored");
    }
}