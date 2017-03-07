package com.eversharp.commons.exception;

import java.util.HashMap;
import java.util.Map;

public class ServiceException
        extends Exception {
    
    private static final long          serialVersionUID        = -4378260409429624322L;
    
    private static Map<String, String> exceptionMap            = new HashMap<String, String>();
    
    public static final String        AccessException         = "s1000";
    
    public static final String        JSONException           = "s1001";
    
    public static final String        IOException             = "s1002";
    
    public static final String        ParseException          = "s1003";
    
    public static final String        URISyntaxException      = "s1004";
    
    public static final String        ServerLogicException    = "s1005";
    
    public static final String        ClientProtocolException = "s1006";
    
    public static String getClientprotocolexception() {
        return ClientProtocolException;
    }

    static {
        exceptionMap.put(AccessException, "数据访问异常");
        exceptionMap.put(JSONException, "json解析异常");
        exceptionMap.put(IOException, "文件连接异常");
        exceptionMap.put(ParseException, "参数解析错误");
        exceptionMap.put(URISyntaxException, "参数错误!");
        exceptionMap.put(ServerLogicException, "逻辑错误");
        exceptionMap.put(ClientProtocolException, "通信协议错误");
    }
    
    public static String getParseexception() {
        return ParseException;
    }
    
    public static String getServerlogicexception() {
        return ServerLogicException;
    }
    
    private String exceptionCode;
    
    public ServiceException() {
        super();
    }
    
    public ServiceException(Exception e) {
        super(e);
    }
    
    public static String getUrisyntaxexception() {
        return URISyntaxException;
    }
    
    public static String getIoexception() {
        return IOException;
    }
    
    public ServiceException(String exceptionCode) {
        super(exceptionCode);
        this.exceptionCode = exceptionCode;
    }
    
    public ServiceException(String exceptionCode, String errorMsg) {
        super(exceptionCode);
        this.exceptionCode = exceptionCode;
        exceptionMap.put(exceptionCode, errorMsg);
    }
    
    @Override
    public String getMessage() {
        if (exceptionMap.containsKey(exceptionCode)) {
            return exceptionMap.get(exceptionCode).toString();
        }
        return "未知错误";
    }
}
