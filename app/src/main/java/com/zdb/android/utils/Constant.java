package com.zdb.android.utils;

import java.util.Random;

import android.text.format.Time;

public class Constant {
	public static final String GET_SPLASH = "zdb/splash";
	public static final String ADD_DEVICE = "zdb/device/addDevice";
	public static final String ADD_LOCATION = "zdb/device/addLocation";
	public static final String LOGIN = "zdb/user/login";
	public static final String IDENTIFYCODE = "zdb/identify/identifyCode";
	public static final String REG = "zdb/user/reg";
	public static final String UPDATE = "zdb/user/update";
	public static final String USER_INFO = "zdb/user/info";
	public static final String USER_UPDATE = "zdb/user/update";
	public static final String USER_LIST = "zdb/user/userList";
	public static final String UPLOAD = "zdb/upload/files";

	public static final String HOME_SHOP = "zdb/home";
	public static final String HOME_SHOP_LIST = "zdb/home/homeList";
	public static final String ADD_HOME_SHOP = "zdb/home/addHomeShop";
	public static final String UPDATE_HOME_SHOP = "zdb/home/updateHomeShop";
	public static final String ADD_HOME_AD = "zdb/home/addHomeAd";
	public static final String UPDATE_HOME_AD = "zdb/home/updateHomeAd";
	public static final String GET_BANNER = "zdb/home/banner";
	public static final String GET_FRIEND = "zdb/friend";
	public static final String GET_CIRCLE = "zdb/friend/circle";
	public static final String ADD_CIRCLE = "zdb/friend/addCircle";
	public static final String GET_FRIEND_WAR = "zdb/friend/war";
	public static final String UPDATE_FRIEND_WAR = "zdb/friend/updateWar";
	public static final String TAB_SHOP = "zdb/shop";
	
	public static final int IO_BUFFER_SIZE = 2*1024;

	/**
	 * share
	 */
	public static final String POSTER = "poster";
	public static final String POSTER_DIALOG = "posterDialog";
	public static final String JSON_USER = "user";
	public static final String USER_ID = "userId";
	public static final String TOKEN = "token";
	public static final String DMW = "widthPixels";
	public static final String DMH = "heightPixels";
	public static final String PUBLISH_TYPE = "publishType";

	public static String[][] educationArr = { { "小学", "高中", "中专", "大专", "本科",
			"博士", "科学家" } };
	public static String[][] pNationArr = { { "汉族", "蒙古族", "回族", "壮族", "维吾尔族",
			"藏族", "苗族", "彝族", "布依族", "朝鲜族", "满族", "侗族", "瑶族", "白族", "土家族",
			"哈尼族", "哈萨克族", "傣族", "黎族", "僳僳族", "佤族", "畲族", "拉祜族", "水族", "东乡族",
			"纳西族", "景颇族", "柯尔克孜族", "土族", "达斡尔族", "仫佬族", "仡佬族", "羌族", "锡伯族",
			"布朗族", "撒拉族", "毛南族", "阿昌族", "普米族", "塔吉克族", "怒族", "乌孜别克族", "俄罗斯族",
			"鄂温克族", "德昂族", "保安族", "裕固族", "京族", "基诺族", "高山族", "塔塔尔族", "独龙族",
			"鄂伦春族", "赫哲族", "门巴族", "珞巴族" } };
	public static String[][] pBirthday = { getBarthday(0), getBarthday(1),
			getBarthday(2) };

	public static String[][] getAge() {
		String[][] arr = new String[1][100];
		for (int i = 10; i < 100; i++) {
			arr[0][i] = i + "";
		}
		return arr;
	}

	private static String[] getBarthday(int flag) {
		String[] arr = null;
		int size;
		switch (flag) {
		case 0:
			Time time = new Time("GMT+8");
			time.setToNow();
			int t = time.year;
			size = 65;
			arr = new String[size];
			for (int i = 0; i < size; i++) {
				arr[i] = (t - i) + "";
			}
			break;
		case 1:
			size = 12;
			arr = new String[size];
			for (int i = 0; i < size; i++) {
				arr[i] = i + 1 + "";
			}
			break;
		case 2:
			size = 24;
			arr = new String[size];
			for (int i = 0; i < size; i++) {
				arr[i] = i + 1 + "";
			}
			break;
		}
		return arr;
	}

	public static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
}
