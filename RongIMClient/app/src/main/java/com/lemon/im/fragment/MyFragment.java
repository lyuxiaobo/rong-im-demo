package com.lemon.im.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lemon.im.R;
import com.lemon.im.activity.LoginActivity;
import com.lemon.im.base.BaseFragment;
import com.lemon.im.bean.LoginResultBean;
import com.lemon.im.utils.ActivityCollectorUtil;
import com.lemon.im.utils.SPUtils;
import com.lemon.im.utils.ToastUtils;
import com.leon.lib.settingview.LSettingItem;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * CCB simple {@link } subclass.
 */
public class MyFragment extends BaseFragment {
    private static final String TAG = "MyFragment";
    private LSettingItem tvLogout, tvPersonalInfo, tvSet, tvVersionUpdate, tvShare;
    private TextView tvName;
    private ImageView photo;
    private String newName;
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
        photo= view.findViewById(R.id.ivPhoto);
//        tvShare= view.findViewById(R.id.tvShare);
    }

    @Override
    public void loadData() {
        LoginResultBean.DataBean user = SPUtils.getBean(mContext, "user", LoginResultBean.DataBean.class);
        tvName.setText(user.getName());
        Glide.with(mContext).load("https://img1.baidu.com/it/u=504609824,3604971623&fm=26&fmt=auto&gp=0.jpg").centerCrop().into(photo);
    }

    @Override
    public void initListener() {
        photo.setOnClickListener(v -> {
                /*PictureSelector.create(getActivity())
                        .openGallery(PictureMimeType.ofImage())
                        .imageEngine(GlideEngine.createGlideEngine()) // Please refer to the Demo GlideEngine.java
                        .forResult(PictureConfig.CHOOSE_REQUEST);*/
        });
        tvLogout.setmOnLSettingItemClick(isChecked -> {
            SPUtils.remove(mContext, "user");
            ActivityCollectorUtil.finishAllActivity();
            startActivity(new Intent(mContext, LoginActivity.class));
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
        tvName.setOnClickListener(v -> {

            QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(getActivity())
                    .setPlaceholder("请输入您的新用户名")
                    .setTitle("更改用户名")
                    .addAction("取消", new QMUIDialogAction.ActionListener() {
                        @Override
                        public void onClick(QMUIDialog dialog, int index) {
                            dialog.dismiss();
                        }
                    }).addAction("确定", (dialog, index) -> {
                        dialog.dismiss();
                    });

            QMUIDialog dialog = builder.create();

            dialog.show();
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    newName = builder.getEditText().getText().toString().trim();
                    Log.i(TAG, "onDismiss: " + newName);
                }
            });
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // onResult Callback
                    List<LocalMedia> result = PictureSelector.obtainMultipleResult(data);
                    break;
                default:
                    break;
            }
        }

    }
}
