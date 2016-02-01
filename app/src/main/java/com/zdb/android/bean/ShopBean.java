package com.zdb.android.bean;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ShopBean extends Result {

	private static final long serialVersionUID = 4224505843206656694L;

	@SerializedName("res")
	public Response res;

	public class Response implements Serializable {
		private static final long serialVersionUID = -8071285033160322736L;
		@SerializedName("shopAd")
		public List<Ad> shopAd;
		@SerializedName("indulgence")
		public List<Indulgence> indulgence;
		@SerializedName("recommend")
		public List<Recommend> recommend;
		@SerializedName("prefecture")
		public List<Prefecture> prefecture;
	}

	public class Ad implements Serializable {
		private static final long serialVersionUID = 6099972974269695459L;
		@SerializedName("id")
		public int id;
		@SerializedName("adId")
		public String adId;
		@SerializedName("img")
		public String img;
		@SerializedName("createTime")
		public long createTime;
		@SerializedName("updateTime")
		public long updateTime;
	}

	public class Indulgence implements Serializable {
		private static final long serialVersionUID = 6702756365342798351L;
		@SerializedName("id")
		public int id;
		@SerializedName("indulgenceId")
		public String indulgenceId;
		@SerializedName("price")
		public String price;
		@SerializedName("img")
		public String img;
		@SerializedName("createTime")
		public long createTime;
		@SerializedName("updateTime")
		public long updateTime;
	}

	public class Recommend implements Serializable {
		private static final long serialVersionUID = 8377552760212489888L;
		@SerializedName("id")
		public int id;
		@SerializedName("recommendId")
		public String recommendId;
		@SerializedName("logo")
		public String logo;
		@SerializedName("img")
		public String img;
		@SerializedName("des")
		public String des;
		@SerializedName("price")
		public String price;
		@SerializedName("maketPrice")
		public String maketPrice;
		@SerializedName("label")
		public String label;
		@SerializedName("createTime")
		public long createTime;
		@SerializedName("updateTime")
		public long updateTime;
	}

	public class Prefecture implements Serializable {

		private static final long serialVersionUID = 4393010803925084969L;
		@SerializedName("id")
		public int id;
		@SerializedName("prefectureId")
		public String prefectureId;
		@SerializedName("img")
		public String img;
		@SerializedName("title")
		public String title;
		@SerializedName("shopNums")
		public int shopNums;
		@SerializedName("star")
		public int star;
		@SerializedName("price")
		public String price;
		@SerializedName("maketPrice")
		public String maketPrice;
		@SerializedName("post")
		public boolean post;
		@SerializedName("createTime")
		public long createTime;
		@SerializedName("updateTime")
		public long updateTime;
	}
}
