package com.zdb.android.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

public class DialogView extends DialogFragment {

	private OnDialogListener mOnDialogListener;

	public DialogView(OnDialogListener lis) {
		this.mOnDialogListener = lis;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Bundle bundle = getArguments();
		View v = LayoutInflater.from(getActivity()).inflate(
				bundle.getInt("layout"), null);
		if (mOnDialogListener != null)
			mOnDialogListener.onDialogView(v);
		return v;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		return super.onCreateDialog(savedInstanceState);
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mOnDialogListener != null)
			mOnDialogListener.onDialogOff();
	}

	public interface OnDialogListener {
		public void onDialogView(View v);

		public void onDialogOff();
	}
}
