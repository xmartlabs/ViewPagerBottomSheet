package biz.laenger.android.vpbs;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import java.lang.reflect.Field;

public final class BottomSheetUtils {
    public static void setupViewPager(@NonNull ViewPager viewPager) {
        final View bottomSheetParent = findBottomSheetParent(viewPager);
        if (bottomSheetParent != null) {
            viewPager.addOnPageChangeListener(new BottomSheetViewPagerListener(viewPager, bottomSheetParent));
        }
    }

    public static void setupViewPager(@NonNull ViewPager2 viewPager) {
        final View bottomSheetParent = findBottomSheetParent(viewPager);
        if (bottomSheetParent != null) {
            viewPager.registerOnPageChangeCallback(new BottomSheetViewPager2Listener(viewPager, bottomSheetParent));
        }
    }

    static View getCurrentView(@NonNull ViewPager viewPager) {
        final int currentItem = viewPager.getCurrentItem();
        for (int i = 0; i < viewPager.getChildCount(); i++) {
            final View child = viewPager.getChildAt(i);
            final ViewPager.LayoutParams layoutParams = (ViewPager.LayoutParams) child.getLayoutParams();
            int position = -1;
            try {
                Field field = layoutParams.getClass().getDeclaredField("position");
                field.setAccessible(true);
                position = (int) field.get(layoutParams);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (!layoutParams.isDecor && currentItem == position) {
                return child;
            }
        }
        return null;
    }

    private static class BottomSheetViewPagerListener extends ViewPager.SimpleOnPageChangeListener {
        private final ViewPager viewPager;
        private final ViewPagerBottomSheetBehavior<View> behavior;

        private BottomSheetViewPagerListener(ViewPager viewPager, View bottomSheetParent) {
            this.viewPager = viewPager;
            this.behavior = ViewPagerBottomSheetBehavior.from(bottomSheetParent);
        }

        @Override
        public void onPageSelected(int position) {
            viewPager.post(behavior::invalidateScrollingChild);
        }
    }

    private static class BottomSheetViewPager2Listener extends ViewPager2.OnPageChangeCallback {
        private final ViewPager2 viewPager;
        private final ViewPagerBottomSheetBehavior<View> behavior;

        private BottomSheetViewPager2Listener(ViewPager2 viewPager, View bottomSheetParent) {
            this.viewPager = viewPager;
            this.behavior = ViewPagerBottomSheetBehavior.from(bottomSheetParent);
        }

        @Override
        public void onPageSelected(int position) {
            viewPager.post(behavior::invalidateScrollingChild);
        }
    }

    private static View findBottomSheetParent(final View view) {
        View current = view;
        while (current != null) {
            final ViewGroup.LayoutParams params = current.getLayoutParams();
            if (params instanceof CoordinatorLayout.LayoutParams && ((CoordinatorLayout.LayoutParams) params).getBehavior() instanceof ViewPagerBottomSheetBehavior) {
                return current;
            }
            final ViewParent parent = current.getParent();
            current = parent == null || !(parent instanceof View) ? null : (View) parent;
        }
        return null;
    }

}
