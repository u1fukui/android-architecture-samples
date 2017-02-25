package com.u1fukui.droidkaigi2017_mvvm.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.u1fukui.droidkaigi2017_mvvm.R;
import com.u1fukui.droidkaigi2017_mvvm.databinding.FragmentMypageBinding;

public class MyPageFragment extends Fragment {

    public static final String TAG = MyPageFragment.class.getSimpleName();

    private FragmentMypageBinding binding;

    public static MyPageFragment newInstance() {
        return new MyPageFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMypageBinding.inflate(inflater, container, false);
        binding.title.setText(R.string.tab_mypage);
        return binding.getRoot();
    }
}
