package com.zrquan.mobile.ui.viewholder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.zrquan.mobile.ui.GetPictureActivity;
import com.zrquan.mobile.ui.common.CommonFragment;
import com.zrquan.mobile.R;
import com.zrquan.mobile.widget.scrollview.PullScrollView;
import com.zrquan.mobile.widget.view.RoundedImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class UserProfileContentViewHolder {

    private CommonFragment context;

    @InjectView(R.id.scroll_view)
    PullScrollView mScrollView;
    @InjectView(R.id.iv_header_image)
    ImageView mHeadImg;
    @InjectView(R.id.riv_user_avatar)
    RoundedImageView riv_user_avatar;
    @InjectView(R.id.table_layout)
    TableLayout mMainLayout;

    public UserProfileContentViewHolder(CommonFragment context, View view) {
        ButterKnife.inject(this, view);
        this.context = context;
        init();
    }

    @OnClick(R.id.riv_user_avatar)
    public void onUserAvatarClick() {
        AlertDialog.Builder selectAvatarBuilder = new AlertDialog.Builder(context.getActivity());
        DialogInterface.OnClickListener local1 = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                switch (paramAnonymousInt) {
                    case 0:
                        Intent intent1 = new Intent("cn.zrquan.mobile.action.GET_PICTURE");
                        intent1.putExtra("source", GetPictureActivity.REQUEST_CODE_CAMERA);
                        intent1.putExtra("width", 100);
                        intent1.putExtra("height", 100);
                        intent1.putExtra("crop", true);
                        context.startActivity(intent1);
                        break;
                    case 1:
                        Intent intent2 = new Intent("cn.zrquan.mobile.action.GET_PICTURE");
                        intent2.putExtra("source", GetPictureActivity.REQUEST_CODE_GALLERY);
                        intent2.putExtra("width", 100);
                        intent2.putExtra("height", 100);
                        intent2.putExtra("crop", true);
                        context.startActivity(intent2);
                        break;
                    default:
                        paramAnonymousDialogInterface.dismiss();
                }
            }
        };
        selectAvatarBuilder.setItems(new String[]{"拍照", "从相册中选择"}, local1);
        AlertDialog selectAvatarDialog = selectAvatarBuilder.create();
        selectAvatarDialog.setCanceledOnTouchOutside(true);
        selectAvatarDialog.show();
    }

    public void reloadAvatar(Bitmap bitmap) {
//        riv_user_avatar.setImageBitmap(BitmapFactory.decodeFile(filePath, null));
        riv_user_avatar.setImageBitmap(bitmap);
    }


    private void init() {
        mScrollView.setHeader(mHeadImg);
        showTable();
    }

    private void showTable() {
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.leftMargin = 30;
        layoutParams.bottomMargin = 10;
        layoutParams.topMargin = 10;

        for (int i = 0; i < 30; i++) {
            TableRow tableRow = new TableRow(context.getActivity());
            TextView textView = new TextView(context.getActivity());
            textView.setText("Test pull down scroll view " + i);
            textView.setTextSize(20);
            textView.setPadding(15, 15, 15, 15);

            tableRow.addView(textView, layoutParams);
            if (i % 2 != 0) {
                tableRow.setBackgroundColor(Color.LTGRAY);
            } else {
                tableRow.setBackgroundColor(Color.WHITE);
            }

            final int n = i;
            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context.getActivity(), "Click item " + n, Toast.LENGTH_SHORT).show();
                }
            });

            mMainLayout.addView(tableRow);
        }
    }
}
