package com.cloth.kernel.service;

/**
 * @功能描述: http管理者，负责创建http请求
 * @页面进入路径: 无
 */

public class LcHttpClientWrapper {

    public static final String CryptorKey = "xvNWTwAkKfQ9sEUpy6kC";
    private static final String HTTP_PREFIX = "http://";
    private static final String HTTPS_PREFIX = "https://";

    /**
     * @功能描述 构造方法
     */
    private LcHttpClientWrapper() {
    }

    //获取单例
    public static LcHttpClientWrapper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     *   请求的request，可以设置请求的参数和
     *                       设置loading, toast是否显示
     *  发起http请求的总入口
     */
    public void httpDoRequest() {

    }

    /**
     * @功能描述:
     * @页面进入路径: 无
     */
    private static class SingletonHolder {
        private static final LcHttpClientWrapper INSTANCE = new LcHttpClientWrapper();
    }


}
