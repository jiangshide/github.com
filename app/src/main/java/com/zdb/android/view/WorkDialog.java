package com.zdb.android.view;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.zdb.android.R;
import com.zdb.android.view.wheek.ArrayAdapter;
import com.zdb.android.view.wheek.WheelView;
import com.zdb.android.view.wheek.WheelView.OnWheelListener;

public class WorkDialog extends Dialog implements OnClickListener {

	private Activity mActivity;
	private TextView dialogTips;
	private WheelView[] wheel;
	private View wheelV1, wheelV2;
	private Button dialogSubmit;
	private OnWheelListener mLis;
	private String mTitle;
	private int mIndex;
	private String[][] mArr;
	private int size;

	private int[] ids = { R.id.wheel1, R.id.wheel2, R.id.wheel3 };

	public WorkDialog(Activity context, String title, String[][] arr,
			OnWheelListener lis, int index) {
		super(context, R.style.MyDialogStyle);
		if (this.isShowing())
			this.dismiss();
		this.mActivity = context;
		this.mTitle = title;
		this.mArr = arr;
		this.mLis = lis;
		this.mIndex = index;
		size = arr.length;
		wheel = new WheelView[size];
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_work);
		dialogTips = (TextView) this.findViewById(R.id.dialogTips);
		dialogSubmit = (Button) this.findViewById(R.id.dialogSubmit);
		wheelV1 = this.findViewById(R.id.wheelV1);
		wheelV2 = this.findViewById(R.id.wheelV2);
		dialogTips.setText(mTitle);
		initData();
		Window window = this.getWindow();
		WindowManager.LayoutParams params = this.getWindow().getAttributes();
		window.setWindowAnimations(R.style.myAnim);
		window.setAttributes(params);
		indexPos();
	}

	private void initData() {
		for (int i = 0; i < size; i++) {
			wheel[i] = (WheelView) this.findViewById(ids[i]);
			wheel[i].setColor(mActivity.getResources().getColor(R.color.looks),
					mActivity.getResources().getColor(R.color.white), mActivity
							.getResources().getDrawable(R.drawable.block_week),
					mActivity.getResources().getColor(R.color.translete), mLis);
			wheel[i].setAdapter(new ArrayAdapter<String>(mArr[i]));
			wheel[i].TEXT_SIZE = 40;
			wheel[i].setCurrentItem(mArr[i].length / 2);
		}

		dialogSubmit.setOnClickListener(this);
	}

	private void indexPos() {
		if (size == 1) {
			wheel[0].setVisibility(View.VISIBLE);
		} else if (size == 2) {
			wheel[0].setVisibility(View.VISIBLE);
			wheelV1.setVisibility(View.VISIBLE);
			wheel[1].setVisibility(View.VISIBLE);
		} else if (size == 3) {
			wheel[0].setVisibility(View.VISIBLE);
			wheelV1.setVisibility(View.VISIBLE);
			wheel[1].setVisibility(View.VISIBLE);
			wheelV2.setVisibility(View.VISIBLE);
			wheel[2].setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialogSubmit:
			this.dismiss();
			break;
		}
	}
}
