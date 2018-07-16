package com.andyshon.journal.FragmentItems;

/**
 * Created by andyshon on 15.07.18.
 */

public class Comment implements ListFragments {
    private String name;

    public Comment(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    @Override
    public int getListFragmentType() {
        return ListFragments.TYPE_B;
    }
}
