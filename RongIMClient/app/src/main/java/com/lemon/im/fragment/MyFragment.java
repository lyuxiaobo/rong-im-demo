package com.lemon.im.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lemon.im.R;
import com.lemon.im.activity.LoginActivity;
import com.lemon.im.base.BaseFragment;
import com.lemon.im.bean.LoginRequestBean;
import com.lemon.im.bean.LoginResultBean;
import com.lemon.im.utils.ActivityCollectorUtil;
import com.lemon.im.utils.GlideEngine;
import com.lemon.im.utils.GsonUtils;
import com.lemon.im.utils.SPUtils;
import com.lemon.im.utils.ToastUtils;
import com.lemon.im.utils.UrlFactory;
import com.leon.lib.settingview.LSettingItem;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

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
    private LoginResultBean.DataBean user;
    QMUITipDialog dialog;

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
        photo = view.findViewById(R.id.ivPhoto);
        tvShare = view.findViewById(R.id.tvShare);
    }

    @Override
    public void loadData() {
        user = SPUtils.getBean(mContext, "user", LoginResultBean.DataBean.class);
        tvName.setText(user.getName());
        Glide.with(mContext).load("https://img1.baidu.com/it/u=504609824,3604971623&fm=26&fmt=auto&gp=0.jpg").centerCrop().into(photo);
    }

    @Override
    public void initListener() {
        photo.setOnClickListener(v -> {
            PictureSelector.create(getActivity())
                    .openGallery(PictureMimeType.ofImage())
                    .imageEngine(GlideEngine.createGlideEngine()) // Please refer to the Demo GlideEngine.java
                    .forResult(PictureConfig.CHOOSE_REQUEST);
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
            dialog.setOnDismissListener(dialog1 -> {
                newName = builder.getEditText().getText().toString().trim();
                Log.i(TAG, "onDismiss: " + newName);
                if (newName.equals("")) {
                    return;
                }
                updateName();
            });
        });

        tvShare.setmOnLSettingItemClick(isChecked -> {
            final int TAG_SHARE_WECHAT_FRIEND = 0;
            final int TAG_SHARE_WECHAT_MOMENT = 1;
            final int TAG_SHARE_WEIBO = 2;
            final int TAG_SHARE_CHAT = 3;
            final int TAG_SHARE_LOCAL = 4;
            QMUIBottomSheet.BottomGridSheetBuilder builder = new QMUIBottomSheet.BottomGridSheetBuilder(getActivity());
            builder.addItem(R.mipmap.icon_more_operation_share_friend, "分享到微信", TAG_SHARE_WECHAT_FRIEND, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                    .addItem(R.mipmap.icon_more_operation_share_moment, "分享到朋友圈", TAG_SHARE_WECHAT_MOMENT, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                    .addItem(R.mipmap.icon_more_operation_share_weibo, "分享到微博", TAG_SHARE_WEIBO, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                    .addItem(R.mipmap.icon_more_operation_share_chat, "分享到私信", TAG_SHARE_CHAT, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                    .addItem(R.mipmap.icon_more_operation_save, "保存到本地", TAG_SHARE_LOCAL, QMUIBottomSheet.BottomGridSheetBuilder.SECOND_LINE)
                    .setAddCancelBtn(true)
                    .setSkinManager(QMUISkinManager.defaultInstance(getActivity()))
                    .setOnSheetItemClickListener((dialog, itemView) -> {
                        dialog.dismiss();
                        int tag = (int) itemView.getTag();
                        switch (tag) {
                            case TAG_SHARE_WECHAT_FRIEND:
                                Toast.makeText(getActivity(), "分享到微信", Toast.LENGTH_SHORT).show();
                                break;
                            case TAG_SHARE_WECHAT_MOMENT:
                                Toast.makeText(getActivity(), "分享到朋友圈", Toast.LENGTH_SHORT).show();
                                break;
                            case TAG_SHARE_WEIBO:
                                Toast.makeText(getActivity(), "分享到微博", Toast.LENGTH_SHORT).show();
                                break;
                            case TAG_SHARE_CHAT:
                                Toast.makeText(getActivity(), "分享到私信", Toast.LENGTH_SHORT).show();
                                break;
                            case TAG_SHARE_LOCAL:
                                Toast.makeText(getActivity(), "保存到本地", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }).build().show();
        });


    }

    private void updateName() {
        LoginRequestBean loginRequestBean = new LoginRequestBean();
        loginRequestBean.setUserId(user.getUserId());
        loginRequestBean.setName(newName);

        okPostRequest("update", UrlFactory.BaseUrl + "/user/update", GsonUtils.toJson(loginRequestBean), LoginResultBean.class, "正在……", true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // onResult Callback
                    List<LocalMedia> result = PictureSelector.obtainMultipleResult(data);
                    result.forEach(action -> {
                        Log.i(TAG, "onActivityResult: " + action.getPosition());
                    });
                    break;
                default:
                    break;
            }
        }

    }


    @Override
    protected void okResponseSuccess(String whit, Object t) {
        super.okResponseSuccess(whit, t);
        if (!TextUtils.equals(whit, "update")) {
            return;
        }
        LoginResultBean datas = (LoginResultBean) t;
        if (datas.getCode() == 200) {
            dialog = new QMUITipDialog.Builder(mContext).setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS).setTipWord("修改用户名成功").create();
            dialog.show();
            tvName.postDelayed(() -> {
                dialog.dismiss();
            }, 1000);
            SPUtils.saveBean(mContext, "user", datas.getData());
            tvName.setText(datas.getData().getName());
        } else {
            ToastUtils.showCenterToast(mContext, datas.getMsg());
        }
    }
}
