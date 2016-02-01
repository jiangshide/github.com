package com.zdb.android.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class SplashBean extends Result {

	private static final long serialVersionUID = -4344435850620015992L;

	@SerializedName("res")
	public Poster res;

	public class Poster implements Serializable {

		private static final long serialVersionUID = -3780124354490887800L;

		@SerializedName("id")
		public int id;
		@SerializedName("posterId")
		public String posterId;
		@SerializedName("dImg")
		public String dImg;
		@SerializedName("img")
		public String img;
		@SerializedName("audio")
		public String audio;
		@SerializedName("video")
		public String video;
		@SerializedName("createTime")
		public long createTime;
		@SerializedName("updateTime")
		public long updateTime;
	}
}
