package com.andyshon.journal.FragmentItems;

import android.net.Uri;

/**
 * Created by andyshon on 15.07.18.
 */

public class Gallery implements ListFragments {
    private Uri imageUri;
    private String timestamp;

    public Gallery(Uri imageUri, String timestamp) {
        this.imageUri = imageUri;
        this.timestamp = timestamp;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public int getListFragmentType() {
        return ListFragments.TYPE_GALLERY;
    }
}
