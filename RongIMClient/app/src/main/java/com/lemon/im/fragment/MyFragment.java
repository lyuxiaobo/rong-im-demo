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
            ToastUtils.showCenterToast(mContext, "正在开发中……");
        });
        tvSet.setmOnLSettingItemClick(isChecked -> {
            ToastUtils.showCenterToast(mContext, "正在开发中……");

        });
        tvVersionUpdate.setmOnLSettingItemClick(isChecked -> {
            updateDiy();
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

    /**
     * 自定义接口协议
     *
     * @param view
     */
    public void updateDiy() {


        new UpdateAppManager
                .Builder()
                //必须设置，当前Activity
                .setActivity(getActivity())
                //必须设置，实现httpManager接口的对象
                .setHttpManager(new UpdateAppHttpUtil())
                //必须设置，更新地址
                .setUpdateUrl(UrlFactory.BaseUrl + "/version/update")
                //全局异常捕获
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
                        e.printStackTrace();
                    }
                })
                //以下设置，都是可选
                //设置请求方式，默认get
                //不显示通知栏进度条
//                .dismissNotificationProgress()
                //是否忽略版本
                .showIgnoreVersion()
                //设置点击升级后，消失对话框，默认点击升级后，对话框显示下载进度，如果是强制更新，则设置无效
//                .hideDialogOnDownloading()
                //为按钮，进度条设置颜色。
                .setThemeColor(R.color.primary)
                //设置apk下砸路径，默认是在下载到sd卡下/Download/1.0.0/test.apk
//                .setTargetPath(path)
                //设置appKey，默认从AndroidManifest.xml获取，如果，使用自定义参数，则此项无效
//                .setAppKey("ab55ce55Ac4bcP408cPb8c1Aaeac179c5f6f")
                .setUpdateDialogFragmentListener(new IUpdateDialogFragmentListener() {
                    @Override
                    public void onUpdateNotifyDialogCancel(UpdateAppBean updateApp) {
                        //用户点击关闭按钮，取消了更新，如果是下载完，用户取消了安装，则可以在 onActivityResult 监听到。

                    }
                })
                //不自动，获取
//                .setIgnoreDefParams(true)
                .build()
                //检测是否有新版本
                .checkNewApp(new UpdateCallback() {
                    /**
                     * 解析json,自定义协议
                     *
                     * @param json 服务器返回的json
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
                                    //（必须）是否更新Yes,No
                                    .setUpdate(jsonObject.optString("update"))
                                    //（必须）新版本号，
                                    .setNewVersion(newVersion)
                                    //（必须）下载地址
                                    .setApkFileUrl(jsonObject.optString("apk_file_url"))
                                    //测试下载路径是重定向路径
//                                    .setApkFileUrl("http://openbox.mobilem.360.cn/index/d/sid/3282847")
                                    .setUpdateDefDialogTitle(String.format("是否升级到%s版本？", newVersion))
                                    //（必须）更新内容
                                    .setUpdateLog(jsonObject.optString("update_log"))
                                    //是否强制更新，可以不设置
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
                     * 网络请求之前
                     */
                    @Override
                    public void onBefore() {

                    }

                    /**
                     * 网路请求之后
                     */
                    @Override
                    public void onAfter() {

                    }

                    /**
                     * 没有新版本
                     */
                    @Override
                    public void noNewApp(String error) {
                        ToastUtils.showToast(getActivity(), "没有新版本");
                    }
                });

    }

}
