package com.zdb.android.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class UserAlbum implements Serializable{

	private static final long serialVersionUID = -5199981057313223780L;

	@SerializedName("id")
	public int id;
	@SerializedName("albumId")
	public String albumId;
	@SerializedName("img")
	public String img;
	@SerializedName("audio")
	public String audio;
	@SerializedName("video")
	public String video;
	@SerializedName("view")
	public int view;
	@SerializedName("praise")
	public int praise;
	@SerializedName("msg")
	public String msg;
	@SerializedName("createTime")
	public long createTime;
	@SerializedName("updateTime")
	public long updateTime;
}
