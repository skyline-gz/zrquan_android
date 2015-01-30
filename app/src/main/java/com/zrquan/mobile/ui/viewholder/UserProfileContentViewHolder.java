package com.zrquan.mobile.ui.viewholder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import com.zrquan.mobile.ui.common.CommonFragment;
import com.zrquan.mobile.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserProfileContentViewHolder {

    private View mVisitorContentView;
    private CommonFragment context;

    public UserProfileContentViewHolder(CommonFragment context, View view) {
        ButterKnife.inject(this, view);
        mVisitorContentView = view;
        this.context = context;
    }

    @OnClick(R.id.riv_user_avatar)
    public void onUserAvatarClick() {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(context.getActivity());
        DialogInterface.OnClickListener local1 = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                switch (paramAnonymousInt) {
                    case 0:
                        Intent localIntent2 = new Intent("cn.zrquan.mobile.action.GET_PICTURE");
                        localIntent2.putExtra("source", 1);
                        localIntent2.putExtra("width", 100);
                        localIntent2.putExtra("height", 100);
                        localIntent2.putExtra("crop", true);
                        context.getActivity().startActivityForResult(localIntent2, 1);
                        break;
                    case 1:
                        Intent localIntent1 = new Intent("cn.zrquan.mobile.action.GET_PICTURE");
                        localIntent1.putExtra("source", 2);
                        localIntent1.putExtra("width", 100);
                        localIntent1.putExtra("height", 100);
                        localIntent1.putExtra("crop", true);
                        context.getActivity().startActivityForResult(localIntent1, 1);
                        break;
                    default:
                        paramAnonymousDialogInterface.dismiss();
                }
            }
        };
        localBuilder.setItems(new String[]{"拍照", "从相册中选择"}, local1);
        localBuilder.show();
    }
}
