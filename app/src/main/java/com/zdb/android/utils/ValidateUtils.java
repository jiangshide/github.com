/*
 * Copyright (c) 2011. jiangshide (jiangshide8@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zdb.android.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtils {
	private static final boolean isChineseType(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	public static final boolean isChinese(String value) {
		char[] ch = value.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChineseType(c)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isStr(String str) {
		if (null != str || !"".equals(str)) {
			return true;
		}
		return false;
	}

	public static boolean isObj(Object obj) {
		if (null != obj)
			return true;
		return false;
	}

	public static boolean isArr(String[] arr) {
		if (null != arr && arr.length > 0)
			return true;
		return false;
	}

	public static boolean isArr(List<?> arr) {
		if (null != arr && arr.size() > 0)
			return true;
		return false;
	}

	public static boolean isArr(ArrayList<?> arr) {
		if (null != arr && arr.size() > 0)
			return true;
		return false;
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		return isNum.matches();
	}

	public static boolean isMobile(String mobile) {
		String regExp = "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(mobile);
		return m.find();
	}

	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	public static boolean isValidTagAndAlias(String s) {
		Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_-]{0,}$");
		Matcher m = p.matcher(s);
		return m.matches();
	}
}
