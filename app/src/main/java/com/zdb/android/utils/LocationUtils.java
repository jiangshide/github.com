package com.zdb.android.utils;

import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.android.utils.LogUtils;
import com.google.gson.Gson;
import com.zdb.android.ZdbService;
import com.zdb.android.bean.LocationBean;

public enum LocationUtils implements AMapLocationListener {
	instance;

	private Context mContext;

	/**
	 * 开始定位
	 */
	public final static int MSG_LOCATION_START = 0;
	/**
	 * 定位完成
	 */
	public final static int MSG_LOCATION_FINISH = 1;
	/**
	 * 停止定位
	 */
	public final static int MSG_LOCATION_STOP = 2;

	private AMapLocationClient locationClient = null;
	private AMapLocationClientOption locationOption = null;

	private boolean state;
	private AMapLocation loc;

	public void init(Context c) {
		this.mContext = c;
		locationClient = new AMapLocationClient(c);
		locationOption = new AMapLocationClientOption();
		// 设置定位模式为高精度模式
		locationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
		// 设置定位监听
		locationClient.setLocationListener(this);
		start(true);
	}

	public void start(boolean isStart) {
		if (isStart) {
			initOption();
			// 设置定位参数
			locationClient.setLocationOption(locationOption);
			// 启动定位
			locationClient.startLocation();
			mHandler.sendEmptyMessage(MSG_LOCATION_START);
		} else {
			// 停止定位
			locationClient.stopLocation();
			mHandler.sendEmptyMessage(MSG_LOCATION_STOP);
		}
	}

	// 根据控件的选择，重新设置定位参数
	private void initOption() {
		// 设置是否需要显示地址信息
		locationOption.setNeedAddress(true);
		/**
		 * 设置是否优先返回GPS定位结果，如果30秒内GPS没有返回定位结果则进行网络定位 注意：只有在高精度模式下的单次定位有效，其他方式无效
		 */
		locationOption.setGpsFirst(true);
		// 设置发送定位请求的时间间隔,最小值为2000，如果小于2000，按照2000算
		locationOption.setInterval(Long.valueOf(1000 * new Random()
				.nextInt(200)));

	}

	Handler mHandler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
			// 开始定位
			case MSG_LOCATION_START:
				LogUtils.e("-----start-----location...");
				state = false;
				break;
			// 定位完成
			case MSG_LOCATION_FINISH:
				loc = (AMapLocation) msg.obj;
				state = true;
				LogUtils.e("------state:" + state);
				mContext.startService(new Intent(mContext, ZdbService.class));
				break;
			// 停止定位
			case MSG_LOCATION_STOP:
				LogUtils.e("-------stop location.");
				state = false;
				break;
			default:
				break;
			}
		};
	};

	public String getLocationJson() {
		String str = null;
		if (loc != null) {
			LocationBean lb = new LocationBean();
			lb.state = state;
			lb.locationType = loc.getLocationType();
			lb.longitude = loc.getLongitude();
			lb.latitude = loc.getLatitude();
			lb.accuracy = loc.getAccuracy();
			lb.provider = loc.getProvider();
			lb.speed = loc.getSpeed();
			lb.bearing = loc.getBearing();
			lb.satellites = loc.getExtras().getInt("satellites", 0);
			lb.country = loc.getCountry();
			lb.province = loc.getProvince();
			lb.city = loc.getCity();
			lb.cityCode = loc.getCityCode();
			lb.district = loc.getDistrict();
			lb.adCode = loc.getAdCode();
			lb.address = loc.getAddress();
			lb.poiName = loc.getPoiName();
			lb.errorCode = loc.getErrorCode();
			lb.errorInfo = loc.getErrorInfo();
			lb.locationDetail = loc.getLocationDetail();
			str = new Gson().toJson(lb);
			LogUtils.e("---------str:" + str);
		}
		return str;
	}
	
	public LocationBean getLocation() {
		LocationBean lb = new LocationBean();
		if (loc != null) {
			lb.state = state;
			lb.locationType = loc.getLocationType();
			lb.longitude = loc.getLongitude();
			lb.latitude = loc.getLatitude();
			lb.accuracy = loc.getAccuracy();
			lb.provider = loc.getProvider();
			lb.speed = loc.getSpeed();
			lb.bearing = loc.getBearing();
			lb.satellites = loc.getExtras().getInt("satellites", 0);
			lb.country = loc.getCountry();
			lb.province = loc.getProvince();
			lb.city = loc.getCity();
			lb.cityCode = loc.getCityCode();
			lb.district = loc.getDistrict();
			lb.adCode = loc.getAdCode();
			lb.address = loc.getAddress();
			lb.poiName = loc.getPoiName();
			lb.errorCode = loc.getErrorCode();
			lb.errorInfo = loc.getErrorInfo();
			lb.locationDetail = loc.getLocationDetail();
		}
		return lb;
	}

	public void cancel() {
		if (null != locationClient) {
			/**
			 * 如果AMapLocationClient是在当前Activity实例化的，
			 * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
			 */
			locationClient.onDestroy();
			locationClient = null;
			locationOption = null;
		}
	}

	@Override
	public void onLocationChanged(AMapLocation loc) {
		if (null != loc) {
			Message msg = mHandler.obtainMessage();
			msg.obj = loc;
			msg.what = MSG_LOCATION_FINISH;
			mHandler.sendMessage(msg);
		}
	}

}
