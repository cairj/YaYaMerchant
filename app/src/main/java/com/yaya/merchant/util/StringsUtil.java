package com.yaya.merchant.util;

import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringsUtil {
    public static String buildQueryString(String url, Map<String, String> params, boolean needEncode) throws UnsupportedEncodingException {

        if (params != null) {
            StringBuffer buffer = new StringBuffer();
            Iterator iterator = params.keySet().iterator();
            while (iterator.hasNext()) {
                String str = (String) iterator.next();
                if (buffer.length() > 0) {
                    buffer.append("&");
                }

                if (needEncode) {
                    buffer.append(str + "=" + URLEncoder.encode((String) params.get(str), "utf-8"));
                } else {
                    buffer.append(str + "=" + (String) params.get(str));
                }
            }
            if (buffer.length() > 0) {
                url = url + "?";
                url = url + buffer.toString();
            }
        }


        return url;
    }

    public static String toString(byte[] bytes) {
        StringBuffer buffer = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            if ((bytes[i] & 0xFF) < 16) {
                buffer.append("0");
            }
            buffer.append(Long.toString(bytes[i] & 0xFF, 165));
        }

        return buffer.toString();
    }

    public static String toString(InputStream input){
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    /**
     * 将字符串按照分隔符隔开，并添加到list中
     * @param content 要加工的内容
     * @param segment 分隔符
     * @return
     */
    public static List<String> stringToList(String content, String segment){
        if (TextUtils.isEmpty(content)){
            return null;
        }
        List<String> list = new ArrayList<>();

        String[] stringArray = content.split(segment);
        for (String temp : stringArray){
            list.add(temp);
        }
        return list;
    }

    /**
     * 将list中的字符串用分隔符拼接成一个完整的String
     * @param stringList
     * @param segment
     * @return
     */
    public static String listToString(List<String> stringList, String segment){
        if (stringList == null || stringList.size() == 0){
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (String s : stringList){
            stringBuffer.append(s).append(segment);
        }
        stringBuffer.deleteCharAt(stringBuffer.length()-1);
        return stringBuffer.toString();
    }

    public static String listToString(List<String> stringList){
        return listToString(stringList, ",");
    }

    public static String[] listToStringArray(List<String> stringList){
        if (stringList == null){
            return null;
        }
        String[] array = new String[stringList.size()];
        for (int i=0; i<stringList.size(); i++){
            array[i] = stringList.get(i);
        }
        return array;
    }

    public static List<String> jsonArrayToList(JSONArray jsonArray){
        List<String> stringList = new ArrayList<>();
        try {
            for (int i=0; i<jsonArray.length(); i++){
                String s = jsonArray.getString(i);
                stringList.add(s);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return stringList;
    }

    /**
     * 复制内容
     * @param content
     */
    public static void copy(Context context, String content){
        ClipboardManager cbm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cbm.setText(content);
    }

    public static String wrapTopicName(String topicName){
        return String.format("#%s#", topicName);
    }

    /**
     * 每两个字之间添加1个换行符
     * @param content
     * @return
     */
    public static String segmentLine(String content){
        if (TextUtils.isEmpty(content)){
            return content;
        }
        StringBuffer buffer = new StringBuffer();
        char[] array = content.toCharArray();
        for (int i=0; i<array.length; i++){
            if (i == array.length-1){
                buffer.append(array[i]);
            }else {
                buffer.append(array[i]).append("\n");
            }
        }
        return buffer.toString();
    }

    /**
     * 格式化手机号，在第三位和第七位后面添加分隔符
     * @param phone 手机号
     * @param separator 分隔符
     */
    public static String formatPhone(String phone, String separator){
        if (phone == null || separator == null){
            return "";
        }

        char[] array = phone.toCharArray();
        StringBuffer buffer = new StringBuffer();
        for (int i=0; i<array.length; i++){
            buffer.append(array[i]);
            if (i == 2 || i == 6){
                buffer.append(separator);
            }
        }
        return buffer.toString();
    }

    /**
     * 保留两位小数
     */
    public static String formatDecimals(float decimal){
        return formatDecimals(decimal,"#0.00");
    }
    /**
     * 保留n位小数（pattern用于确定保留的小数位数）
     */
    public static String formatDecimals(float decimal,String pattern){
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(decimal);
    }

    // 校验Tag Alias 只能是数字,英文字母和中文
    public static boolean isValidTagAndAlias(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_!@#$&*+=.|]+$");
        Matcher m = p.matcher(s);
        return m.matches();
    }
}
