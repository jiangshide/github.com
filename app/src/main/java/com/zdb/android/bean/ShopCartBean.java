package com.zdb.android.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class ShopCartBean extends Result{

	private static final long serialVersionUID = 2620787289085820029L;

	@SerializedName("res")
	public Response res;

	public class Response implements Serializable{

		private static final long serialVersionUID = -6263179255442136466L;
		
		@SerializedName("shopCart")
		public ShopCart shopCart;
	}
	
	public class ShopCart implements Serializable{

		private static final long serialVersionUID = -5790244685368065063L;
		
	}
}
