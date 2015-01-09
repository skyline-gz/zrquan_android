package com.zrquan.mobile.ui.feed;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zrquan.mobile.R;
import com.zrquan.mobile.ui.demo.BottomMenuFragment;
import com.zrquan.mobile.ui.demo.SelectPicPopupWindow;

//问答 动态
public class QuestionFragment extends Fragment {

    private TextView tvTest;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_question, null);
        tvTest = (TextView) v.findViewById(R.id.tv_test);
        tvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog(inflater);
            }
        });
        return v;
    }

    public void createDialog(final LayoutInflater inflater) {
//        startActivity(new Intent(getActivity(), SelectPicPopupWindow.class));
//        BottomMenuFragment df = new BottomMenuFragment();
//        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
//        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        df.show(ft, "df");

        View view = inflater.inflate(R.layout.dialog, null);
        AlertDialog infoDialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();

        infoDialog.getWindow().getAttributes().windowAnimations = R.style.AnimBottom;

        infoDialog.getWindow().setGravity(Gravity.BOTTOM);
        infoDialog.show();

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
