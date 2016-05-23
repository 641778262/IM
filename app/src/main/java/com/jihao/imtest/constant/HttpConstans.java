package com.jihao.imtest.constant;

/**
 * Created by jiahao on 16/5/23.
 */
public class HttpConstans {
    public static boolean DEBUG = true;
    public static final String TEST_URL = "http://test.jiahao.me";
//    public static final String TEST_URL = "http://test2.jiahao.me";
    public static final String URL= "http://quyun.jiahao.me";
    public static String BASE_URL;
    static {
        BASE_URL = DEBUG?TEST_URL:URL;
    }


    public static final String LOGIN = "/user/login";
}
