package com.andyshon.journal.Adapter;

/**
 * Created by andyshon on 16.07.18.
 */

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.andyshon.journal.Fragments.GalleryFragment;
import com.andyshon.journal.Fragments.NoteFragment;


public class PagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;

    private PagerAdapterCallback callback;

    public interface PagerAdapterCallback {
        void onGetCurrentFragment(Fragment currentFragment);
    }


    public PagerAdapter(Context context, FragmentManager fm, int NumOfTabs) {
        super(fm);
        callback = (PagerAdapterCallback) context;
        this.mNumOfTabs = NumOfTabs;
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                System.out.println("CASE 0");
                NoteFragment noteFragment = new NoteFragment();
                callback.onGetCurrentFragment(noteFragment);
                return noteFragment;
            case 1:
                System.out.println("CASE 1");
                GalleryFragment galleryFragment = new GalleryFragment();
                callback.onGetCurrentFragment(galleryFragment);
                return galleryFragment;
            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}