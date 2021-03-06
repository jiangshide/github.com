package com.zdb.android.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zdb.android.R;

public class FlowLayout extends RelativeLayout implements OnClickListener {
	private float minTextSize = 18;
	private float maxTextSize = 25;

	private float minAlpha = 0.2f;
	private float maxAlpha = 1f;

	private int mWidth, mHeight;
	private int textMargin = (int) (4.5 * minTextSize);
	private int firstTextY;
	private boolean isInit = true;
	private int moveSpeed = 2;
	private float scaleArcTopPoint = 3;
	private boolean isAutoMove = true;
	private float lastX, lastY;
	private boolean isClick = false;
	Timer timer;
	MyTimerTask mTask;
	List<TextView> mTextViews, leftTextViews, rightTextViews;
	private Context mContext;

	public FlowLayout(Context context) {
		super(context);
		init(context);
	}

	public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public FlowLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		timer = new Timer();
		mTask = new MyTimerTask(handler);
		mTextViews = new ArrayList<TextView>();
		leftTextViews = new ArrayList<TextView>();
		rightTextViews = new ArrayList<TextView>();
		mContext = context;
	}

	public void addText(String text) {
		TextView tv = createTextView(text);
		mTextViews.add(tv);
		addView(tv);
	}

	public void addLeftText(String text) {
		TextView tv = createTextView(text);
		leftTextViews.add(tv);
		addView(tv);
	}

	public void addRightText(String text) {
		TextView tv = createTextView(text);
		rightTextViews.add(tv);
		addView(tv);
	}

	private TextView createTextView(String text) {
		TextView tv = new TextView(mContext);
		tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		tv.setText(text);
		tv.setTextColor(getResources().getColor(R.color.pink));
		tv.setTextSize(minTextSize);
		tv.setGravity(Gravity.CENTER);
		tv.setOnClickListener(this);
		tv.setOnTouchListener(touchListener);
		return tv;
	}

	private OnTouchListener touchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			float x = event.getX();
			float y = event.getY();
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				isClick = true;
				lastX = event.getX();
				lastY = event.getY();
				stop();
				isAutoMove = false;
				break;
			case MotionEvent.ACTION_MOVE:
				float length = (float) Math.sqrt(Math.pow(x - lastX, 2)
						+ Math.pow(y - lastY, 2));
				if (length > 10)
					isClick = false;
				float y_length = event.getY() - lastY;
				if (y_length < 0) {
					isMoveUp = true;
					moveUp((int) -y_length);
				} else if (canDown && y_length > 0) {
					isMoveUp = false;
					moveDown((int) y_length);
				}
				lastX = event.getX();
				lastY = event.getY();
				break;

			case MotionEvent.ACTION_UP:
				if (isClick)
					v.performClick();
				else {
					start();
				}
				isAutoMove = true;
				break;

			}
			return true;
		}
	};

	public void start() {
		if (mTask != null) {
			mTask.cancel();
			mTask = null;
		}
		mTask = new MyTimerTask(handler);
		timer.schedule(mTask, 0, 10);
	}

	public void stop() {
		if (mTask != null) {
			mTask.cancel();
			mTask = null;
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int counts = getChildCount();
		for (int i = 0; i < counts; i++) {
			View view = getChildAt(i);
			measureChild(view, widthMeasureSpec, heightMeasureSpec);
		}
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	public void dispatchWindowFocusChanged(boolean hasFocus) {
		super.dispatchWindowFocusChanged(hasFocus);
		if (isInit) {
			mHeight = getHeight();
			mWidth = getWidth();
			firstTextY = mHeight;
			isInit = false;
			start();
		}
	}

	private void moveUp(int speed) {
		firstTextY -= speed;
		if (firstTextY < -textMargin) {
			canDown = true;
			firstTextY = mTextViews.get(1).getTop();
			TextView tv = mTextViews.get(0);
			mTextViews.remove(0);
			mTextViews.add(tv);
			tv = leftTextViews.get(0);
			leftTextViews.remove(0);
			leftTextViews.add(tv);
			tv = rightTextViews.get(0);
			rightTextViews.remove(0);
			rightTextViews.add(tv);
		}
		FlowLayout.this.requestLayout();
	}

	private void moveDown(int speed) {
		firstTextY += speed;
		if (firstTextY > textMargin) {
			firstTextY = -textMargin;
			TextView tv = mTextViews.get(mTextViews.size() - 1);
			mTextViews.remove(mTextViews.size() - 1);
			mTextViews.add(0, tv);
			tv = leftTextViews.get(leftTextViews.size() - 1);
			leftTextViews.remove(leftTextViews.size() - 1);
			leftTextViews.add(0, tv);
			tv = rightTextViews.get(rightTextViews.size() - 1);
			rightTextViews.remove(rightTextViews.size() - 1);
			rightTextViews.add(0, tv);
		}
		FlowLayout.this.requestLayout();
	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			synchronized (FlowLayout.this) {
				if (isAutoMove) {
					if (isMoveUp)
						moveUp(moveSpeed);
					else
						moveDown(moveSpeed);
				}
			}
		}

	};

	class MyTimerTask extends TimerTask {
		Handler Taskhandler;

		public MyTimerTask(Handler handler) {
			Taskhandler = handler;
		}

		@Override
		public void run() {
			Taskhandler.sendMessage(Taskhandler.obtainMessage());
		}

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (!isInit) {
			layoutAll(mTextViews, 0);
			int temp = firstTextY;
			firstTextY += textMargin;
			layoutAll(leftTextViews, -1);
			layoutAll(rightTextViews, 1);
			firstTextY = temp;
		}
	}

	private boolean isMoveUp = true;
	private boolean canDown = false;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			isClick = true;
			lastX = event.getX();
			lastY = event.getY();
			stop();
			isAutoMove = false;
			break;
		case MotionEvent.ACTION_MOVE:
			float length = (float) Math.sqrt(Math.pow(x - lastX, 2)
					+ Math.pow(y - lastY, 2));
			if (length > 10)
				isClick = false;
			float y_length = event.getY() - lastY;
			if (y_length < 0) {
				isMoveUp = true;
				moveUp((int) -y_length);
			} else if (canDown && y_length > 0) {
				isMoveUp = false;
				moveDown((int) y_length);
			}
			lastX = event.getX();
			lastY = event.getY();
			break;

		case MotionEvent.ACTION_UP:
			if (isClick) {
				stop();
				delayStartHandler.sendEmptyMessageDelayed(0, 2000);
			} else {
				start();
			}
			isAutoMove = true;
			break;

		}
		return true;
	}

	private void layoutAll(List<TextView> textViews, int type) {
		int temp_y = firstTextY;
		for (int i = 0; i < textViews.size(); i++) {
			TextView temp = textViews.get(i);
			int detaX = type
					* (int) (-mWidth * 4 / scaleArcTopPoint
							/ Math.pow(mHeight, 2)
							* Math.pow(mHeight / 2.0 - temp_y, 2) + mWidth
							/ scaleArcTopPoint);
			float scale = (float) (1 - 4 * Math.pow(mHeight / 2.0 - temp_y, 2)
					/ Math.pow(mHeight, 2));
			if (scale < 0)
				scale = 0;
			float textScale = (float) ((minTextSize + scale
					* (maxTextSize - minTextSize)) * 1.0 / minTextSize);
			temp.setScaleX(textScale);
			temp.setScaleY(textScale);
			temp.setAlpha(minAlpha + scale * (maxAlpha - minAlpha));
			temp.layout((mWidth - temp.getMeasuredWidth()) / 2 + detaX, temp_y,
					(mWidth + temp.getMeasuredWidth()) / 2 + detaX, temp_y
							+ temp.getMeasuredHeight());
			temp_y += 2 * textMargin;
		}
	}

	Handler delayStartHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			start();
		}

	};

	@Override
	public void onClick(View v) {
		TextView tv = (TextView) v;
		Toast.makeText(mContext, tv.getText(), Toast.LENGTH_SHORT).show();
		stop();
		delayStartHandler.sendEmptyMessageDelayed(0, 2000);
	}
}
