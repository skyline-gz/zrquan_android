package com.zrquan.mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zrquan.mobile.R;
import com.zrquan.mobile.model.PostFeed;

import java.util.List;

/**
 * Created by James_Ouyang on 2015/2/25.
 */
public class PostFeedAdapter extends ArrayAdapter<PostFeed> {

    private LayoutInflater inflater;

    public PostFeedAdapter(Context context, List<PostFeed> data) {
        super(context, R.layout.card_item_post, data);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.card_item_post, null, false);
            holder = new ViewHolder();
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
        holder.user = (TextView) convertView.findViewById(R.id.user);
        holder.time = (TextView) convertView.findViewById(R.id.time);
        holder.theme = (TextView) convertView.findViewById(R.id.theme);
        holder.content = (TextView) convertView.findViewById(R.id.content);

        return convertView;
    }

    public static class ViewHolder {
        ImageView avatar;
        TextView user;
        TextView time;
        TextView theme;
        TextView content;
    }
}
