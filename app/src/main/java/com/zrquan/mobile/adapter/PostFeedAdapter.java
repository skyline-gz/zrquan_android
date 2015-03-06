package com.zrquan.mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.zrquan.mobile.R;
import com.zrquan.mobile.model.PostFeed;
import com.zrquan.mobile.support.volley.cache.BitmapLruCache;

import java.util.List;

/**
 * Created by James_Ouyang on 2015/2/25.
 */
public class PostFeedAdapter extends ArrayAdapter<PostFeed> {

    private LayoutInflater inflater;
    private ImageLoader imageLoader;

    public PostFeedAdapter(Context context, List<PostFeed> data, RequestQueue queue) {
        super(context, R.layout.card_item_post, data);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(queue, new BitmapLruCache());
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.card_item_post, null, false);
            holder = buildHolder(convertView);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        PostFeed item = getItem(position);

        holder.avatar.setImageUrl("http://zrquan.qiniudn.com/uploads/avartars/4/bb02460b87b65f3c4c95b9c8e72d0e2c20150213-18962-hih4tz.png", imageLoader);
        holder.avatar.setDefaultImageResId(R.drawable.avatar_default);
        holder.avatar.setErrorImageResId(R.drawable.avatar_default);
        holder.user.setText(item.getPostUserName());
//        holder.time.setText(item.getPostCreatedAt().toString());
        holder.theme.setText(item.getThemeName());
        holder.content.setText(item.getPostContent());

        return convertView;
    }

    private ViewHolder buildHolder(View convertView) {
        ViewHolder holder = new ViewHolder();
        holder.avatar = (NetworkImageView) convertView.findViewById(R.id.avatar);
        holder.user = (TextView) convertView.findViewById(R.id.user);
        holder.time = (TextView) convertView.findViewById(R.id.time);
        holder.theme = (TextView) convertView.findViewById(R.id.theme);
        holder.content = (TextView) convertView.findViewById(R.id.content);
        return holder;
    }

    public static class ViewHolder {
        NetworkImageView avatar;
        TextView user;
        TextView time;
        TextView theme;
        TextView content;
    }
}
