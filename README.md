# ViewPagerBottomSheet

This is a forked project from [ViewPagerBottomSheet](https://github.com/laenger/ViewPagerBottomSheet) which adds support for ViewPager2 in Bottom Sheets and Bottom Sheet Dialogs! 
ViewPager is allowed but the library uses reflection to operate. However, ViewPager2 doesn't need reflection, so we strongly recommend use ViewPager2 instead of ViewPager. 

## Example

This repository features an example app that demonstrates nested ViewPagers2 inside a bottom sheet:

![Example App](assets/viewpager.gif)



## Use for your project

Add the maven repo url to your `build.gradle`:

```groovy
repositories {
   maven { url 'https://jitpack.io' }
}
```

Add the library to the dependencies:

```groovy
dependencies {
    implementation 'com.github.xmartlabs:ViewPagerBottomSheet:-SNAPSHOT'
}
```

Use `ViewPagerBottomSheetBehavior` for your bottom sheet view:
```
app:layout_behavior="@string/view_pager_bottom_sheet_behavior"
```

Setup any nested `ViewPager` inside the bottom sheet:
```
BottomSheetUtils.setupViewPager(bottomSheetViewPager)
```
(This also works when the ViewPager _is_ the bottom sheet view and for further nested ViewPagers)

## Use with Bottom Sheet Dialogs

Use `ViewPagerBottomSheetDialogFragment` as super-class and setup any `ViewPager` as follows:

```java
public class DialogFragment extends ViewPagerBottomSheetDialogFragment {
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        final View contentView = View.inflate(getContext(), R.layout.dialog_bottom_sheet, null);

        ViewPager2 viewPager = (ViewPager2) contentView.findViewById(R.id.viewpager);
        // ...
        BottomSheetUtils.setupViewPager(viewPager);

        dialog.setContentView(contentView);
    }
}
```

![Example App](assets/dialog.gif)

## How does this work?

`ViewPagerBottomSheetBehavior` is essentially identical to the original `BottomSheetBehavior` from the Design Support Library but adds important details: ViewPagers are now properly handled when finding the scrolling child, and this reference can be updated on ViewPager page changes. See the key diff in this commit: [2775715](https://github.com/laenger/ViewPagerBottomSheet/commit/277571585500b8c1ed4ed444a5bd250b981c47fc)
