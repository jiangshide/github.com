package com.zdb.android.bean;

import com.google.gson.annotations.SerializedName;

public class LocationBean {

	@SerializedName("locationInfoId")
	public String locationInfoId;
	@SerializedName("state")
	public boolean state;// 定位状态:true~成功定位,false~定位失败
	@SerializedName("locationType")
	public int locationType;// 定位类型
	@SerializedName("longitude")
	public double longitude;// 经度
	@SerializedName("latitude")
	public double latitude;// 纬度
	@SerializedName("accuracy")
	public float accuracy;// 精度:米
	@SerializedName("provider")
	public String provider;// 提供者:lbs or gps
	/**
	 * 已下为gps状态可获取
	 */
	@SerializedName("speed")
	public float speed;// 速度:米/秒
	@SerializedName("bearing")
	public float bearing;// 角度
	@SerializedName("satellites")
	public int satellites;// 星数：获取当前提供定位服务的卫星个数
	/**
	 * 已下为非gps状态可获取
	 */
	@SerializedName("country")
	public String country;// 国家
	@SerializedName("province")
	public String province;// 省
	@SerializedName("city")
	public String city;// 市
	@SerializedName("cityCode")
	public String cityCode;// 城市编码
	@SerializedName("district")
	public String district;// 区
	@SerializedName("adCode")
	public String adCode;// 区域码
	@SerializedName("address")
	public String address;// 地址
	@SerializedName("poiName")
	public String poiName;// 兴趣点
	/**
	 * 定位失败时状态
	 */
	@SerializedName("errorCode")
	public int errorCode;// 错误码
	@SerializedName("errorInfo")
	public String errorInfo;// 错误信息
	@SerializedName("locationDetail")
	public String locationDetail;// 错误描述
}
