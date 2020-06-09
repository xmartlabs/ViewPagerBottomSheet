package biz.laenger.android.vpbs.example.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import biz.laenger.android.vpbs.BottomSheetUtils;
import biz.laenger.android.vpbs.ViewPagerBottomSheetDialogFragment;
import biz.laenger.android.vpbs.example.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogFragment extends ViewPagerBottomSheetDialogFragment {

    @BindView(R.id.bottom_sheet_toolbar)
    Toolbar bottomSheetToolbar;
    @BindView(R.id.bottom_sheet_tabs)
    TabLayout bottomSheetTabLayout;
    @BindView(R.id.bottom_sheet_viewpager2)
    ViewPager2 bottomSheetViewPager;

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        final View contentView = View.inflate(getContext(), R.layout.dialog_bottom_sheet, null);
        ButterKnife.bind(this, contentView);
        dialog.setContentView(contentView);

        setupViewPager();
    }

    private void setupViewPager() {
        bottomSheetToolbar.setTitle(R.string.action_bottom_sheet_dialog);
        bottomSheetViewPager.setOffscreenPageLimit(1);
        SimplePagerAdapter adapter = new SimplePagerAdapter(getContext());
        bottomSheetViewPager.setAdapter(adapter);
        BottomSheetUtils.setupViewPager(bottomSheetViewPager);
        new TabLayoutMediator(bottomSheetTabLayout, bottomSheetViewPager,
                (tab, position) -> adapter.getPageTitle(position)
        ).attach();
    }

    static class SimplePagerAdapter extends RecyclerView.Adapter<SimplePagerAdapter.ViewHolder> {
        private final Context context;

        SimplePagerAdapter(Context context) {
            this.context = context;
        }

        public CharSequence getPageTitle(int position) {
            return context.getString(R.string.tab_nested_scroll) + " " + String.valueOf(position);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_nested_scroll, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 2;
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }

}
