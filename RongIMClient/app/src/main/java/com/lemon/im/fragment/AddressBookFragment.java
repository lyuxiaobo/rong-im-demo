package com.lemon.im.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.JsonSyntaxException;
import com.lemon.im.R;
import com.lemon.im.base.BaseCacheFragment;
import com.lemon.im.bean.AllUserResultBean;
import com.lemon.im.utils.GsonUtils;
import com.lemon.im.utils.UrlFactory;

import io.rong.imkit.utils.RouteUtils;
import io.rong.imlib.model.Conversation;


/**
 * Ccb simple {@link } subclass.
 */
public class AddressBookFragment extends BaseCacheFragment {
    private static final String TAG = "TaskTodoFragment";
    private TasksAdapter tasksAdapter;
    private RecyclerView recyclerView;
    private int start = 0;
    private int size = 10;

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_address_book, container, false);
    }

    @Override
    public void initView(View view) {
        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        tasksAdapter = new TasksAdapter(R.layout.address_book_item);
        recyclerView.setAdapter(tasksAdapter);

        isTitleBar(true, view.findViewById(R.id.address_book_title));
        findViewById(R.id.tvTitleBack).setVisibility(View.INVISIBLE);
        ((TextView) findViewById(R.id.tvTitleBar)).setText("通讯录");


        TextView tv = new TextView(getActivity());
        tv.setGravity(Gravity.CENTER);
        tv.setText("暂无数据!");
        // 没有数据的时候默认显示该布局
        tasksAdapter.setEmptyView(tv);

        tasksAdapter.setOnItemClickListener((adapter, view1, position) -> {
            Conversation.ConversationType conversationType = Conversation.ConversationType.PRIVATE;
            String targetId = tasksAdapter.getData().get(position).getUserId();
            String title = tasksAdapter.getData().get(position).getUserId();
            Bundle bundle = new Bundle();
            if (!TextUtils.isEmpty(title)) {
                bundle.putString(RouteUtils.TITLE, title); //会话页面标题
//                bundle.putLong(RouteUtils.INDEX_MESSAGE_TIME, 0L); //打开会话页面时的默认跳转位置，如果不配置将跳转到消息列表底部
            }
            RouteUtils.routeToConversationActivity(getContext(), conversationType, targetId, bundle);

        });
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        recyclerView.scrollToPosition(0);
    }

    @Override
    public void loadData() {
        loadHttpData();
    }

    public void loadHttpData() {
        okGetRequest("getUser", UrlFactory.BaseUrl + "/user/all");
    }

    @Override
    public void initListener() {
        /*tasksAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadHttpData();
            }
        }, recyclerView);*/
    }

    @Override
    protected void okResponseStart(String flag) {
        super.okResponseStart(flag);
    }

    @Override
    protected void okResponseError(String whit, String body) {
        super.okResponseError(whit, body);
        TextView tv = new TextView(getActivity());
        tv.setGravity(Gravity.CENTER);
        tv.setText("加载失败!");
        tasksAdapter.setEmptyView(tv);
    }

    @Override
    protected void okResponseSuccess(String whit, Object t) {
        super.okResponseSuccess(whit, t);
        if (TextUtils.equals(whit, "getUser")) {

            String json = String.valueOf(t);
            AllUserResultBean datas = null;
            try {
                datas = GsonUtils.fromJson(json, AllUserResultBean.class);
            } catch (JsonSyntaxException e) {
                e.fillInStackTrace();
            }
            if (datas == null) {
                return;
            }
            tasksAdapter.addData(datas.getData());
        }
    }

    @Override
    protected void okResponseFinish(String flag) {
        super.okResponseFinish(flag);
        tasksAdapter.loadMoreComplete();
    }


    class TasksAdapter extends BaseQuickAdapter<AllUserResultBean.DataBean, BaseViewHolder> {

        public TasksAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, AllUserResultBean.DataBean item) {
            helper.setText(R.id.txtName, item.getUserId())
                    .setText(R.id.message_tv_itemHosp, "创建时间：" + item.getCreateTime());
            Glide.with(mContext).load("http://www.rongcloud.cn/images/logo.png").centerCrop().into((ImageView) helper.getView(R.id.pic));
        }
    }

}
