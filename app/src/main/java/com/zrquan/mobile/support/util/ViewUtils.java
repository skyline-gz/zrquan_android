package com.zrquan.mobile.support.util;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class ViewUtils {

    //http://stackoverflow.com/questions/24811536/android-listview-get-item-view-by-position
    public static View getViewByPosition(int pos, ListView listView) {
        int firstListItemPosition = listView.getFirstVisiblePosition();
        int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    public static boolean checkTextEmpty(TextView view) {
        return view.getText().toString().trim().length() == 0;
    }
}
