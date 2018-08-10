package com.cloth.kernel.service.http.converter;

import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.cloth.kernel.service.LoggerWrapper;
import com.cloth.kernel.service.http.model.BaseResponse;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.InvalidParameterException;

import okhttp3.ResponseBody;
import retrofit2.Converter;



class DecodeResponseBodyConverter<T> implements Converter<ResponseBody, BaseResponse<T>> {
    private static final Feature[] EMPTY_SERIALIZER_FEATURES = new Feature[0];
    private Type mType;
    private ParserConfig config;
    private int featureValues;
    private Feature[] features;

    public DecodeResponseBodyConverter(Type type, ParserConfig config, int featureValues,
                                       Feature... features) {
        mType = type;
        this.config = config;
        this.featureValues = featureValues;
        this.features = features;
    }

    @Override
    public BaseResponse<T> convert(@NonNull ResponseBody responseBody) throws IOException {
        String response = responseBody.string();

        BaseResponse<T> baseResponse;
        try {
            baseResponse = JSON.parseObject(response, mType, config, featureValues,
                    features != null ? features : EMPTY_SERIALIZER_FEATURES);
        } catch (Exception e) {
            e.printStackTrace();
            /*
            当服务端返回数据content跟传过来的mType
            不符合时，会解析异常，在这里再采用泛型解析，保证数据能正常解析
            */
            try {
                baseResponse = JSON.parseObject(response, new TypeReference<BaseResponse<T>>() {});
            } catch (Exception ex) {
                e.printStackTrace();
                LoggerWrapper.d(ex.getMessage());
                throw new InvalidParameterException("json解析异常1");
            }
        } finally {
            responseBody.close();
        }

        if(baseResponse == null){
            throw new InvalidParameterException("json解析异常2");
        }
        return  baseResponse;
    }
}
