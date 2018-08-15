package com.lq.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/8/14.
 * unicode 编码格式转为String
 *
 * String转为unicode
 */
public class UnicodeToString {

    static String getUnicode(String s) {
        try {
            StringBuffer out = new StringBuffer("");
            byte[] bytes = s.getBytes("unicode");
            for (int i = 0; i < bytes.length - 1; i += 2) {
                out.append("\\u");
                String str = Integer.toHexString(bytes[i + 1] & 0xff);
                for (int j = str.length(); j < 2; j++) {
                    out.append("0");
                }
                String str1 = Integer.toHexString(bytes[i] & 0xff);
                out.append(str1);
                out.append(str);

            }
            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String unicodeToString(String str) {

        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");
        }
        return str;
    }

    public static void main(String[] args) {
        String ss ="{\"status\":true,\"code\":0,\"msg\":\"ok\",\"count\":\"\",\"data\":{\"title\":\"\\u68a6\\u91cc\\u8001\\u5bb6\\u5c71\\u6c34\\u5b9e\\u666f\\u6f14\\u51fa\",\"plan\":\"2018-08-14(\\u5468\\u4e8c)\\u7b2c1\\u573a20:00-21:10\",\"product\":\"20180303\",\"datetime\":\"2018-08-14\",\"starttime\":\"20:00\",\"endtime\":\"21:10\",\"quantity\":3478,\"param\":[{\"area\":\"160\",\"title\":\"\\u5609\\u5bbe\\u533a\",\"sku\":1494,\"quantity\":302},{\"area\":\"162\",\"title\":\"VIP\",\"sku\":36,\"quantity\":0},{\"area\":\"163\",\"title\":\"\\u8d35\\u5bbe\\u533a\",\"sku\":238,\"quantity\":28},{\"area\":\"164\",\"title\":\"\\u52a0\\u5ea7\\u533a\",\"sku\":2040,\"quantity\":0}]}}";
        System.out.println(UnicodeToString.unicodeToString(ss));
        System.out.println(UnicodeToString.getUnicode(ss));
    }
}
