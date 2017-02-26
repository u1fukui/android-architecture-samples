package com.u1fukui.droidkaigi2017_mvvm.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;

public class ToolbarViewModel extends BaseObservable implements ViewModel {

    private String toolbarTitle;

    public ToolbarViewModel() {
    }

    @Bindable
    public String getToolbarTitle() {
        return toolbarTitle;
    }

    public void setToolbarTitle(String title) {
        toolbarTitle = title;
        notifyPropertyChanged(BR.toolbarTitle);
    }

    @Override
    public void destroy() {
        // Nothing to do
    }
}
