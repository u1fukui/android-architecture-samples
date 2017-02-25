package com.u1fukui.droidkaigi2017_mvvm.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.u1fukui.droidkaigi2017_mvvm.databinding.FragmentHomeBinding;
import com.u1fukui.droidkaigi2017_mvvm.model.Contributor;
import com.u1fukui.droidkaigi2017_mvvm.viewmodel.ContributorViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public static final String TAG = HomeFragment.class.getSimpleName();

    private FragmentHomeBinding binding;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        binding.recyclerView.setAdapter(new HomeRecyclerAdapter(createItemList()));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return binding.getRoot();
    }

    private List<ContributorViewModel> createItemList() {
        List<ContributorViewModel> list = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            Contributor contributor = new Contributor();
            contributor.name = Integer.toString(i);
            list.add(new ContributorViewModel(contributor));
        }
        return list;
    }

    @Override
    public void onDestroyView() {
        binding.recyclerView.setAdapter(null);
        super.onDestroyView();
    }
}
