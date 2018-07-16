package com.andyshon.journal.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andyshon.journal.R;


public class GalleryFragment extends Fragment {

    private ImageCallback mImageCallback;

    public interface ImageCallback {
        void onOpenImageGallery();
    }


    public GalleryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);


        mImageCallback.onOpenImageGallery();

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof ImageCallback) {
            mImageCallback = (ImageCallback) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement ImageCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
