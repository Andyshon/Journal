package com.andyshon.journal.FragmentItems;

/**
 * Created by andyshon on 15.07.18.
 */

public class Note implements ListFragments {
    private String name;
    private String timestamp;

    public Note(String name, String timestamp) {
        this.name = name;
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public int getListFragmentType() {
        return ListFragments.TYPE_Note;
    }
}
