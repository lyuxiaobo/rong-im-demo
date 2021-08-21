package com.lemon.im.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lemon.im.R;
import com.lemon.im.base.BaseCacheFragment;

/**
 * @author lyubo
 * @date 2021/8/20
 */
public class MyConversationFragment extends BaseCacheFragment {

    @Override
    public void initView(View view) {

        isTitleBar(true, view.findViewById(R.id.conversation_title));
        findViewById(R.id.tvTitleBack).setVisibility(View.INVISIBLE);
        ((TextView) findViewById(R.id.tvTitleBar)).setText("聊天");

    }

    @Override
    public void initListener() {

    }

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_conversation, container, false);
    }

    @Override
    public void loadData() {

    }
}
