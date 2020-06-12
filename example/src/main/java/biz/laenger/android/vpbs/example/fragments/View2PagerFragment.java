package biz.laenger.android.vpbs.example.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import biz.laenger.android.vpbs.example.R;
import biz.laenger.android.vpbs.example.ViewPager2Adapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class View2PagerFragment extends Fragment {

    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager2)
    ViewPager2 viewPager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_view_pager, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupNestedViewPager();
    }

    private void setupNestedViewPager() {
        final ViewPager2Adapter sectionsPagerAdapter = new ViewPager2Adapter(
                this,
                ViewPager2Adapter.TabItem.NESTED_SCROLL,
                ViewPager2Adapter.TabItem.RECYCLER
        );
        viewPager.setAdapter(sectionsPagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> sectionsPagerAdapter.getPageTitle(position)
        ).attach();
    }

}
