package cn.tencent.DiscuzMob.utils;


import android.text.SpannableStringBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static String getString(Object obj, boolean nullToNull) {
        if (nullToNull && obj == null) return "null";
        return getString(obj);
    }

    public static String getString(Object obj) {
        return isEmptyObject(obj) ? "" : obj.toString();
    }

    public static boolean isEmpty(CharSequence charSequence) {
//        return TextUtils.isEmpty(charSequence);
        if (charSequence == null || charSequence.length() == 0||charSequence.toString().contains("null")||charSequence.toString().equals("")) {
            return true;
        }
        return false;
    }

    public static SpannableStringBuilder removeUselessLn(CharSequence text, int maxLinesCount) {
        SpannableStringBuilder result = new SpannableStringBuilder();
        if (StringUtil.isEmpty(text)) return result;
        String regex = "(\\r?\\n){" + maxLinesCount + ",}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        int cursor = 0;
        while (matcher.find(cursor)) {
            int start = matcher.start();
            int end = matcher.end();
            if (cursor < start) {
                result.append(text.subSequence(cursor, start));
            }
            if (start != 0) {
                if (end == text.length()) {
                    result.append("\n");
                } else {
                    for (int i = 0; i < maxLinesCount; i++) {
                        result.append("\n");
                    }
                }
            }
            cursor = end;
        }
        if (cursor < StringUtil.getLenght(text)) {
            result.append(text.subSequence(cursor, text.length()));
        }
        return result;
    }

    public static boolean isAllSpace(CharSequence charSequence) {
        if (isEmpty(charSequence)) return true;
        String regex = "\\S+";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(charSequence);
        if (matcher.find()) {
            return false;
        }
        return true;
    }

    public static boolean isEmptyObject(Object o) {
        if (o == null) return true;
        return isEmpty(o.toString());
    }

    /**
     * 字符串比较，均为空时判否
     *
     * @param o0 字符串1
     * @param o1 字符串2
     * @return
     */
    public static boolean equals(String o0, String o1) {
        return equals(o0, o1, false);
    }

    /**
     * 字符串比较
     *
     * @param o0            字符串1
     * @param o1            字符串2
     * @param bothNullEqual 均为空时是否判等
     * @return
     */
    public static boolean equals(String o0, String o1, boolean bothNullEqual) {
        if (o0 == null && o1 == null) {
            return bothNullEqual;
        } else if (o0 == null || o1 == null) {
            return false;
        } else {
            return o0.equals(o1);
        }
    }

    /**
     * 字符串比较，均为空时判否
     *
     * @param o0 字符串1
     * @param o1 字符串2
     * @return
     */
    public static boolean equalsIgnoreCase(String o0, String o1) {
        return equalsIgnoreCase(o0, o1, false);
    }

    /**
     * 字符串比较，均为空时判否
     *
     * @param o0 字符串1
     * @param o1 字符串2
     * @return
     */
    public static boolean equalsIgnoreCase(String o0, String o1, boolean bothNullEqual) {
        if (o0 == null && o1 == null) {
            return bothNullEqual;
        } else if (o0 == null || o1 == null) {
            return false;
        } else {
            return o0.equalsIgnoreCase(o1);
        }
    }


    public static String clearSpecial(String message) {
        String r = "\u200E";
        String l = "\u200F";
        String c = "\u200C";
        String d = "\u200D";
        message = message.replace(r, "");
        message = message.replace(l, "");
        message = message.replace(c, "");
        message = message.replace(d, "");
        return message;
    }

    public static boolean isPhoneNumber(String number) {
        String regex = "[0-9]{11}";
        Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        return pattern.matcher(number).matches();
    }

    public static String getLowerCaseString(String encode) {
        if (StringUtil.isEmpty(encode)) return encode;
        return encode.toLowerCase();
    }

    public static int getLenght(CharSequence str) {
        return str != null ? str.length() : 0;
    }

    public static int getInteger(String num) {
        return getInteger(num, 0);
    }

    public static int getInteger(String num, int intDef) {
        int numValue = intDef;
        if (StringUtil.isEmpty(num)) return numValue;
        String regex = "[0-9]+";
        Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(num);
        if (matcher.matches()) {
            try {
                numValue = Integer.valueOf(num);
            } catch (NumberFormatException ne) {
            } catch (Exception e) {
            }
        }
        return numValue;
    }


    public static long getLong(String num) {
        return getLong(num, 0);
    }

    public static long getLong(String num, long longDef) {
        long numValue = longDef;
        if (StringUtil.isEmpty(num)) return numValue;
        String regex = "[0-9]+";
        Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(num);
        if (matcher.matches()) {
            try {
                numValue = Long.valueOf(num);
            } catch (NumberFormatException ne) {
            } catch (Exception e) {
            }
        }
        return numValue;
    }

    /**
     * 计算字符占用的unicode字节数
     *
     * @param ch 待计算字符
     * @return 占用unicode字节数
     */
    public static int calculateCharactorUnicodeBytes(char ch) {
        if (ch <= 255) {
            return 1;
        } else {
            return 3;
        }
    }

    /**
     * 按照花粉服务器策略计算输入内容长度(UTF-8编码)
     *
     * @return
     */
    public static int calculateFansTextLength(String text) {
        if (isEmpty(text)) {
            return 0;
        }
        int size = text.length();
        int length = 0;
        for (int i = 0; i < size; i++) {
            length += calculateCharactorUnicodeBytes(text.charAt(i));
        }

        return length;
    }


    public static String toUnicode(String s) {
        String as[] = new String[s.length()];
        String s1 = "";
        for (int i = 0; i < s.length(); i++) {
            as[i] = Integer.toHexString(s.charAt(i) & 0xffff);
            s1 = s1 + "\\u" + as[i];
        }
        return s1;
    }

    public static String toUnicodeString(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                sb.append("\\u" + Integer.toHexString(c));
            }
        }
        return sb.toString();
    }

    public static String createUnicodeString(String startUnicode, String endUnicode) {
        char cs = startUnicode.charAt(0);
        char ce = endUnicode.charAt(0);
        StringBuffer sb = new StringBuffer();
        for (char c = cs; c < ce; c++) {
            sb.append("\\ud83e");
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                sb.append("\\u" + Integer.toHexString(c));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
