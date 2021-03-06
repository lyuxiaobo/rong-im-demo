package com.lemon.im.fragment;


import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
import com.lemon.im.utils.UpdateAppHttpUtil;
import com.lemon.im.utils.UrlFactory;
import com.leon.lib.settingview.LSettingItem;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateCallback;
import com.vector.update_app.listener.ExceptionHandler;
import com.vector.update_app.listener.IUpdateDialogFragmentListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


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
            PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofImage())
                    .imageEngine(GlideEngine.createGlideEngine())
                    .isEnableCrop(true)
                    .maxSelectNum(1)
                    .forResult(new OnResultCallbackListener<LocalMedia>() {
                        @Override
                        public void onResult(List<LocalMedia> result) {
                            // onResult Callback
                            result.forEach(action -> {
                                Log.i(TAG, "onActivityResult: " + action.toString());
                            });
                        }

                        @Override
                        public void onCancel() {
                            // onCancel Callback
                        }
                    });

        });
        tvLogout.setmOnLSettingItemClick(isChecked -> {
            SPUtils.remove(mContext, "user");
            ActivityCollectorUtil.finishAllActivity();
            startActivity(new Intent(mContext, LoginActivity.class));
        });

        tvPersonalInfo.setmOnLSettingItemClick(isChecked -> {
            ToastUtils.showCenterToast(mContext, "?????????????????????");
        });
        tvSet.setmOnLSettingItemClick(isChecked -> {
            ToastUtils.showCenterToast(mContext, "?????????????????????");

        });
        tvVersionUpdate.setmOnLSettingItemClick(isChecked -> {
            //?????????????????????
            PackageManager pm = getActivity().getPackageManager();
            try {
                PackageInfo pi = pm.getPackageInfo(getActivity().getPackageName(), PackageManager.GET_ACTIVITIES);
                int versionCode = pi.versionCode;
                updateApp(versionCode);
            } catch (Exception e) {
                e.printStackTrace();
            } catch (NoSuchMethodError e) {
                e.printStackTrace();
            }

        });
        tvName.setOnClickListener(v ->

        {
            QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(getActivity())
                    .setPlaceholder("???????????????????????????")
                    .setTitle("???????????????")
                    .addAction("??????", new QMUIDialogAction.ActionListener() {
                        @Override
                        public void onClick(QMUIDialog dialog, int index) {
                            dialog.dismiss();
                        }
                    }).addAction("??????", (dialog, index) -> {
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

        tvShare.setmOnLSettingItemClick(isChecked ->

        {
            final int TAG_SHARE_WECHAT_FRIEND = 0;
            final int TAG_SHARE_WECHAT_MOMENT = 1;
            final int TAG_SHARE_WEIBO = 2;
            final int TAG_SHARE_CHAT = 3;
            final int TAG_SHARE_LOCAL = 4;
            QMUIBottomSheet.BottomGridSheetBuilder builder = new QMUIBottomSheet.BottomGridSheetBuilder(getActivity());
            builder.addItem(R.mipmap.icon_more_operation_share_friend, "???????????????", TAG_SHARE_WECHAT_FRIEND, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                    .addItem(R.mipmap.icon_more_operation_share_moment, "??????????????????", TAG_SHARE_WECHAT_MOMENT, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                    .addItem(R.mipmap.icon_more_operation_share_weibo, "???????????????", TAG_SHARE_WEIBO, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                    .addItem(R.mipmap.icon_more_operation_share_chat, "???????????????", TAG_SHARE_CHAT, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                    .addItem(R.mipmap.icon_more_operation_save, "???????????????", TAG_SHARE_LOCAL, QMUIBottomSheet.BottomGridSheetBuilder.SECOND_LINE)
                    .setAddCancelBtn(true)
                    .setSkinManager(QMUISkinManager.defaultInstance(getActivity()))
                    .setOnSheetItemClickListener((dialog, itemView) -> {
                        dialog.dismiss();
                        int tag = (int) itemView.getTag();
                        switch (tag) {
                            case TAG_SHARE_WECHAT_FRIEND:
                                Toast.makeText(getActivity(), "???????????????", Toast.LENGTH_SHORT).show();
                                break;
                            case TAG_SHARE_WECHAT_MOMENT:
                                Toast.makeText(getActivity(), "??????????????????", Toast.LENGTH_SHORT).show();
                                break;
                            case TAG_SHARE_WEIBO:
                                Toast.makeText(getActivity(), "???????????????", Toast.LENGTH_SHORT).show();
                                break;
                            case TAG_SHARE_CHAT:
                                Toast.makeText(getActivity(), "???????????????", Toast.LENGTH_SHORT).show();
                                break;
                            case TAG_SHARE_LOCAL:
                                Toast.makeText(getActivity(), "???????????????", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }).build().show();
        });


    }

    private void updateName() {
        LoginRequestBean loginRequestBean = new LoginRequestBean();
        loginRequestBean.setUserId(user.getUserId());
        loginRequestBean.setName(newName);

        okPostRequest("update", UrlFactory.BaseUrl + "/user/update", GsonUtils.toJson(loginRequestBean), LoginResultBean.class, "????????????", true);
    }


    @Override
    protected void okResponseSuccess(String whit, Object t) {
        super.okResponseSuccess(whit, t);
        if (!TextUtils.equals(whit, "update")) {
            return;
        }
        LoginResultBean datas = (LoginResultBean) t;
        if (datas.getCode() == 200) {
            dialog = new QMUITipDialog.Builder(mContext).setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS).setTipWord("?????????????????????").create();
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


    public void updateApp(int versionCode) {
        new UpdateAppManager
                .Builder()
                //?????????????????????Activity
                .setActivity(getActivity())
                //?????????????????????httpManager???????????????
                .setHttpManager(new UpdateAppHttpUtil())
                //???????????????????????????
                .setUpdateUrl(UrlFactory.BaseUrl + "/version/update?versionCode=" + versionCode)
                //??????????????????
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
                        e.printStackTrace();
                    }
                })
                //???????????????????????????
                //???????????????????????????get
                //???????????????????????????
//                .dismissNotificationProgress()
                //??????????????????
                .showIgnoreVersion()
                //???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
//                .hideDialogOnDownloading()
                //????????????????????????????????????
                .setThemeColor(R.color.primary)
                //??????apk????????????????????????????????????sd??????/Download/1.0.0/test.apk
//                .setTargetPath(path)
                //??????appKey????????????AndroidManifest.xml?????????????????????????????????????????????????????????
//                .setAppKey("ab55ce55Ac4bcP408cPb8c1Aaeac179c5f6f")
                .setUpdateDialogFragmentListener(new IUpdateDialogFragmentListener() {
                    @Override
                    public void onUpdateNotifyDialogCancel(UpdateAppBean updateApp) {
                        //?????????????????????????????????????????????????????????????????????????????????????????????????????? onActivityResult ????????????

                    }
                })
                //??????????????????
//                .setIgnoreDefParams(true)
                .build()
                //????????????????????????
                .checkNewApp(new UpdateCallback() {
                    /**
                     * ??????json,???????????????
                     *
                     * @param json ??????????????????json
                     * @return UpdateAppBean
                     */
                    @Override
                    protected UpdateAppBean parseJson(String json) {
                        Log.i(TAG, "parseJson: ");
                        UpdateAppBean updateAppBean = new UpdateAppBean();
                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            final String newVersion = jsonObject.optString("new_version");
                            updateAppBean
                                    //????????????????????????Yes,No
                                    .setUpdate(jsonObject.optString("update"))
                                    //???????????????????????????
                                    .setNewVersion(newVersion)
                                    //????????????????????????
                                    .setApkFileUrl(jsonObject.optString("apk_file_url"))
                                    //????????????????????????????????????
//                                    .setApkFileUrl("http://openbox.mobilem.360.cn/index/d/sid/3282847")
                                    .setUpdateDefDialogTitle(String.format("???????????????%s?????????", newVersion))
                                    //????????????????????????
                                    .setUpdateLog(jsonObject.optString("update_log"))
                                    //????????????????????????????????????
                                    .setConstraint(false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return updateAppBean;
                    }

                    @Override
                    protected void hasNewApp(UpdateAppBean updateApp, UpdateAppManager updateAppManager) {
                        updateAppManager.showDialogFragment();
                    }

                    /**
                     * ??????????????????
                     */
                    @Override
                    public void onBefore() {

                    }

                    /**
                     * ??????????????????
                     */
                    @Override
                    public void onAfter() {

                    }

                    /**
                     * ???????????????
                     */
                    @Override
                    public void noNewApp(String error) {
                        ToastUtils.showToast(getActivity(), "???????????????");
                    }
                });

    }

}
