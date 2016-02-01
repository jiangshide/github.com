package com.zdb.android.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Circle implements Serializable{

	private static final long serialVersionUID = -1615570236196515842L;

	@SerializedName("id")
	public int id;
	@SerializedName("circleId")
	public String circleId;
	@SerializedName("head")
	public String head;
	@SerializedName("name")
	public String name;
	@SerializedName("img")
	public String img;
	@SerializedName("msg")
	public String msg;
	@SerializedName("device")
	public String device;
	@SerializedName("latitude")
	public double latitude;
	@SerializedName("longitude")
	public double longitude;
	@SerializedName("netType")
	public int netType;
	@SerializedName("createTime")
	public String createTime;
}
