package com.u1fukui.droidkaigi2017_mvvm.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.Log;
import android.view.View;

import com.u1fukui.droidkaigi2017_mvvm.R;
import com.u1fukui.droidkaigi2017_mvvm.model.Contributor;
import com.u1fukui.droidkaigi2017_mvvm.repository.contributors.ContributorsRepository;
import com.u1fukui.droidkaigi2017_mvvm.BR;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public final class ContributorsViewModel extends BaseObservable implements ViewModel {

    public static final String TAG = ContributorsViewModel.class.getSimpleName();

    private final ToolbarViewModel toolbarViewModel;

    private final ContributorsRepository contributorsRepository;

    private final CompositeDisposable compositeDisposable;

    private ObservableList<ContributorViewModel> viewModels;

    private int loadingVisibility;

    private boolean refreshing;

    @Nullable
    private Callback callback;

    public ContributorsViewModel(
            ToolbarViewModel toolbarViewModel,
            ContributorsRepository contributorsRepository,
            CompositeDisposable compositeDisposable) {

        this.toolbarViewModel = toolbarViewModel;
        this.contributorsRepository = contributorsRepository;
        this.compositeDisposable = compositeDisposable;
        this.viewModels = new ObservableArrayList<>();
    }

    public void setCallback(@NonNull Callback callback) {
        this.callback = callback;
    }

    public void start() {
        loadContributors(false);
    }

    @Override
    public void destroy() {
        compositeDisposable.clear();
    }

    @Bindable
    public int getLoadingVisibility() {
        return loadingVisibility;
    }

    private void setLoadingVisibility(int visibility) {
        this.loadingVisibility = visibility;
        notifyPropertyChanged(BR.loadingVisibility);
    }

    @Bindable
    public boolean getRefreshing() {
        return refreshing;
    }

    private void setRefreshing(boolean refreshing) {
        this.refreshing = refreshing;
        notifyPropertyChanged(BR.refreshing);
    }

    public void onSwipeRefresh() {
        loadContributors(true);
    }

    public void retry() {
        loadContributors(false);
    }

    public ObservableList<ContributorViewModel> getContributorViewModels() {
        return this.viewModels;
    }

    private void loadContributors(boolean refresh) {
        if (!refresh) {
            setLoadingVisibility(View.VISIBLE);
        }

        Disposable disposable = contributorsRepository.findAll()
                .map(new Function<List<Contributor>, List<ContributorViewModel>>() {
                    @Override
                    public List<ContributorViewModel> apply(List<Contributor> contributors) throws Exception {
                        List<ContributorViewModel> list = new ArrayList<>();
                        for (Contributor contributor : contributors) {
                            list.add(new ContributorViewModel(contributor));
                        }
                        return list;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ContributorViewModel>>() {
                    @Override
                    public void accept(List<ContributorViewModel> contributorViewModels) throws Exception {
                        renderContributors(contributorViewModels);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setLoadingVisibility(View.GONE);
                        if (callback != null) {
                            callback.showError(R.string.error);
                        }
                        Log.e(TAG, "Failed to show contributors", throwable);
                    }
                });
        compositeDisposable.add(disposable);
    }

    private void renderContributors(List<ContributorViewModel> contributorViewModels) {
        viewModels.clear();
        viewModels.addAll(contributorViewModels);

        toolbarViewModel.setToolbarTitle("Contributors: " + contributorViewModels.size());

        setLoadingVisibility(View.GONE);
        setRefreshing(false);
    }

    public interface Callback {

        void showError(@StringRes int textRes);
    }
}
