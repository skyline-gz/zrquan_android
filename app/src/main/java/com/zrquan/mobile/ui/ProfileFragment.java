package com.zrquan.mobile.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zrquan.mobile.R;
import com.zrquan.mobile.ui.demo.DemoSettingActivity;

public class ProfileFragment extends Fragment {

    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();
        View v = inflater.inflate(R.layout.fragment_profile, null);

        TextView tvBtnSetting = (TextView) v.findViewById(R.id.tv_btn_setting);
        tvBtnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence cs = "点击了设置按钮";
                Toast.makeText( context, cs, Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(getActivity(), DemoSettingActivity.class);
//                myIntent.putExtra("key", value); //Optional parameters
                getActivity().startActivity(myIntent);
            }
        });

        return v;
    }
}
