package com.u1fukui.droidkaigi2017_mvvm.view.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.u1fukui.droidkaigi2017_mvvm.R;
import com.u1fukui.droidkaigi2017_mvvm.customview.BindingHolder;
import com.u1fukui.droidkaigi2017_mvvm.databinding.ViewContributorBinding;
import com.u1fukui.droidkaigi2017_mvvm.viewmodel.ContributorViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<BindingHolder<ViewContributorBinding>> {

    private List<ContributorViewModel> itemList = new ArrayList<>();

    public HomeRecyclerAdapter(List<ContributorViewModel> list) {
        itemList.addAll(list);
    }

    @Override
    public BindingHolder<ViewContributorBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BindingHolder<>(parent, R.layout.view_contributor);
    }

    @Override
    public void onBindViewHolder(BindingHolder<ViewContributorBinding> holder, int position) {
        ContributorViewModel viewModel = itemList.get(position);
        holder.binding.setViewModel(viewModel);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
