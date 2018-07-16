package com.andyshon.journal.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.andyshon.journal.FragmentItems.Note;
import com.andyshon.journal.Utils.FragmentItemsUtils;
import com.andyshon.journal.R;


public class NoteFragment extends Fragment {

    private EditText etTextField;
    private CommentCallback mCommentCallback;

    public interface CommentCallback {
        void onGetNote(Note note);
    }


    public NoteFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note, container, false);

        etTextField = view.findViewById(R.id.etTextField);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CommentCallback) {
            mCommentCallback = (CommentCallback) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement CommentCallback");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


    public void requestForNote() {
        Note note = new Note(etTextField.getText().toString().trim(), FragmentItemsUtils.getCurrentTimeStamp());
        mCommentCallback.onGetNote(note);
    }

}
