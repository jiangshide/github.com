package com.zdb.android;

import java.util.HashMap;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.android.utils.HttpUtils;
import com.android.utils.HttpUtils.HTTP_MOTHED;
import com.android.utils.HttpUtils.OnResourceListener;
import com.android.utils.JException;
import com.android.utils.LogUtils;
import com.zdb.android.bean.DeviceBean;
import com.zdb.android.utils.Constant;

public class ZdbService extends Service implements OnResourceListener {

	private HttpUtils net = HttpUtils.instance;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		LogUtils.e("---------onStartCommand:" + startId);
		uploadDeviceInfo();
		return START_STICKY;
	}

	public void uploadDeviceInfo() {
		net.setSufix(Constant.ADD_DEVICE);
		DeviceBean db = new DeviceBean();
		net.sendMsg(HTTP_MOTHED.POST, getMap(db.getTelInfo(this)), this,
				DeviceBean.class);
	}

	private HashMap<String, Object> getMap(String json) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(net.JSON, json);
		return map;
	}

	@Override
	public void onDownloading(int downloadSize) {
	}

	@Override
	public void onSuccess(Object obj) {
		LogUtils.e("---------obj:" + obj);
		if (obj != null && obj instanceof DeviceBean) {

		} else {

		}
	}

	@Override
	public void onFailse(JException err) {
		LogUtils.e("-------onFailse:", err.getMessage());
	}

	@Override
	public void cancel() {
		LogUtils.e("-------cancel()");
	}
}
