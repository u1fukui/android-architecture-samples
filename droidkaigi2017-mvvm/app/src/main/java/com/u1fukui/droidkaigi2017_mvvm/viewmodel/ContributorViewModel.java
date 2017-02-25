package com.u1fukui.droidkaigi2017_mvvm.viewmodel;

import android.databinding.BaseObservable;
import android.util.Log;
import android.view.View;

import com.u1fukui.droidkaigi2017_mvvm.model.Contributor;

public class ContributorViewModel extends BaseObservable implements ViewModel {

    private Contributor contributor;

    public ContributorViewModel(Contributor contributor) {
        this.contributor = contributor;
    }

    public String getName() {
        return contributor.name;
    }

    public String getAvatorUrl() {
        return contributor.avatarUrl;
    }

    public void onItemClick(View view) {
        Log.d("TAG", "onItemClick!");
    }

    @Override
    public void destroy() {
        // nop
    }
}
