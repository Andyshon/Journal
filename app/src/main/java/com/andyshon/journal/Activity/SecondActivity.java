package com.andyshon.journal.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.andyshon.journal.Adapter.ItemAdapter;
import com.andyshon.journal.Adapter.PagerAdapter;
import com.andyshon.journal.FragmentItems.Gallery;
import com.andyshon.journal.FragmentItems.Note;
import com.andyshon.journal.Utils.FragmentItemsUtils;
import com.andyshon.journal.Fragments.GalleryFragment;
import com.andyshon.journal.Utils.KeyboardUtil;
import com.andyshon.journal.Fragments.NoteFragment;
import com.andyshon.journal.Fragments.ParentFragment;
import com.andyshon.journal.R;


public class SecondActivity extends AppCompatActivity
        implements NoteFragment.NoteCallback, GalleryFragment.GalleryCallback, PagerAdapter.PagerAdapterCallback, ItemAdapter.ItemAdapterCallback {


    private static final int GET_IMAGE = 1;
    private CustomViewPager viewPager;
    private TabLayout tabLayout;
    private PagerAdapter adapter;
    private PanesBottomSheetBehavior mBottomBehavior;

    private ParentFragment parentFragment;

    public static int CURRENT_ITEM=0;

    private Fragment currentFragment;
    private ProgressBar mRecordingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        mRecordingBar = (ProgressBar) findViewById(R.id.recording_progress_bar);
        hideRecordingBar();

        //ViewPager pager = (ViewPager) findViewById(R.id.pager);
        View bottomSheet = findViewById(R.id.bottom);
        //View controlBarSpacer = view.findViewById(R.id.control_bar_spacer);
        FrameLayout controlBar = (FrameLayout) findViewById(R.id.bottom_control_bar);

        mBottomBehavior = (PanesBottomSheetBehavior) ((CoordinatorLayout.LayoutParams) bottomSheet.getLayoutParams()).getBehavior();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction tr = fragmentManager.beginTransaction();
        //parentFragment = new ParentFragment(mBottomBehavior);
        parentFragment = ParentFragment.newInstance(mBottomBehavior);
        tr.replace(R.id.experiment_pane, parentFragment, "s").commit();


        ImageView tvSendBtn = findViewById(R.id.tvSendBtn);
        tvSendBtn.setOnClickListener(view -> {
            Fragment fragment = getCurrentFragment();
            System.out.println("CURRENT_ITEM---" + CURRENT_ITEM + "FRAGMENT::: " + fragment.getClass());

            if (CURRENT_ITEM == 0) {
                NoteFragment noteFragment = (NoteFragment) fragment;
                noteFragment.requestForNote();
            }
            else if (CURRENT_ITEM == 1){
                GalleryFragment galleryFragment = (GalleryFragment) fragment;
            }
        });


        setCoordinatorBehavior(controlBar, new BottomDependentBehavior() {
            @Override
            public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
                int dependencyTop = dependency.getTop();
                int belowHalf = dependencyTop - (parent.getHeight() / 2);

                // Translate down once the drawer is below halfway
                int translateY = Math.max(0, belowHalf);
                if (child.getTranslationY() != translateY) {
                    child.setTranslationY(translateY);
                    return true;
                }

                return false;
            }
        });



        mBottomBehavior.setBottomSheetCallback(
                new PanesBottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(@NonNull View bottomSheet, int newState) {
                        if (getBottomDrawerState() == PanesBottomSheetBehavior.STATE_COLLAPSED) {
                            // We no longer need to know what happens when the keyboard closes: Stay closed.
                            KeyboardUtil.closeKeyboard(SecondActivity.this).subscribe();
                        }
                    }

                    @Override
                    public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                    }
                });

        mBottomBehavior.setPeekHeight(getResources().getDimensionPixelSize(R.dimen.panes_toolbar_height)+25);


        tabLayout = (TabLayout) findViewById(R.id.tool_picker);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_comment_white_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_photo_white_24dp));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        viewPager = (CustomViewPager) findViewById(R.id.pager);
        adapter = new PagerAdapter(SecondActivity.this, getFragmentManager(), tabLayout.getTabCount());
        viewPager.setOffscreenPageLimit(0);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                System.out.println("onTabSelected ::"  +tab.getTag() + " : " + tab.getPosition());
                viewPager.setCurrentItem(tab.getPosition());
                CURRENT_ITEM = viewPager.getCurrentItem();
                System.out.println("CURRENT_ITEM = " + CURRENT_ITEM);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        resetAdapter();

    }


    private void resetAdapter() {
        viewPager.setAdapter(adapter);
    }


    private void setCurrentFragment (Fragment fragment) {
        currentFragment = fragment;
    }


    private Fragment getCurrentFragment () {
        return currentFragment;
    }


    @Override
    public void onGetCurrentFragment(Fragment currentFragment) {
        System.out.println("INSTANCE OF:" + currentFragment.getClass());
        setCurrentFragment(currentFragment);
    }


    @Override
    public void onGetNote(Note note) {
        parentFragment.updateAdapter(note);
    }


    private int getBottomDrawerState() {
        if (mBottomBehavior == null) {
            return PanesBottomSheetBehavior.STATE_MIDDLE;
        }
        return mBottomBehavior.getState();
    }


    @Override
    public void NotifyAdapter() {
        parentFragment.updateAdapter();
    }


    @Override
    public void onOpenImageGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GET_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == GET_IMAGE) {
            if (resultCode==RESULT_OK) {

                Uri imageUri = data.getData();
                String scheme = imageUri.getScheme();

                if (scheme.equals(ContentResolver.SCHEME_CONTENT)) {
                    try {
                        Gallery gallery = new Gallery(imageUri, FragmentItemsUtils.getCurrentTimeStamp());
                        parentFragment.updateAdapter(gallery);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            TabLayout.Tab tab = tabLayout.getTabAt(0);
            tab.select();
            viewPager.setCurrentItem(0);
            //adapter.getItem(0);
            resetAdapter();
        }
    }


    private static class BottomDependentBehavior extends CoordinatorLayout.Behavior {
        @Override
        public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
            if (dependency.getId() == R.id.bottom && dependency.getVisibility() != View.GONE) {
                return true;
            } else {
                return super.layoutDependsOn(parent, child, dependency);
            }
        }
    }

    private void setCoordinatorBehavior(View view, BottomDependentBehavior behavior) {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) view.getLayoutParams();
        params.setBehavior(behavior);
        view.setLayoutParams(params);
    }


    private void showRecordingBar() {
        if (mRecordingBar != null) {
            mRecordingBar.setVisibility(View.VISIBLE);
            if (mBottomBehavior != null) {
                mBottomBehavior.setPeekHeight(
                        mRecordingBar.getResources().getDimensionPixelSize(
                                R.dimen.panes_toolbar_height) + mRecordingBar.getResources().getDimensionPixelSize(R.dimen.recording_indicator_height));
            }
        }
    }

    private void hideRecordingBar() {
        if (mRecordingBar != null) {
            mRecordingBar.setVisibility(View.GONE);
            if (mBottomBehavior != null) {
                mBottomBehavior.setPeekHeight(mRecordingBar.getResources().getDimensionPixelSize(R.dimen.panes_toolbar_height));
            }
        }
    }
}
