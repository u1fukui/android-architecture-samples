package com.u1fukui.droidkaigi2017_mvvm.view.activity;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.u1fukui.droidkaigi2017_mvvm.R;
import com.u1fukui.droidkaigi2017_mvvm.databinding.ActivityMainBinding;
import com.u1fukui.droidkaigi2017_mvvm.view.fragment.HomeFragment;
import com.u1fukui.droidkaigi2017_mvvm.view.fragment.MyPageFragment;
import com.u1fukui.droidkaigi2017_mvvm.viewmodel.ToolbarViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private ToolbarViewModel viewModel;

    private Fragment homeFragment;

    private Fragment myPageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        viewModel = new ToolbarViewModel();
        binding.setViewModel(viewModel);
        
        initViews();
        initFragments(savedInstanceState);
    }

    private void initViews() {
        binding.bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                viewModel.setToolbarTitle(item.getTitle().toString());
                item.setChecked(true);

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        switchFragment(homeFragment, HomeFragment.TAG);
                        break;
                    case R.id.nav_mypage:
                        switchFragment(myPageFragment, MyPageFragment.TAG);
                        break;
                }
                return false;
            }
        });
    }

    private void initFragments(Bundle savedInstanceState) {
        final FragmentManager manager = getSupportFragmentManager();
        homeFragment = manager.findFragmentByTag(HomeFragment.TAG);
        myPageFragment = manager.findFragmentByTag(MyPageFragment.TAG);

        if (homeFragment == null) {
            homeFragment = HomeFragment.newInstance();
        }
        if (myPageFragment == null) {
            myPageFragment = MyPageFragment.newInstance();
        }

        if (savedInstanceState == null) {
            switchFragment(homeFragment, HomeFragment.TAG);
        }
    }

    private boolean switchFragment(@NonNull Fragment fragment, @NonNull String tag) {
        if (fragment.isAdded()) {
            return false;
        }

        final FragmentManager manager = getSupportFragmentManager();
        final FragmentTransaction ft = manager.beginTransaction();

        final Fragment currentFragment = manager.findFragmentById(R.id.content_view);
        if (currentFragment != null) {
            ft.detach(currentFragment);
        }
        if (fragment.isDetached()) {
            ft.attach(fragment);
        } else {
            ft.add(R.id.content_view, fragment, tag);
        }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();

        // NOTE: When this method is called by user's continuous hitting at the same time,
        // transactions are queued, so necessary to reflect commit instantly before next transaction starts.
        manager.executePendingTransactions();

        return true;
    }
}
