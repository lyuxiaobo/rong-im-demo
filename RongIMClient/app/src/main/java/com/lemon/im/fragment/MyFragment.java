package com.lemon.im.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lemon.im.R;
import com.lemon.im.base.BaseFragment;
import com.lemon.im.utils.ActivityCollectorUtil;
import com.lemon.im.utils.SPUtils;
import com.lemon.im.utils.ToastUtils;
import com.leon.lib.settingview.LSettingItem;


/**
 * CCB simple {@link } subclass.
 */
public class MyFragment extends BaseFragment {
    private static final String TAG = "MyFragment";
    private LSettingItem tvLogout, tvPersonalInfo, tvSet, tvVersionUpdate;
    private TextView tvName;

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my, container, false);
    }

    @Override
    public void initView(View view) {
        tvLogout = view.findViewById(R.id.tvLogout);
        tvPersonalInfo = view.findViewById(R.id.tvPersonalInfo);
        tvName = view.findViewById(R.id.tvName);
        tvVersionUpdate = view.findViewById(R.id.tvVersionUpdate);
        tvSet = view.findViewById(R.id.tvSet);
    }

    @Override
    public void loadData() {
        tvName.setText(SPUtils.get(mContext, "username", "").toString());
    }

    @Override
    public void initListener() {
        tvLogout.setmOnLSettingItemClick(isChecked -> {
            SPUtils.remove(mContext, "username");
            ActivityCollectorUtil.finishAllActivity();
//            startActivity(new Intent(mContext, LoginActivity.class));
        });

        tvPersonalInfo.setmOnLSettingItemClick(isChecked -> {
            ToastUtils.showCenterToast(mContext, "正在开发中……");
        });
        tvSet.setmOnLSettingItemClick(isChecked -> {
            ToastUtils.showCenterToast(mContext, "正在开发中……");

        });
        tvVersionUpdate.setmOnLSettingItemClick(isChecked -> {
            ToastUtils.showCenterToast(mContext, "正在开发中……");

        });
    }


}
