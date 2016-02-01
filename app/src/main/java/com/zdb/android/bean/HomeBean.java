package com.zdb.android.bean;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class HomeBean extends Result {

	private static final long serialVersionUID = 665714985824438445L;

	@SerializedName("res")
	public Response res;

	public class Response implements Serializable {
		private static final long serialVersionUID = -8519491332881452405L;
		@SerializedName("banner")
		public List<Banner> banner;
		@SerializedName("homeList")
		public List<HomeList> homeList;
	}

	public class Banner implements Serializable {
		private static final long serialVersionUID = 6099972974269695459L;
		@SerializedName("id")
		public String id;
		@SerializedName("bannerId")
		public String bannerId;
		@SerializedName("account")
		public String account;
		@SerializedName("title")
		public String title;
		@SerializedName("des")
		public String des;
		@SerializedName("img")
		public String img;
		@SerializedName("link")
		public String link;
		@SerializedName("createTime")
		public long createTime;
		@SerializedName("updateTime")
		public long updateTime;
	}

	public class HomeList implements Serializable {
		private static final long serialVersionUID = 739528484348972682L;
		@SerializedName("id")
		public String id;
		@SerializedName("goodId")
		public String goodId;
		@SerializedName("account")
		public String account;
		@SerializedName("head")
		public String head;
		@SerializedName("title")
		public String title;
		@SerializedName("des")
		public String sign;
		@SerializedName("isFollow")
		public boolean isFollow;
		@SerializedName("img")
		public String img;
		@SerializedName("label")
		public String label;
		@SerializedName("viewNum")
		public int viewNum;
		@SerializedName("msgNum")
		public int msgNum;
		@SerializedName("likeNum")
		public int likeNum;
		@SerializedName("createTime")
		public long createTime;
		@SerializedName("updateTime")
		public long updateTime;
	}

	public Banner getAd() {
		return new Banner();
	}

	public HomeList getHomeShop() {
		return new HomeList();
	}
}
