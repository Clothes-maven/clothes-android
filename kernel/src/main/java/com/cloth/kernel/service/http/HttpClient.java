package com.cloth.kernel.service.http;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.cloth.kernel.service.http.converter.DecodeConverterFactory;
import com.cloth.kernel.service.http.httplog.HttpLogIntercepter;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.EventListener;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class HttpClient {

    /**
     * 最大链接数 default：64
     */
    private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 64;
    /**
     * 最大空闲链接数
     */
    private static final int MAX_IDLE_CONNECTIONS = 10;
    /**
     * 每个host对应的连接数
     */
    private static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 8;
    /**
     * 空闲连接数生存时间 （分钟）
     */
    private static final long KEEP_ALIVE_DURATION = 5;
    /**
     * 默认失败重试次数
     */
    private static final int RETRY_TIME = 2;
    /*超时时间 默认6秒*/
    private static final int CONNECT_TIME = 10;
    /*读超时 默认10s*/
    private static final int READ_TIME = 10;
    /*写超时 默认10s*/
    private static final int WRITE_TIME = 10;


    final String mBaseurl;

    private Retrofit retrofit;

    private Retrofit getRetrofit() {
        return retrofit;
    }

    public String getBaseurl() {
        return mBaseurl;
    }


    public HttpClient(Builder builder) {
        this.mBaseurl = builder.baseUrl;
        OkHttpClient.Builder clientBuilder = builder.mClientBuilder;
        //同步异步
        RxJava2CallAdapterFactory adapterFactory;
        if (builder.isAsync()) {
           adapterFactory = RxJava2CallAdapterFactory.createAsync();
        } else {
            adapterFactory = RxJava2CallAdapterFactory.create();
        }
        //代理类
        if (builder.ip !=null && builder.ip.length() !=0 && builder.port>0) {
            clientBuilder.proxy(new Proxy(Proxy.Type.HTTP,new InetSocketAddress(builder.ip, builder.port)));
        }

        OkHttpClient okHttpClient = clientBuilder.build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(DecodeConverterFactory.create())
                .addCallAdapterFactory(adapterFactory)
                .baseUrl(mBaseurl)
                .build();
    }

    public <T> T exec(final Class<T> service) {

        if (getRetrofit() ==null) {
            throw new IllegalStateException("请初始化retrofit");
        }
        return getRetrofit().create(service);
    }


    @NonNull
    private static OkHttpClient.Builder getOkHttpClientBuilder() {

        //设置连接池数
        ConnectionPool connectionPool = new ConnectionPool(MAX_IDLE_CONNECTIONS, KEEP_ALIVE_DURATION, TimeUnit.MINUTES);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectionPool(connectionPool);

        //设置并发数
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(DEFAULT_MAX_TOTAL_CONNECTIONS);
        dispatcher.setMaxRequestsPerHost(DEFAULT_MAX_CONNECTIONS_PER_ROUTE);
        builder.dispatcher(dispatcher);

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLogIntercepter());
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(httpLoggingInterceptor);

        builder.eventListenerFactory(new EventListener.Factory() {
            @Override
            public EventListener create(@NonNull Call call) {
                return new EventListenerImpl();
            }
        });
        return builder;
    }


    public static final class Builder {
        String baseUrl;
        boolean isAsync =true;
        OkHttpClient.Builder mClientBuilder =getOkHttpClientBuilder();
        long connectTime = CONNECT_TIME;
        long readTime = READ_TIME;
        long writeTime = WRITE_TIME;
        String ip;
        int port;


        public Builder() {
        }

        public long getReadTime() {
            return readTime;
        }

        public Builder readTime(long readTime) {
            this.readTime = readTime;
            return this;
        }

        public long getWriteTime() {
            return writeTime;
        }

        public Builder writeTime(long writeTime) {
            this.writeTime = writeTime;
            return this;
        }

        public long getConnectTime() {
            return connectTime;
        }

        public Builder connectTime(long connectTime) {
            this.connectTime = connectTime;
            return this;
        }

        public OkHttpClient.Builder getClientBuilder() {
            return mClientBuilder;
        }

        public Builder clientBuilder(OkHttpClient.Builder clientBuilder) {
            mClientBuilder = clientBuilder;
            return this;
        }

        public boolean isAsync() {
            return isAsync;
        }

        public Builder async(boolean async) {
            isAsync = async;
            return this;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public String getIp() {
            return ip;
        }

        public Builder ipPort(String ip,int port) {
            this.ip = ip;
            this.port = port;
            return this;
        }

        public int getPort() {
            return port;
        }


        public HttpClient build() {
            //手动创建一个OkHttpClient并设置超时时间缓存等设置
            mClientBuilder.connectTimeout(connectTime, TimeUnit.SECONDS);
            mClientBuilder.readTimeout(readTime, TimeUnit.SECONDS);
            mClientBuilder.writeTimeout(writeTime, TimeUnit.SECONDS);
            return new HttpClient(this);
        }
    }


}
