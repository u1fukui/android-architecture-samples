package com.u1fukui.droidkaigi2017_mvvm.view.fragment;

import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.u1fukui.droidkaigi2017_mvvm.App;
import com.u1fukui.droidkaigi2017_mvvm.R;
import com.u1fukui.droidkaigi2017_mvvm.databinding.FragmentHomeBinding;
import com.u1fukui.droidkaigi2017_mvvm.databinding.ViewContributorBinding;
import com.u1fukui.droidkaigi2017_mvvm.repository.contributors.ContributorsRemoteDataSource;
import com.u1fukui.droidkaigi2017_mvvm.repository.contributors.ContributorsRepository;
import com.u1fukui.droidkaigi2017_mvvm.view.customview.BindingHolder;
import com.u1fukui.droidkaigi2017_mvvm.view.customview.ObservableListRecyclerAdapter;
import com.u1fukui.droidkaigi2017_mvvm.viewmodel.ContributorViewModel;
import com.u1fukui.droidkaigi2017_mvvm.viewmodel.ContributorsViewModel;
import com.u1fukui.droidkaigi2017_mvvm.viewmodel.ToolbarViewModel;

public class HomeFragment extends Fragment implements ContributorsViewModel.Callback {

    public static final String TAG = HomeFragment.class.getSimpleName();

    private FragmentHomeBinding binding;

    private ContributorsViewModel viewModel;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        viewModel = createViewModel();
        viewModel.setCallback(this);
        viewModel.start();
        binding.setViewModel(viewModel);

        initRecyclerView();

        return binding.getRoot();
    }

    private ContributorsViewModel createViewModel() {
        ToolbarViewModel toolbarVm = new ToolbarViewModel();

        ContributorsRemoteDataSource dataSource = new ContributorsRemoteDataSource(App.getApiClient());
        ContributorsRepository repository = new ContributorsRepository(dataSource);

        return new ContributorsViewModel(toolbarVm, repository, App.getCompositeDisposable());
    }

    private void initRecyclerView() {
        binding.recyclerView.setAdapter(new HomeRecyclerAdapter(viewModel.getContributorViewModels()));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onStop() {
        super.onStop();
        viewModel.destroy();
    }

    @Override
    public void onDestroyView() {
        viewModel.setCallback(null);
        binding.recyclerView.setAdapter(null);
        super.onDestroyView();
    }

    //region ContributorsViewModel.Callback
    @Override
    public void showError(@StringRes int textRes) {
        Snackbar.make(binding.getRoot(), textRes, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewModel.retry();
                    }
                })
                .setActionTextColor(ContextCompat.getColor(getActivity(), R.color.white))
                .show();
    }
    //endregion

    private static class HomeRecyclerAdapter extends ObservableListRecyclerAdapter<ContributorViewModel, BindingHolder<ViewContributorBinding>> {

        public HomeRecyclerAdapter(@NonNull ObservableList<ContributorViewModel> list) {
            super(list);
        }

        @Override
        public BindingHolder<ViewContributorBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BindingHolder<>(parent, R.layout.view_contributor);
        }

        @Override
        public void onBindViewHolder(BindingHolder<ViewContributorBinding> holder, int position) {
            ContributorViewModel viewModel = getItem(position);
            holder.binding.setViewModel(viewModel);
            holder.binding.executePendingBindings();
        }
    }
}
