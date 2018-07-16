package com.andyshon.journal.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.andyshon.journal.FragmentItems.Comment;
import com.andyshon.journal.FragmentItems.Gallery;
import com.andyshon.journal.FragmentItems.Note;
import com.andyshon.journal.FragmentItems.ListFragments;
import com.andyshon.journal.R;
import com.andyshon.journal.Utils.BitmapUtils;

import java.util.List;

/**
 * Created by andyshon on 15.07.18.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private static Context mContext;
    private static List<ListFragments> commentList;

    private Activity activity;

    static Callback callback;

    public interface Callback{
        void NotifyAdapter();
    }


    public ItemAdapter(Context c, List<ListFragments> list) {
        mContext = c;
        commentList = list;
        callback = (Callback)mContext;
        this.activity = (Activity) mContext;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {

            case ListFragments.TYPE_Note:
                System.out.println("TYPE - Note");
                view = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.note_entry, parent, false);
                return new ViewHolderNote(view);

            case ListFragments.TYPE_B:
                System.out.println("TYPE - B");
                view = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.comment_entry, parent, false);
                return new ViewHolderB(view);

            case ListFragments.TYPE_GALLERY:
                System.out.println("TYPE - Gallery");
                view = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.image_entry, parent, false);
                return new ViewHolderGallery(view);
        }


        return null;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ListFragments listFragments = commentList.get(position);
        System.out.println("listFragments::: " + listFragments.getListFragmentType());
        holder.bindType(listFragments);
    }

    @Override
    public int getItemViewType(int position) {
        return commentList.get(position).getListFragmentType();
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }


    public abstract class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void bindType(ListFragments item);
    }


    public class ViewHolderNote extends ViewHolder {
        private final TextView mTextView, tvDate, tvOptions;

        ViewHolderNote(View itemView) {
            super(itemView);

            mTextView = (TextView) itemView.findViewById(R.id.tvText);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvOptions = (TextView) itemView.findViewById(R.id.tvOptionDigit);
        }

        @Override
        public void bindType(ListFragments item) {
            mTextView.setText(((Note) item).getName());
            tvDate.setText(((Note)item).getTimestamp());

            if (tvOptions != null) {
                tvOptions.setOnClickListener(view -> showPopupMenu(tvOptions, getAdapterPosition()));
            }
        }
    }


    public class ViewHolderB extends ViewHolder {
        private final TextView mTextView, tvOptions;

        ViewHolderB(View itemView) {
            super(itemView);

            mTextView = (TextView) itemView.findViewById(R.id.tvText);
            tvOptions = (TextView) itemView.findViewById(R.id.tvOptionDigit);
        }

        @Override
        public void bindType(ListFragments item) {
            mTextView.setText(((Comment) item).getName());


            if (tvOptions != null) {
                tvOptions.setOnClickListener(view -> showPopupMenu(tvOptions, getAdapterPosition()));
            }
        }
    }


    public class ViewHolderGallery extends ViewHolder {
        private final ImageView imageView;
        private final TextView tvDate, tvOptions;

        ViewHolderGallery(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.ivImage);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvOptions = (TextView) itemView.findViewById(R.id.tvOptionDigit);
        }

        @Override
        public void bindType(ListFragments item) {
            tvDate.setText(((Gallery)item).getTimestamp());
            imageView.setImageBitmap(BitmapUtils.decodeUri(((Gallery)item).getImageUri(), activity));


            if (tvOptions != null) {
                tvOptions.setOnClickListener(view -> showPopupMenu(tvOptions, getAdapterPosition()));
            }
        }
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private static void showPopupMenu(View view, int pos) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_item, popup.getMenu());

        MenuItem item = popup.getMenu().findItem(R.id.action_delete);
        item.setVisible(isFullMenu(pos));

        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(pos));
        popup.show();
    }

    private static boolean isFullMenu (int pos) {
        ListFragments item = commentList.get(pos);
        if (item instanceof Note)
            return true;
        else if (item instanceof Comment)
            return false;
        else if (item instanceof Gallery)
            return true;
        return false;
    }

    /**
     * Click listener for popup menu items
     */
    static class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        private int itemPosition;

        MyMenuItemClickListener(int p) {
            itemPosition = p;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_delete:
                    Toast.makeText(mContext, "Delete", Toast.LENGTH_SHORT).show();
                    commentList.remove(itemPosition);
                    callback.NotifyAdapter();
                    return true;
                case R.id.action_share:
                    Toast.makeText(mContext, "Share", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

}
