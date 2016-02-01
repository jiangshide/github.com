package com.zdb.android.activity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsd.android.view.ViewUtils;
import com.jsd.android.view.annotation.ViewInject;
import com.zdb.android.R;
import com.zdb.android.adapter.PublishAlbumAdapter;
import com.zdb.android.base.BaseActivity;
import com.zdb.android.bean.ImageItem;

public class PublishItemActivity extends BaseActivity implements
		OnItemClickListener, OnClickListener {

	@ViewInject(R.id.left)
	public ImageView left;
	@ViewInject(R.id.title)
	public TextView title;
	@ViewInject(R.id.right)
	public ImageView right;

	@ViewInject(R.id.albumImgItemList)
	private GridView albumImgItemList;
	@ViewInject(R.id.albumImgItemSelected)
	private TextView albumImgItemSelected;

	private PublishAlbumAdapter mPublishAlbumAdapter;

	private List<ImageItem> selectedArr;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.publish_album_item);
		ViewUtils.inject(this);
		initTop();
	}

	public void initTop() {
		left.setImageResource(R.drawable.btn_back);
		right.setImageResource(R.drawable.arrow);
		left.setOnClickListener(this);
		right.setOnClickListener(this);
		title.setText("相册");
		initItemData((List<ImageItem>) getIntent().getExtras().getSerializable(
				"itemAlbum"));
	}

	private void initItemData(List<ImageItem> itemList) {
		albumImgItemList.setOnItemClickListener(this);
		albumImgItemList
				.setAdapter(mPublishAlbumAdapter = new PublishAlbumAdapter(this));
		mPublishAlbumAdapter.update(itemList);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		selectedArr = mPublishAlbumAdapter.selected(position);
		int size = selectedArr.size();
		albumImgItemSelected.setText("共选择: " + size);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.left:
			finish();
			break;
		case R.id.right:
			Bundle bundle = new Bundle();
			bundle.putSerializable("item", (Serializable) selectedArr);
			toPage(PublishUploadActivity.class, bundle,false);
			break;
		}
	}
}
