package com.andyshon.journal.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andyshon.journal.Models.Post;
import com.andyshon.journal.R;
import com.andyshon.journal.database.Database;

import java.util.List;

/**
 * Created by andyshon on 17.07.18.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    private Context mContext;
    private static List<Post> postList;

    private static PostAdapterCallback callback;

    private static Post post;


    public interface PostAdapterCallback{
        void NotifyAdapter();
    }


    public PostAdapter(Context c, List<Post> list) {
        mContext = c;
        postList = list;
        callback = (PostAdapterCallback) mContext;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_entry, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        post = postList.get(position);
        holder.title.setText(post.getName());

        holder.post_item.setOnClickListener(view -> {
            Toast.makeText(mContext, "go to post["+ postList.get(position).getId() + "] activity!", Toast.LENGTH_SHORT).show();
            //mListener.onPost(id);
            //make intent from activity
        });

        //Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), Uri.parse(post.getImage()));

        //System.out.println("BitmapUtils.decodeUri:" + BitmapUtils.decodeUri(Uri.parse(post.getImage()), activity));
        //holder.thumbnail.setImageBitmap(BitmapUtils.decodeUri(Uri.parse(post.getImage()), activity));

        if (post.getImage() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(post.getImage(), 0, post.getImage().length);
            holder.thumbnail.setImageBitmap(bitmap);
        }


        holder.tvOptionDigit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.tvOptionDigit, post.getId(), position, mContext);
            }
        });
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title, tvOptionDigit;
        private ImageView thumbnail;
        private RelativeLayout post_item;

        MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tvPostTitle);
            thumbnail = (ImageView) view.findViewById(R.id.imageCover);
            tvOptionDigit = (TextView) view.findViewById(R.id.tvOptionDigit);
            post_item = (RelativeLayout) view.findViewById(R.id.post_item);
        }
    }


    @Override
    public int getItemCount() {
        return postList.size();
    }



    /**
     * Showing popup menu when tapping on 3 dots
     */
    private static void showPopupMenu(View view, int post_id, int position, Context mContext) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_item, popup.getMenu());

        MenuItem item = popup.getMenu().findItem(R.id.action_share);
        item.setVisible(false);

        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(post_id, position, mContext));
        popup.show();
    }


    /**
     * Click listener for popup menu items
     */
    static class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        private Context mContext;
        private int post_id;
        private int position;

        MyMenuItemClickListener(int post_id, int position, Context context) {
            mContext = context;
            this.post_id = post_id;
            this.position = position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_delete:
                    Toast.makeText(mContext, "Delete", Toast.LENGTH_SHORT).show();
                    postList.remove(position);
                    Database database = new Database(mContext);
                    database.deletePost(post_id);
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
