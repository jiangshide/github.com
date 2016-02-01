package com.zdb.android.bean;

import java.io.Serializable;

/**
 * 一个图片对象
 * 
 * @author Administrator
 * 
 */
public class ImageItem implements Serializable {
	private static final long serialVersionUID = 5899191334901142355L;
	public String imageId;
	public String thumbnailPath;
	public String imagePath;
	public boolean isSelected = false;
	public boolean isUploaded;
}
