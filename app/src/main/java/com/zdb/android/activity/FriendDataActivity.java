package com.zdb.android.activity;

import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.utils.HttpUtils.HTTP_MOTHED;
import com.android.utils.JException;
import com.android.utils.ValidateUtils;
import com.jsd.android.view.ViewUtils;
import com.jsd.android.view.annotation.ViewInject;
import com.zdb.android.R;
import com.zdb.android.adapter.TopicAdapter;
import com.zdb.android.base.BaseActivity;
import com.zdb.android.bean.TopicBean;
import com.zdb.android.bean.TopicBean.Topic;
import com.zdb.android.utils.Constant;
import com.zdb.android.view.refresh.ZrcListView;
import com.zdb.android.view.refresh.ZrcListView.OnItemClickListener;

public class FriendDataActivity extends BaseActivity implements
		OnItemClickListener, OnClickListener {

	@ViewInject(R.id.topicList)
	private ZrcListView topicList;

	private TopicAdapter mTopicAdapter;

	private int pageId = 0;
	private int nums = 10;

	private LinearLayout topicNumL, topicImgArr;
	private ImageView topicImg, topicBack, topicHead, topicChat, topicVideo,
			topicNumImg;
	private TextView topicName, topicFans, topicSign, topicTime, topicNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_friend_data);
		ViewUtils.inject(this);
		initChildView(initListView(topicList, R.layout.tab_friend_data_top));
	}

	private void initChildView(View v) {
		topicNumL = (LinearLayout) v.findViewById(R.id.topicNumL);
		topicImgArr = (LinearLayout) v.findViewById(R.id.topicImgArr);
		topicImg = (ImageView) v.findViewById(R.id.topicImg);
		topicBack = (ImageView) v.findViewById(R.id.topicBack);
		topicHead = (ImageView) v.findViewById(R.id.topicHead);
		topicChat = (ImageView) v.findViewById(R.id.topicChat);
		topicVideo = (ImageView) v.findViewById(R.id.topicVideo);
		topicNumImg = (ImageView) v.findViewById(R.id.topicNumImg);
		topicName = (TextView) v.findViewById(R.id.topicName);
		topicFans = (TextView) v.findViewById(R.id.topicFans);
		topicSign = (TextView) v.findViewById(R.id.topicSign);
		topicTime = (TextView) v.findViewById(R.id.topicTime);
		topicNum = (TextView) v.findViewById(R.id.topicNum);
		topicBack.setOnClickListener(this);
		topicList.addHeaderView(v);
		showFriend();
	}

	private void showFriend() {
		topicList.setAdapter(mTopicAdapter = new TopicAdapter(this));
		topicList.refresh();
		topicList.setOnItemClickListener(this);
		initImgArr();
	}

	private void initImgArr() {
		topicImgArr.removeAllViews();
		for (int i = 0; i < 10; i++) {
			View imgV = view(R.layout.img);
			ImageView img = (ImageView) imgV.findViewById(R.id.img);
			topicImgArr.addView(imgV);
		}
	}

	@Override
	public void onItemClick(ZrcListView parent, View view, int position, long id) {

	}

	@Override
	protected void onRefresh() {
		super.onRefresh();
		request();
	}

	@Override
	protected void onLoadMore() {
		super.onLoadMore();
		requestMore();
	}

	private void request() {
		netProgress(HTTP_MOTHED.GET, null, TopicBean.class,
				Constant.GET_FRIEND, true);
	}

	private void requestMore() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("start", pageId * nums);
		map.put("end", nums);
		netProgress(HTTP_MOTHED.GET, map, TopicBean.class, Constant.GET_FRIEND,
				false);
		pageId++;
	}

	@Override
	public void onSuccess(Object obj) {
		super.onSuccess(obj);
		if (isRefresh) {
			if (obj instanceof TopicBean) {
				TopicBean fb = (TopicBean) obj;
				if (fb.result == 0 && fb.res != null) {
					List<Topic> arr = fb.res.topic;
					if (ValidateUtils.isArr(arr)) {
						mTopicAdapter.refresh(arr);
						topicList.setRefreshSuccess("加载成功"); // 通知加载成功
						int size = arr.size();
						if (size >= nums)
							topicList.startLoadMore(); // 开启LoadingMore功能
					} else {
						topicList.setRefreshFail("获取数据为空!");
					}
				} else {
					topicList.setRefreshFail(fb.msg);
				}
			}
		} else {
			if (obj instanceof TopicBean) {
				TopicBean tb = (TopicBean) obj;
				if (tb.res != null && tb.result == 0) {
					List<Topic> arr = tb.res.topic;
					if (ValidateUtils.isArr(arr)) {
						mTopicAdapter.loadMore(arr);
						topicList.setLoadMoreSuccess();
					} else {
						topicList.stopLoadMore();
					}
				} else {
					topicList.stopLoadMore();
				}
			} else {
				topicList.stopLoadMore();
			}
		}
	}

	@Override
	public void onFailse(JException err) {
		super.onFailse(err);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.topicBack:
			this.finish();
			break;
		}
	}
}
