package com.cody.codystage.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cody.codystage.common.constants.ResConstants;
import com.cody.codystage.common.exception.ServiceException;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class RequestUtil {

    public static boolean isAjaxRequest(HttpServletRequest request) {
        boolean ajaxBool = false;
        if (request.getHeader("x-requested-with") != null
                && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
            ajaxBool = true;
        }

        return ajaxBool;
    }

    /**
     * 从request中拿参数(过滤所有有可能会产生XSS攻击的特殊字符) 如果参数在request中不存在，则返回defaultValue
     *
     * @param request
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getString(HttpServletRequest request, String key, String defaultValue) {
        String value = request.getParameter(key);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return escapeHTMLTag(value.trim());
    }

    /**
     * 使HTML的标签失去作用*
     *
     * @param input 被操作的字符串
     * @return String
     */
    public static final String escapeHTMLTag(String input) {
        if (input == null)
            return "";
        input = input.trim().replaceAll("&", "&amp;");
        input = input.replaceAll("<", "&lt;");
        input = input.replaceAll(">", "&gt;");
        input = input.replaceAll("\n", "<br>");
        input = input.replaceAll("'", "&#39;");
        input = input.replaceAll("\"", "&quot;");
        input = input.replaceAll("\\\\", "&#92;");
        return input;

    }

    /**
     * 从request中拿参数(过滤所有有可能会产生XSS攻击的特殊字符) 如果参数在request中不存在，则返回defaultValue
     *
     * @param request
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getStringEscapSQL(HttpServletRequest request, String key, String defaultValue) {
        String value = request.getParameter(key);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return escapeSQLRex(value.trim());
    }

    public static String escapeSQLRex(String searchKey) {
        return searchKey.replace("\\", "\\\\")
                .replace("%", "\\%")
                .replace("'", "\\'")
                .replace("_", "\\_");
    }


    /**
     * 将该参数转为一个ｍａｐ型数组,or直接用json转换?
     *
     * @param request
     * @param key
     * @param defaultValue
     * @return
     */
    public static List getStringToMapList(HttpServletRequest request, String key, List defaultValue) throws Exception {
        String adPlayers = request.getParameter(key);
        if (StringUtils.isEmpty(adPlayers) || adPlayers.equals("-1")) {
            return defaultValue;
        } else {
            return JSON.parseArray(adPlayers);
        }

    }

    /**
     * 从request中拿参数不进行特殊字符过滤
     *
     * @param request
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getStringNoEscape(HttpServletRequest request, String key, String defaultValue) {
        String value = request.getParameter(key);
        if (StringUtils.isBlank(value)) {
            value = defaultValue;
        }
        return value.trim();
    }

    /**
     * 为textarea进行录入的方法
     *
     * @param request
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getStringFromAreaText(HttpServletRequest request, String key, String defaultValue) {
        String value = request.getParameter(key);
        if (value == null || "".equals(value.trim())) {
            return defaultValue;
        }

        return parserToHTMLForTextArea(value);
    }

    public static String parserToHTMLForTextArea(String str) {
        String result = escapeHTMLTag(str);
        result = result.replaceAll(" ", "&ensp;");
        return result;
    }

    public static Integer getInt(HttpServletRequest request, String key, Integer defaultValue) {
        String value = request.getParameter(key);
        if (value == null || "".equals(value.trim())) {
            return defaultValue;
        }
        return Integer.valueOf(value.trim());
    }

    public static int[] getIntArray(HttpServletRequest request, String key, int[] defaultArray) {
        String[] str = request.getParameterValues(key);
        if (str == null) {
            return defaultArray;
        }
        int[] arr = new int[str.length];
        for (int i = 0; i < str.length; i++) {
            arr[i] = Integer.valueOf(str[i]);
        }
        return arr;
    }

    public static int[] getStringToIntArray(HttpServletRequest request, String key, int[] defaultArray) {
        String str = request.getParameter(key);
        if (isBlank(str) || "-1".equals(str)) {
            return defaultArray;
        }
        String[] strs = str.split(",");
        int[] arr = new int[strs.length];
        for (int i = 0; i < strs.length; i++) {
            arr[i] = Integer.valueOf(strs[i].trim());
        }
        return arr;
    }

    /**
     * 判断指定的字符串是否是空串
     */
    public static boolean isBlank(String str) {
        if (StringUtils.isEmpty(str))
            return true;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static Double getDouble(HttpServletRequest request, String key, double defaultValue) {
        String value = request.getParameter(key);
        if (value == null || "".equals(value.trim())) {
            return defaultValue;
        }
        return Double.valueOf(value.trim());
    }

    public static Long getLong(HttpServletRequest request, String key, Long defaultValue) {
        String value = request.getParameter(key);
        if (value == null || "".equals(value.trim())) {
            return defaultValue;
        }
        return Long.valueOf(value.trim());
    }

    /**
     * 获取真实IP
     *
     * @param request
     * @return
     */
    public static String getOriginallyAddr(HttpServletRequest request) {
        String realAddr = request.getHeader("x-forwarded-for");
        if (StringUtils.isEmpty(realAddr) || StringUtils.equalsIgnoreCase(realAddr, "unknown")) {
            realAddr = request.getHeader("Proxy-Client-IP");//apache+weblogic
            if (StringUtils.isEmpty(realAddr) || StringUtils.equalsIgnoreCase(realAddr, "unknown")) {
                realAddr = request.getHeader("WL-Proxy-Client-IP");//apache+weblogic
            }
            if (StringUtils.isEmpty(realAddr) || StringUtils.equalsIgnoreCase(realAddr, "unknown")) {
                realAddr = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(realAddr) || StringUtils.equalsIgnoreCase(realAddr, "unknown")) {
                realAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(realAddr) || StringUtils.equalsIgnoreCase(realAddr, "unknown")) {
                realAddr = request.getRemoteAddr();
            }
        } else if (realAddr.length() > 15) {//代理
            String[] realAddrs = realAddr.split(",");
            for (String addr : realAddrs) {
                if (!StringUtils.equalsIgnoreCase(addr, "unknown")) {
                    realAddr = addr;
                    break;
                }
            }
        }

        return realAddr;
    }

    public static boolean isLicitIp(String paramString) {
        if ((paramString == null) || (paramString.length() == 0)) {
            return false;
        }
        String str = "^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$";
        Pattern localPattern = Pattern.compile(str);
        Matcher localMatcher = localPattern.matcher(paramString);

        return (localMatcher.find());
    }

    /**
     * 得到剔除过<html>，</html>,<script>,</script>过后的的字符串
     *
     * @param request      request
     * @param key          key
     * @param defaultValue if key not exists in request, return this value
     * @return value mapped key.
     */
    public static String getSecureString(HttpServletRequest request, String key, String defaultValue) {
        String value = getString(request, key, defaultValue);
        return Objects.nonNull(value) ? filterScript(value.trim()) : null;
    }

    /**
     * 去掉输入中的<html>，</html>,<script>,</script>过后的的字符串,把结果返回
     *
     * @param value 可以为null，则返回null
     * @return null if value is null, otherwise return after filtered value
     */
    public static String filterScript(String value) {

        if (value == null)
            return null;

        String regex = "<html>|<script>|</html>|</script>";
        // 不区分大小写
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(value);

        return matcher.replaceAll("");
    }

    /**
     * 输入一个字符串数组，返回一个剔除掉<html>，</html>,<script>,</script>的字符串数组
     * 如果输入为null，则返回null
     *
     * @param values string array
     * @return after filtered string array
     */
    public static String[] filterScript(String[] values) {
        if (values == null)
            return null;
        String[] newString = new String[values.length];
        int i = 0;
        for (String value : values) {
            String mid = filterScript(value);
            newString[i] = mid;
            i++;
        }
        return newString;
    }

    /**
     * 打印request参数
     *
     * @param request
     */
    public static void loggerParams(HttpServletRequest request) {
        log.debug("=== header:");
        Map<String, Object> header = Maps.newHashMap();
        Enumeration<String> e = request.getHeaderNames();
        while (e.hasMoreElements()) {
            String name = e.nextElement();
            String value = request.getHeader(name);
            header.put(name, value);
        }
        log.debug("header = {}", JSONObject.toJSONString(header));

        log.debug("=== params:");
        Map<String, String[]> map = request.getParameterMap();

        Map<String, Object> params = Maps.newHashMap();
        for (String key : map.keySet()) {
            String[] value = map.get(key);
            map.put(key, value);
        }
        log.debug("params = {}", JSONObject.toJSONString(params));
    }

    public static List<MultipartFile> getFiles(HttpServletRequest request, String key) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        return multipartRequest.getFiles(key);
    }

    public static MultipartFile getFile(HttpServletRequest request, String key) {
        if (isMultipartContent(request)) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            return multipartRequest.getFile(key);
        }else {
            throw new ServiceException(ResConstants.HTTP_RES_CODE_1212,ResConstants.HTTP_RES_CODE_1212_VALUE);
        }

    }

    /**
     * 判断是否是multipart/form-data请求
     *
     * @param request
     * @return
     */
    public static boolean isMultipartContent(HttpServletRequest request) {
        if (!"post".equals(request.getMethod().toLowerCase())) {
            return false;
        }
        String contentType = request.getContentType();
        if ((contentType != null) && (contentType.toLowerCase().startsWith("multipart/"))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 转换request的params为map
     *
     * @param request
     * @return
     */
    public static Map<String, Object> convertParamMap(HttpServletRequest request) {
        Map<String, Object> paramMap = Maps.newHashMap();
        Map<String, String[]> param = request.getParameterMap();
        for (String key : param.keySet()) {
            paramMap.put(key, StringUtils.join(param.get(key), ","));
        }
        return paramMap;
    }


}
