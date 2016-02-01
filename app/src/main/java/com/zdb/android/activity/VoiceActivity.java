package com.zdb.android.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.jsd.android.view.ViewUtils;
import com.jsd.android.view.annotation.ViewInject;
import com.zdb.android.R;
import com.zdb.android.base.BaseActivity;
import com.zdb.android.view.voice.RecordingSampler;
import com.zdb.android.view.voice.RecordingSampler.CalculateVolumeListener;
import com.zdb.android.view.voice.VisualizerView;

public class VoiceActivity extends BaseActivity implements
		CalculateVolumeListener {

	// Recording Info
	private RecordingSampler mRecordingSampler;

	// View
	@ViewInject(R.id.visualizer)
	private VisualizerView mVisualizerView;
	@ViewInject(R.id.visualizer2)
	private VisualizerView mVisualizerView2;
	@ViewInject(R.id.visualizer3)
	private VisualizerView mVisualizerView3;
	@ViewInject(R.id.fab)
	private ImageView mFloatingActionButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		setContentView(R.layout.voice_view);
		{
			ViewTreeObserver observer = mVisualizerView.getViewTreeObserver();
			mVisualizerView
					.addOnLayoutChangeListener(new OnLayoutChangeListener() {
						@Override
						public void onLayoutChange(View v, int left, int top,
								int right, int bottom, int oldLeft, int oldTop,
								int oldRight, int oldBottom) {
							mVisualizerView.setBaseY(mVisualizerView
									.getHeight());
							if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
								// mVisualizerView.getViewTreeObserver()
								// .removeOnGlobalLayoutListener(this);
							} else {
								// mVisualizerView.getViewTreeObserver()
								// .removeGlobalOnLayoutListener(this);
							}
						}
					});
		}
		{
			ViewTreeObserver observer = mVisualizerView2.getViewTreeObserver();
			mVisualizerView2
					.addOnLayoutChangeListener(new OnLayoutChangeListener() {
						@Override
						public void onLayoutChange(View v, int left, int top,
								int right, int bottom, int oldLeft, int oldTop,
								int oldRight, int oldBottom) {
							mVisualizerView2.setBaseY(mVisualizerView2
									.getHeight() / 5);
							if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
								// mVisualizerView2.getViewTreeObserver()
								// .removeOnGlobalLayoutListener(this);
							} else {
								// mVisualizerView2.getViewTreeObserver()
								// .removeGlobalOnLayoutListener(this);
							}
						}
					});
		}
		mRecordingSampler = new RecordingSampler();
		mRecordingSampler.setVolumeListener(this);
		mRecordingSampler.setSamplingInterval(100);
		mRecordingSampler.link(mVisualizerView);
		mRecordingSampler.link(mVisualizerView2);
		mRecordingSampler.link(mVisualizerView3);

		mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mRecordingSampler.isRecording()) {
					mFloatingActionButton.setImageResource(R.drawable.ic_mic);
					mRecordingSampler.stopRecording();
				} else {
					mFloatingActionButton
							.setImageResource(R.drawable.ic_mic_off);
					mRecordingSampler.startRecording();
				}
			}
		});
	}

	@Override
	public void onCalculateVolume(int volume) {

	}
}
