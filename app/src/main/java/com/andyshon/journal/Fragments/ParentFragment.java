package com.andyshon.journal.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andyshon.journal.Activity.PanesBottomSheetBehavior;
import com.andyshon.journal.Adapter.ItemAdapter;
import com.andyshon.journal.FragmentItems.ListFragments;
import com.andyshon.journal.R;

import java.util.ArrayList;
import java.util.List;


public class ParentFragment extends Fragment {

    private ItemAdapter postAdapter;
    private List<ListFragments> listFragments;
    private static PanesBottomSheetBehavior mBottomBehavior;

    private TextView tvNoPost;


    public ParentFragment(){}

    public static ParentFragment newInstance(PanesBottomSheetBehavior mb) {
        mBottomBehavior = mb;
        Bundle bundle = new Bundle();
        ParentFragment parentFragment = new ParentFragment();
        parentFragment.setArguments(bundle);
        return parentFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_parent, container, false);

        tvNoPost = view.findViewById(R.id.tvNoPost);

        listFragments = new ArrayList<>();

        postAdapter = new ItemAdapter(getActivity(), listFragments);

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(postAdapter);


        showTitleIfNoItems();

        return view;
    }


    private void showTitleIfNoItems() {
        if (listFragments.size() == 0 || listFragments.isEmpty()) {
            showNoPostTitle();
        }
        else {
            hideNoPostTitle();
        }
    }


    private void showNoPostTitle() {
        tvNoPost.setVisibility(View.VISIBLE);
        mBottomBehavior.setState(PanesBottomSheetBehavior.STATE_MIDDLE);
    }


    private void hideNoPostTitle() {
        tvNoPost.setVisibility(View.GONE);
    }


    public void updateAdapter(ListFragments item) {
        listFragments.add(item);
        postAdapter.notifyDataSetChanged();
    }


    public void updateAdapter() {
        showTitleIfNoItems();
        postAdapter.notifyDataSetChanged();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }
}
