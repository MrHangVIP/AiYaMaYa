package zsx.com.aiyamaya.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dawn on 2016/5/3.
 * 字符串的相关操作
 * 判断当前的字符串是否是空，将字符串格式转换成utf-8的格式，获取href中的字符串，字符串中转义符正常显示，半角和全角相互转换，转换成md5格式，验证正则表达式，
 * 手机号，邮箱，身份证，汉字的验证，判断字符串是否是数字或者字母，验证两个字符串是否相同，检查字符串的字符个数，返回个不为空的字符串
 */
public class StringUtils {
	/**
	 * 判断当前字符串是否是空或者空字符串
	 *
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		return (str == null || str.trim().length() == 0);
	}

	/**
	 * encoded in utf-8
	 * 中文字符串转utf-8格式
	 * <pre>
	 * utf8Encode(null)        =   null
	 * utf8Encode("")          =   "";
	 * utf8Encode("aa")        =   "aa";
	 * utf8Encode("啊啊啊啊")   = "%E5%95%8A%E5%95%8A%E5%95%8A%E5%95%8A";
	 * </pre>
	 *
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException if an error occurs
	 */
	public static String utf8Encode(String str) {
		if (!isBlank(str) && str.getBytes().length != str.length()) {
			try {
				return URLEncoder.encode(str, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("UnsupportedEncodingException occurred. ", e);
			}
		}
		return str;
	}

	/**
	 * get innerHtml from href
	 * 如果是href格式的字符串，获取当中的字符串
	 * <p/>
	 * <pre>
	 * getHrefInnerHtml(null)                                  = ""
	 * getHrefInnerHtml("")                                    = ""
	 * getHrefInnerHtml("mp3")                                 = "mp3";
	 * getHrefInnerHtml("&lt;a innerHtml&lt;/a&gt;")                    = "&lt;a innerHtml&lt;/a&gt;";
	 * getHrefInnerHtml("&lt;a&gt;innerHtml&lt;/a&gt;") <a>innerHtml</a>                   = "innerHtml";
	 * getHrefInnerHtml("&lt;a&lt;a&gt;innerHtml&lt;/a&gt;")                    = "innerHtml";
	 * getHrefInnerHtml("&lt;a href="baidu.com"&gt;innerHtml&lt;/a&gt;")               = "innerHtml";
	 * getHrefInnerHtml("&lt;a href="baidu.com" title="baidu"&gt;innerHtml&lt;/a&gt;") = "innerHtml";
	 * getHrefInnerHtml("   &lt;a&gt;innerHtml&lt;/a&gt;  ")                           = "innerHtml";
	 * getHrefInnerHtml("&lt;a&gt;innerHtml&lt;/a&gt;&lt;/a&gt;")                      = "innerHtml";
	 * getHrefInnerHtml("jack&lt;a&gt;innerHtml&lt;/a&gt;&lt;/a&gt;")                  = "innerHtml";
	 * getHrefInnerHtml("&lt;a&gt;innerHtml1&lt;/a&gt;&lt;a&gt;innerHtml2&lt;/a&gt;")        = "innerHtml2";
	 * </pre>
	 *
	 * @param href
	 * @return <ul>
	 * <li>if href is null, return ""</li>
	 * <li>if not match regx, return source</li>
	 * <li>return the last string that match regx</li>
	 * </ul>
	 */
	public static String getHrefInnerHtml(String href) {
		if (isBlank(href)) {
			return "";
		}

		String hrefReg = ".*<[\\s]*a[\\s]*.*>(.+?)<[\\s]*/a[\\s]*>.*";
		Pattern hrefPattern = Pattern.compile(hrefReg, Pattern.CASE_INSENSITIVE);
		Matcher hrefMatcher = hrefPattern.matcher(href);
		if (hrefMatcher.matches()) {
			return hrefMatcher.group(1);
		}
		return href;
	}

	/**
	 * process special char in html
	 * 将字符串中的转义符号转换成正常显示
	 * <pre>
	 * htmlEscapeCharsToString(null) = null;
	 * htmlEscapeCharsToString("") = "";
	 * htmlEscapeCharsToString("mp3") = "mp3";
	 * htmlEscapeCharsToString("mp3&lt;") = "mp3<";
	 * htmlEscapeCharsToString("mp3&gt;") = "mp3\>";
	 * htmlEscapeCharsToString("mp3&amp;mp4") = "mp3&mp4";
	 * htmlEscapeCharsToString("mp3&quot;mp4") = "mp3\"mp4";
	 * htmlEscapeCharsToString("mp3&lt;&gt;&amp;&quot;mp4") = "mp3\<\>&\"mp4";
	 * </pre>
	 *
	 * @param source
	 * @return
	 */
	public static String htmlEscapeCharsToString(String source) {
		return isBlank(source) ? source : source.replaceAll("&lt;", "<").replaceAll("&gt;", ">")
			.replaceAll("&amp;", "&").replaceAll("&quot;", "\"");
	}

	/**
	 * transform half width char to full width char
	 * 将字符串中的全角转换成半角
	 * <pre>
	 * fullWidthToHalfWidth(null) = null;
	 * fullWidthToHalfWidth("") = "";
	 * fullWidthToHalfWidth(new String(new char[] {12288})) = " ";
	 * fullWidthToHalfWidth("！＂＃＄％＆) = "!\"#$%&";
	 * </pre>
	 *
	 * @param s
	 * @return
	 */
	public static String fullWidthToHalfWidth(String s) {
		if (isBlank(s)) {
			return s;
		}

		char[] source = s.toCharArray();
		for (int i = 0; i < source.length; i++) {
			if (source[i] == 12288) {
				source[i] = ' ';
				// } else if (source[i] == 12290) {
				// source[i] = '.';
			} else if (source[i] >= 65281 && source[i] <= 65374) {
				source[i] = (char) (source[i] - 65248);
			} else {
				source[i] = source[i];
			}
		}
		return new String(source);
	}

	/**
	 * transform full width char to half width char
	 * 将字符串中的半角转换成全角
	 * <pre>
	 * halfWidthToFullWidth(null) = null;
	 * halfWidthToFullWidth("") = "";
	 * halfWidthToFullWidth(" ") = new String(new char[] {12288});
	 * halfWidthToFullWidth("!\"#$%&) = "！＂＃＄％＆";
	 * </pre>
	 *
	 * @param s
	 * @return
	 */
	public static String halfWidthToFullWidth(String s) {
		if (isBlank(s)) {
			return s;
		}

		char[] source = s.toCharArray();
		for (int i = 0; i < source.length; i++) {
			if (source[i] == ' ') {
				source[i] = (char) 12288;
				// } else if (source[i] == '.') {
				// source[i] = (char)12290;
			} else if (source[i] >= 33 && source[i] <= 126) {
				source[i] = (char) (source[i] + 65248);
			} else {
				source[i] = source[i];
			}
		}
		return new String(source);
	}

	/**
	 * 转换成md5格式
	 *
	 * @param string
	 * @return
	 */
	public static String md5(String string) {
		byte[] hash;
		try {
			hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Huh, MD5 should be supported?", e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Huh, UTF-8 should be supported?", e);
		}

		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10) hex.append("0");
			hex.append(Integer.toHexString(b & 0xFF));
		}
		return hex.toString();
	}

	/**
	 * 验证当前字符传是否满足当前的正则表达式
	 *
	 * @param reg 正则规则
	 * @param str 验证的字符串
	 * @return
	 */
	public static boolean checkPattern(String reg, String str) {
		if (isBlank(str)) {
			return false;
		}
		if (isBlank(reg)) {
			return true;
		}
		try {
			Pattern p = Pattern.compile(reg);
			Matcher m = p.matcher(str);
			return m.matches();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 验证当前字符串是否是邮箱
	 *
	 * @param str
	 * @return
	 */
	public static boolean isEmail(String str) {
		String reg = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		return checkPattern(reg, str);
	}

	/**
	 * 验证当前字符串是否是手机号码
	 *
	 * @param str
	 * @return
	 */
	public static boolean isMobile(String str) {
		String reg = "^13\\d{9}|14[57]\\d{8}|15[012356789]\\d{8}|18[01256789]\\d{8}|17[0678]\\d{8}$";
		return checkPattern(reg, str);
	}

	/**
	 * 验证当前的字符串是否是身份证
	 *
	 * @param str
	 * @return
	 */
	public static boolean isIDCard(String str) {
		String reg = "(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])";
		return checkPattern(reg, str);
	}

	/**
	 * 验证当前的字符串是否是汉字
	 *
	 * @param str
	 * @return
	 */
	public static boolean isCHCharacter(String str) {
		String reg = "^[\u4e00-\u9fa5]*$";
		return checkPattern(reg, str);
	}

	/**
	 * 判断字符是否为英文字母或者阿拉伯数字.
	 *
	 * @param ch char字符
	 * @return true or false
	 */
	public static boolean isAlphanumeric(char ch) {
		// 常量定义
		final int DIGITAL_ZERO = 0;
		final int DIGITAL_NINE = 9;
		final char MIN_LOWERCASE = 'a';
		final char MAX_LOWERCASE = 'z';
		final char MIN_UPPERCASE = 'A';
		final char MAX_UPPERCASE = 'Z';

		if ((ch >= DIGITAL_ZERO && ch <= DIGITAL_NINE)
			|| (ch >= MIN_LOWERCASE && ch <= MAX_LOWERCASE)
			|| (ch >= MIN_UPPERCASE && ch <= MAX_UPPERCASE)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 比较两个字符串是否相同
	 *
	 * @param first
	 * @param second
	 * @return
	 */
	public static boolean equals(String first, String second) {
		if (isBlank(first) || isBlank(second))
			return false;
		return first.equals(second);
	}

	/**
	 * 计算字符个数，一个汉字算两个
	 *
	 * @param s
	 * @return
	 */
	public static int countWord(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		int n = s.length(), a = 0, b = 0;
		int len = 0;
		char c;
		for (int i = 0; i < n; i++) {
			c = s.charAt(i);
			if (Character.isSpaceChar(c)) {
				++b;
			} else if (isAscii(c)) {
				++a;
			} else {
				++len;
			}
		}
		return len + (int) Math.ceil((a + b) / 2.0);
	}

	public static boolean isAscii(char c) {
		return c <= 0x7f;
	}

	/**
	 * 返回一个不为null的字符串
	 *
	 * @param str
	 * @return
	 */
	public static String isNull(String str) {
		return isNull(str, null);
	}

	/**
	 * 当str为null，返回defaultStr
	 *
	 * @param str
	 * @param defaultStr
	 * @return
	 */
	public static String isNull(String str, String defaultStr) {
		if (isBlank(str)) {
			if (isBlank(defaultStr)) {
				return "";
			} else {
				return defaultStr;
			}
		}
		return str;
	}

}
