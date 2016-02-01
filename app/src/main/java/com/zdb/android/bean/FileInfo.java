package com.zdb.android.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class FileInfo implements Serializable{

	private static final long serialVersionUID = -2707119260193531320L;

	@SerializedName("id")
	public int id;
	
	@SerializedName("createTime")
	public long createTime;
	
	@SerializedName("url")
	public String url;
}
