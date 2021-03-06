package com.hw.tobcore.util;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtils {
    private static final Log log = LogFactory.getLog(StringUtils.class);
    public static String GBKToUTF(String str) {
        String utfStr = null;
        try {
            utfStr = new String(str.getBytes("GBK"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return utfStr;
    }

    public static String UTFToGBK(String str) {
        String utfStr = null;
        try {
            utfStr = new String(str.getBytes("UTF-8"), "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return utfStr;
    }

    public static String ISOToGBK(String str) {
        String utfStr = null;
        try {
            utfStr = new String(str.getBytes("ISO8859_1"), "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return utfStr;
    }

    public static String getPasWordStr(String password){
        String passStr = "";
        MessageDigest digester = null;
        try {
            digester = MessageDigest.getInstance("SHA");
            digester.update(password.getBytes("utf-8"));
            byte[] bytes = digester.digest();
            passStr = new String(Hex.encodeHex(bytes));
        } catch (Exception e) {
            log.error("解密失败！", e);
        }
        return passStr;
    }

    /**
     * 将字符转换成map
     * {row:20000,pageno:1,startdate:2008-9-10,enddate:2008-10-1}
     *@author hudaowan
     *@date  Nov 3, 2008 10:03:26 AM
     *@return
     */
    public static Map<String,String> strToMap(String str){
        Map<String,String> map = new HashMap<String,String>();
        if(!isBlank(str)){
            String strObj = str.substring(1,str.length()-1);
            String[] obj = strObj.split(",");
            for(String value:obj){
                String[] key = value.split(":");
                map.put(key[0], key[1]);
            }
        }
        return map;
    }

    /**
     * 将map转换成字符
     *@author hudaowan
     *@date  Nov 3, 2008 10:21:31 AM
     *@param map
     *@return
     */
    public static String mapToStr(Map<String,String> map){
        String str="";
        if(map!=null){
            for(Map.Entry<String,String> obj:map.entrySet()){
                String key = obj.getKey();
                String value = obj.getValue();
                str += key+":"+value+",";
            }
        }
        if(str.length()>0){
            str ="{"+str.substring(0,str.length()-1)+"}";
        }
        return str;
    }

    /**
     * @param s
     * @return 如果<tt>s</tt>为<tt>null</tt>或空白字符串返回<tt>true</tt>
     */
    public static boolean isBlank(String s) {
        return s == null ? true : s.trim().length() == 0;
    }

    /**
     * 用<tt>seperator</tt>连接字符串,例如将数组{"a","b","c"}使用';'连接,得到"a;b;c",忽略<tt>null<tt>和空白字符串
     * @see #join(String[], String, boolean, boolean)
     * @param s 需要连接的字符串数组
     * @param seperator 分隔符
     * @return 连接好的字符串或""
     * @throws NullPointerException 如果<tt>s</tt>或<tt>seperator</tt>为<tt>null</tt>
     */
    public static String join(String[] s, String seperator) {
        return join(s, seperator, true, true);
    }

    /**
     * 用<tt>seperator</tt>连接字符串,例如将数组{"a","b","c"}使用';'连接，得到"a;b;c"
     *
     * @param s 需要连接的字符串数组
     * @param seperator 分隔符
     * @param ignoreBlank 是否忽略空字符串,通过<tt>String.trim().length() == 0</tt>判断空字符串
     * @param ignoreNull 是否忽略<tt>null</tt>
     * @return 连接好的字符串或""
     * @throws NullPointerException 如果<tt>s</tt>或<tt>seperator</tt>为<tt>null</tt>
     */
    public static String join(String[] s, String seperator,
                              boolean ignoreBlank, boolean ignoreNull) {
        if (s == null || seperator == null)
            throw new NullPointerException();
        StringBuilder result = new StringBuilder(256);
        for (String s_ : s) {
            if (ignoreNull && s_ == null)
                continue;
            else if (ignoreBlank && s_.trim().length() == 0)
                continue;
            result.append(s_);
            result.append(seperator);
        }
        int i = result.length();
        if (i > 0)
            return result.substring(0, i - seperator.length());
        else
            return "";
    }



    /**
     * 将CamelCase转换成大写字母，以“_”为间隔，例如abcFoo转换成ABC_FOO
     *
     * @param s
     * @return
     */
    public static String camelToCapital(String s) {
        final String pattern = "[A-Z]*[a-z0-9]+|[A-Z0-9]+";
        Pattern p = Pattern.compile(pattern); // the expression
        Matcher m = p.matcher(s); // the source
        String r = null;
        while (m.find()) {
            if (r != null) {
                r = r + "_" + m.group().toUpperCase();
            } else
                r = m.group().toUpperCase();
        }
        return r;
    }
    /**
     * 将CamelCase转换成大写字母，以“_”为间隔，例如abcFoo转换成abc_foo
     *
     * @param s
     * @return
     */
    public static String camelToCamel(String s) {
        final String pattern = "[A-Z]*[a-z0-9]+|[A-Z0-9]+";
        Pattern p = Pattern.compile(pattern); // the expression
        Matcher m = p.matcher(s); // the source
        String r = null;
        while (m.find()) {
            if (r != null) {
                r = r + "_" + m.group().toLowerCase();
            } else
                r = m.group().toLowerCase();
        }
        return r;
    }
    /**
     * 将大写字母转换成CamelCase，以“_”为间隔，例如ABC_FOO转换成abcFoo
     *
     * @param s
     * @return
     */
    public static String capitalToCamel(String s) {
        String[] tokens = s.split("_");
        String r = tokens[0].toLowerCase();
        for (int i = 1; i < tokens.length; i++) {
            r += (tokens[i].substring(0, 1) + tokens[i].substring(1)
                    .toLowerCase());
        }
        return r;
    }

    /**
     * 截取字符串，按照系统默认的字符集，截取后的后缀为“...”
     *
     * @param target 被截取的原字符串，此方法执行前会先<tt>trim</tt>
     * @param maxBytes 截取后字符串的最大<tt>byte</tt>数，包括截取后的字符串的后缀
     * @see #substring(String, String, int, String)
     * @return
     */
    public static String substring(String target, int maxBytes) {
        return substring(target.trim(), Charset.defaultCharset().name(),
                maxBytes, "...");
    }

    /**
     * 截取字符串
     *
     * @param target 被截取的原字符串
     * @param charset 字符串的字符集
     * @param maxBytes 截取后字符串的最大<tt>byte</tt>数，包括截取后的字符串的后缀
     * @param append 字符串被截去后的后缀
     * @return
     */
    public static String substring(String target, String charset, int maxBytes,
                                   String append) {
        try {
            int count = getBytes(target, charset).length;
            if (count <= maxBytes) {
                return target;
            } else {
                int bytesCount = 0;
                char[] replace = new char[getBytes(append, charset).length];
                int j = 0;
                int bound = maxBytes - getBytes(append, charset).length;
                for (int i = 0; i < target.length(); i++) {
                    char c = target.charAt(i);
                    bytesCount = c > 255 ? bytesCount + 2 : bytesCount + 1;
                    if (bytesCount > maxBytes) {
                        return target.substring(0, i - j).concat(append);
                    }
                    if (bytesCount > bound) {
                        replace[j++] = c;
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Unreachable!");
    }

    private static byte[] getBytes(String s, String charset)
            throws UnsupportedEncodingException {
        return s.getBytes(charset);
    }

    public static String trim(String str) {
        str = str.replace('　', ' ');
        return str.trim();
    }


    public static boolean  isNotBlank(String... strs) {
        boolean isNotNull = true;
        if (strs.length == 0) {
           return false;
        }

        for (String str : strs) {
            if (StringUtils.isBlank(str)) {
                isNotNull = false;
                break;
            }
        }
        return isNotNull;
    }

    public static boolean contains(String orig, String targ) {
        return orig.toLowerCase().contains(targ.toLowerCase());
    }


    public static boolean equals(String str, String tar) {
        return isNotBlank(str, tar) && str.toLowerCase().equals(tar.toLowerCase());
    }

    public static String substringAfterLast(String str, String separator) {
        if (!isNotBlank(str, separator)) {
            return "";
        }else {
            int pos = str.lastIndexOf(separator);
            return pos != -1 && pos != str.length() - separator.length() ? str.substring(pos + separator.length()) : "";
        }
    }
    public static String toLowerCaseFirstOne(String s){
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    //首字母转大写
    public static String toUpperCaseFirstOne(String s){
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }
}
