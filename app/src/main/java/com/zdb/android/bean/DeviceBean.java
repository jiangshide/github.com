package com.zdb.android.bean;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.android.utils.SharedUtils;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.zdb.android.utils.Constant;

public class DeviceBean extends Result {

	private static final long serialVersionUID = 5593369631716176420L;

	public String getTelInfo(Context c) {
		TelephonyManager tm = (TelephonyManager) c
				.getSystemService(c.TELEPHONY_SERVICE);
		SharedUtils su = SharedUtils.instance;
		deviceInfoId = su.getString(c, Constant.USER_ID);
		line1Number = tm.getLine1Number();
		deviceId = tm.getDeviceId();
		networkOperatorName = tm.getNetworkOperatorName();
		simSerialNumber = tm.getSimSerialNumber();
		subscriberId = tm.getSubscriberId();
		networkCountryIso = tm.getNetworkCountryIso();
		networkOperator = tm.getNetworkOperator();
		networkType = tm.getNetworkType();
		isNetworkRoaming = tm.isNetworkRoaming();
		callState = tm.getCallState();
		Build b = new Build();
		model = b.MODEL;
		device = b.DEVICE;
		product = b.PRODUCT;
		runTime = b.TIME;
		host = b.HOST;
		user = b.USER;

		String serviceString = Context.LOCATION_SERVICE;
		LocationManager locationManager = (LocationManager) c
				.getSystemService(serviceString);
		String provider = LocationManager.NETWORK_PROVIDER;
		Location loc = locationManager.getLastKnownLocation(provider);
		if(loc != null){
			Latitude = loc.getLatitude();
			Longitude = loc.getLongitude();
		}
		currTime = System.currentTimeMillis();
		widthPixels = su.getInt(c, Constant.DMW);
		heightPixels = su.getInt(c, Constant.DMH);
		pkg = c.getPackageName();
		try {
			versionCode = c.getPackageManager().getPackageInfo(pkg, 0).versionCode;
			versionName = c.getPackageManager().getPackageInfo(pkg, 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		osVersion = getOsVersion();
		wifiIp = getWifiIp(c);
		return new Gson().toJson(this);
	}

	private String getWifiIp(Context c) {
		WifiManager wifiManager = (WifiManager) c.getSystemService("wifi");
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ipAddress = wifiInfo.getIpAddress();
		// 格式化IP address，例如：格式化前：1828825280，格式化后：192.168.1.109
		String ip = String.format("%d.%d.%d.%d", (ipAddress & 0xff),
				(ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff),
				(ipAddress >> 24 & 0xff));
		return ip;

	}

	// 获取device的os version
	private String getOsVersion() {
		String version = android.os.Build.VERSION.RELEASE;
		return version;
	}

	@SerializedName("deviceInfoId")
	public String deviceInfoId;
	@SerializedName("line1Number")
	public String line1Number;// 手机号码
	@SerializedName("deviceId")
	public String deviceId;// IMEI
	@SerializedName("networkOperatorName")
	public String networkOperatorName;// 运营商
	@SerializedName("simSerialNumber")
	public String simSerialNumber;// sim卡序列号

	/**
	 * SIM的状态信息： SIM_STATE_UNKNOWN 未知状态 0 SIM_STATE_ABSENT 没插卡 1
	 * SIM_STATE_PIN_REQUIRED 锁定状态，需要用户的PIN码解锁 2 SIM_STATE_PUK_REQUIRED
	 * 锁定状态，需要用户的PUK码解锁 3 SIM_STATE_NETWORK_LOCKED 锁定状态，需要网络的PIN码解锁 4
	 * SIM_STATE_READY 就绪状态 5
	 */
	@SerializedName("simState")
	public int simState;
	@SerializedName("subscriberId")
	public String subscriberId;// IMSI

	/**
	 * 取得和语音邮件相关的标签，即为识别符 需要权限：READ_PHONE_STATE
	 */
	@SerializedName("voiceMailAlphaTag")
	public String voiceMailAlphaTag;

	/**
	 * 获取语音邮件号码： 需要权限：READ_PHONE_STATE
	 */
	@SerializedName("voiceMailNumber")
	public String voiceMailNumber;
	@SerializedName("networkCountryIso")
	public String networkCountryIso;// ISO
	@SerializedName("networkOperator")
	public String networkOperator;// 运营商编号

	/**
	 * 当前使用的网络类型： 例如： NETWORK_TYPE_UNKNOWN 网络类型未知 0 NETWORK_TYPE_GPRS GPRS网络 1
	 * NETWORK_TYPE_EDGE EDGE网络 2 NETWORK_TYPE_UMTS UMTS网络 3 NETWORK_TYPE_HSDPA
	 * HSDPA网络 8 NETWORK_TYPE_HSUPA HSUPA网络 9 NETWORK_TYPE_HSPA HSPA网络 10
	 * NETWORK_TYPE_CDMA CDMA网络,IS95A 或 IS95B. 4 NETWORK_TYPE_EVDO_0 EVDO网络,
	 * revision 0. 5 NETWORK_TYPE_EVDO_A EVDO网络, revision A. 6
	 * NETWORK_TYPE_1xRTT 1xRTT网络 7
	 */
	@SerializedName("networkType")
	public int networkType;// 网络状态
	@SerializedName("isNetworkRoaming")
	public boolean isNetworkRoaming;// 是否漫游

	/**
	 * 手机类型： 例如： PHONE_TYPE_NONE 无信号 PHONE_TYPE_GSM GSM信号 PHONE_TYPE_CDMA CDMA信号
	 */
	@SerializedName("phoneType")
	public int phoneType;
	@SerializedName("callState")
	public int callState;//
	@SerializedName("model")
	public String model;// 手机品牌
	@SerializedName("device")
	public String device;// 设备名称
	@SerializedName("product")
	public String product;// 设备型号
	@SerializedName("osVersion")
	public String osVersion;// 系统版本
	@SerializedName("wifiIp")
	public String wifiIp;// wifi的ip地址
	@SerializedName("runTime")
	public long runTime;// 运行时间
	@SerializedName("host")
	public String host;//
	@SerializedName("user")
	public String user;
	@SerializedName("latitude")
	public double Latitude;
	@SerializedName("longitude")
	public double Longitude;
	@SerializedName("currTime")
	public long currTime;// 当前时间
	@SerializedName("widthPixels")
	public int widthPixels;// 屏幕分辨率宽
	@SerializedName("heightPixels")
	public int heightPixels;// 屏幕分辨率高
	@SerializedName("pkg")
	public String pkg;// 应用包名
	@SerializedName("versionCode")
	public int versionCode;
	@SerializedName("versionName")
	public String versionName;
}
