package com.zrquan.mobile.ui.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.zrquan.mobile.R;
import com.zrquan.mobile.model.PostFeed;

import java.util.List;

/**
 * Created by James_Ouyang on 2015/2/25.
 */
public class PostFeedAdapter extends ArrayAdapter<PostFeed> {

    public PostFeedAdapter(Context context, List<PostFeed> data) {
        super(context, R.layout.card_item_post, data);
    }
}
