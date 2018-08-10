package com.cloth.kernel.service.http;

import android.support.annotation.Nullable;

import com.cloth.kernel.service.LoggerWrapper;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;

import okhttp3.Call;
import okhttp3.EventListener;
import okhttp3.Handshake;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

public class EventListenerImpl extends EventListener {

    private long start_connect_time;
    private String connect_time;

    private long start_dns_time;
    private String dns_time;

    private long start_tls_time;
    private String tls_time;

    private long start_read_time;
    private String read_time;

    private long start_request_time;
    private String request_time;

    private String up_header_size;
    private String up_body_size;
    private String down_header_size;
    private String down_body_size;
    private String request_url;
    private String code;

    private long start_all_time;
    private String all_time;

//    private HttpClient.NetListener mCallBack;
//
//    public EventListenerImpl(HttpClient.NetListener call) {
//        this.mCallBack = call;
//    }
    @Override
    public void callStart(Call call) {
        super.callStart(call);
        start_all_time  = System.currentTimeMillis();
        request_url = call.request().url().toString();
    }

    @Override
    public void dnsStart(Call call, String domainName) {
        super.dnsStart(call, domainName);
        start_dns_time = System.currentTimeMillis();
    }

    @Override
    public void dnsEnd(Call call, String domainName, List<InetAddress> inetAddressList) {
        super.dnsEnd(call, domainName, inetAddressList);
        dns_time = String.valueOf(System.currentTimeMillis() - start_dns_time);
    }

    @Override
    public void connectStart(Call call, InetSocketAddress inetSocketAddress, Proxy proxy) {
        super.connectStart(call, inetSocketAddress, proxy);
        start_connect_time = System.currentTimeMillis();
    }

    @Override
    public void secureConnectStart(Call call) {
        super.secureConnectStart(call);
        start_tls_time = System.currentTimeMillis();
    }

    @Override
    public void secureConnectEnd(Call call, @Nullable Handshake handshake) {
        super.secureConnectEnd(call, handshake);
        tls_time = String.valueOf(System.currentTimeMillis() - start_tls_time);
    }

    @Override
    public void connectEnd(Call call, InetSocketAddress inetSocketAddress, Proxy proxy, @Nullable Protocol protocol) {
        super.connectEnd(call, inetSocketAddress, proxy, protocol);
        connect_time = String.valueOf(System.currentTimeMillis() - start_connect_time );
    }

    @Override
    public void connectFailed(Call call, InetSocketAddress inetSocketAddress, Proxy proxy, @Nullable Protocol protocol, IOException ioe) {
        super.connectFailed(call, inetSocketAddress, proxy, protocol, ioe);
        connect_time = String.valueOf(System.currentTimeMillis() - start_connect_time);
    }


    @Override
    public void requestHeadersStart(Call call) {
        super.requestHeadersStart(call);
        start_request_time = System.currentTimeMillis();
    }

    @Override
    public void requestHeadersEnd(Call call, Request request) {
        super.requestHeadersEnd(call, request);
        up_header_size = String.valueOf(request.headers().toString().length());
    }

    @Override
    public void requestBodyEnd(Call call, long byteCount) {
        super.requestBodyEnd(call, byteCount);
        up_body_size = String.valueOf(byteCount);
    }

    @Override
    public void responseHeadersStart(Call call) {
        super.responseHeadersStart(call);
        request_time = String.valueOf(System.currentTimeMillis() - start_request_time);
        start_read_time = System.currentTimeMillis();
    }

    @Override
    public void responseHeadersEnd(Call call, Response response) {
        super.responseHeadersEnd(call, response);
        down_header_size = String.valueOf(response.headers().toString().length());
        code = String.valueOf(response.code());
    }

    @Override
    public void responseBodyStart(Call call) {
        super.responseBodyStart(call);
    }

    @Override
    public void responseBodyEnd(Call call, long byteCount) {
        super.responseBodyEnd(call, byteCount);
        down_body_size = String.valueOf(byteCount);
        read_time = String.valueOf(System.currentTimeMillis() - start_read_time);
    }

    @Override
    public void callEnd(Call call) {
        super.callEnd(call);
        all_time = String.valueOf(System.currentTimeMillis() - start_all_time);
        collection();
    }

    @Override
    public void callFailed(Call call, IOException ioe) {
        super.callFailed(call, ioe);
        all_time = String.valueOf(System.currentTimeMillis() - start_all_time);
        collection();
    }


    private void collection(){
//        if (mCallBack !=null) {
//            mCallBack.network(new NetWorkModel(connect_time,dns_time,tls_time,read_time,request_time,up_header_size,up_body_size,down_header_size,down_body_size,request_url,code,all_time));
//        }
        LoggerWrapper.d("connect_time"+connect_time+"dns_time"+dns_time+"tls_time"+tls_time+"read_time"+read_time+"request_time"+request_time+
                "up_header_size"+up_header_size+"up_body_size"+up_body_size+"down_header_size"+down_header_size+"down_body_size"+down_body_size+"request_url"+request_url+"code"+code+"all_time"+all_time);

    }


    public class NetWorkModel{
        public String connect_time;
        public String dns_time;
        public String tls_time;
        public String read_time;
        public String request_time;
        public String up_header_size;
        public String up_body_size;
        public String down_header_size;
        public String down_body_size;
        public String request_url;
        public String code;
        public String all_time;

        public NetWorkModel(String connect_time, String dns_time, String tls_time, String read_time, String request_time, String up_header_size, String up_body_size, String down_header_size, String down_body_size, String request_url, String code, String all_time) {
            this.connect_time = connect_time;
            this.dns_time = dns_time;
            this.tls_time = tls_time;
            this.read_time = read_time;
            this.request_time = request_time;
            this.up_header_size = up_header_size;
            this.up_body_size = up_body_size;
            this.down_header_size = down_header_size;
            this.down_body_size = down_body_size;
            this.request_url = request_url;
            this.code = code;
            this.all_time = all_time;
        }
    }

}
