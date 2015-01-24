package com.zrquan.mobile.ui.feed;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.zrquan.mobile.R;
import com.zrquan.mobile.widget.switchbutton.SwitchButton;

//问答 动态
public class QuestionFragment extends Fragment {

    private TextView tvTest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_question, null);
        SwitchButton sbDefault = (SwitchButton) v.findViewById(R.id.sb_default);

        sbDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(getActivity(), "Default style button, new state: " + (isChecked ? "on" : "off"), Toast.LENGTH_SHORT).show();
            }
        });

        tvTest = (TextView) v.findViewById(R.id.tv_test);
        tvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog(v);
            }
        });
        return v;
    }

    public void createDialog(View v) {
//        startActivity(new Intent(getActivity(), SelectPicPopupWindow.class));
//        BottomMenuFragment df = new BottomMenuFragment();
//        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
//        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        df.show(ft, "df");

        final Dialog infoDialog = new Dialog(getActivity(), R.style.BottomMenuDialogTheme2);
        infoDialog.setContentView(R.layout.select_pic_dialog2);
//        infoDialog.show();

//        AlertDialog infoDialog = new AlertDialog.Builder(getActivity(), R.style.BottomMenuDialogTheme2)
//                .setView(view)
//                .create();
//        infoDialog.getWindow().setBackgroundDrawableResource(R.drawable.btn_style_alert_dialog_background);
//        infoDialog.getWindow().getDecorView().getRootView().setBackgroundColor(Color.TRANSPARENT);
        infoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        infoDialog.getWindow().getAttributes().windowAnimations = R.style.AnimBottom;
        infoDialog.getWindow().setGravity(Gravity.BOTTOM);
        infoDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        infoDialog.show();

//        RelativeLayout layout=(RelativeLayout) infoDialog.findViewById(R.id.pop_layout);

//        添加选择窗口范围监听可以优先获取触点，即不再执行onTouchEvent()函数，点击其他地方时执行onTouchEvent()函数销毁Activity
//        layout.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                infoDialog.dismiss();
//                Toast.makeText(getActivity().getApplicationContext(), "提示：点击窗口外部关闭窗口！",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });

//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setMessage("Are you sure you want to exit?")
//                .setCancelable(false)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
////                        MyActivity.this.finish();
//                    }
//                })
//                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });
//        builder.show();
    }
}
