package biz.laenger.android.vpbs.example;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import biz.laenger.android.vpbs.example.fragments.NestedScrollFragment;
import biz.laenger.android.vpbs.example.fragments.RecyclerFragment;
import biz.laenger.android.vpbs.example.fragments.ViewPagerFragment;

public class ViewPager2Adapter extends FragmentStateAdapter {
    public enum TabItem {
        RECYCLER(RecyclerFragment.class, R.string.tab_recycler),
        NESTED_SCROLL(NestedScrollFragment.class, R.string.tab_nested_scroll),
        VIEW_PAGER(ViewPagerFragment.class, R.string.tab_view_pager);

        private final Class<? extends Fragment> fragmentClass;
        private final int titleResId;

        TabItem(Class<? extends Fragment> fragmentClass, @StringRes int titleResId) {
            this.fragmentClass = fragmentClass;
            this.titleResId = titleResId;
        }
    }

    private final TabItem[] tabItems;
    private final Context context;

    public ViewPager2Adapter(Fragment fragment, TabItem... tabItems) {
        super(fragment);
        this.context = fragment.requireContext();
        this.tabItems = tabItems;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return newInstance(tabItems[position].fragmentClass);
    }

    @Override
    public int getItemCount() {
        return tabItems.length;
    }

    private Fragment newInstance(Class<? extends Fragment> fragmentClass) {
        try {
            return fragmentClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("fragment must have public no-arg constructor: " + fragmentClass.getName(), e);
        }
    }

    public CharSequence getPageTitle(int position) {
        return context.getString(tabItems[position].titleResId);
    }
}
