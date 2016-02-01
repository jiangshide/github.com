package com.zdb.android.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CusViewPager extends ViewPager {
	private boolean left = false;
	private boolean right = false;
	private boolean isScrolling = false;
	private int lastValue = -1;
	private ChangeViewCallback changeViewCallback = null;

	public CusViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CusViewPager(Context context) {
		super(context);
		init();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		View v = getFocusedChild();
		if (v != null) {
			v.measure(widthMeasureSpec,
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			int h = v.getMeasuredHeight();
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(h,
					MeasureSpec.EXACTLY);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	/**
	 * init method .
	 */
	private void init() {
		setOnPageChangeListener(listener);
	}

	/**
	 * listener ,to get move direction .
	 */
	public OnPageChangeListener listener = new OnPageChangeListener() {
		@Override
		public void onPageScrollStateChanged(int arg0) {
			if (arg0 == 1) {
				isScrolling = true;
			} else {
				isScrolling = false;
			}

			if (arg0 == 2) {
				// notify ....
				if (changeViewCallback != null) {
					changeViewCallback.changeView(left, right);
				}
				right = left = false;
			}

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			if (isScrolling) {
				if (lastValue > arg2) {
					right = true;
					left = false;
				} else if (lastValue < arg2) {
					right = false;
					left = true;
				} else if (lastValue == arg2) {
					right = left = false;
				}
			}
			Log.i("meityitianViewPager",
					"meityitianViewPager onPageScrolled  last :arg2  ,"
							+ lastValue + ":" + arg2);
			lastValue = arg2;
		}

		@Override
		public void onPageSelected(int arg0) {
			if (changeViewCallback != null) {
				changeViewCallback.getCurrentPageIndex(arg0);
			}
		}
	};

	public boolean getMoveRight() {
		return right;
	}

	public boolean getMoveLeft() {
		return left;
	}

	public interface ChangeViewCallback {
		public void changeView(boolean left, boolean right);

		public void getCurrentPageIndex(int index);
	}

	/**
	 * set ...
	 * 
	 * @param callback
	 */
	public void setChangeViewCallback(ChangeViewCallback callback) {
		changeViewCallback = callback;
	}
}
