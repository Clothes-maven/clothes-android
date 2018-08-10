package com.cloth.kernel.service.http.converter;

import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;


/**
 * 自下而上的类型推导过程
 */
public class DecodeConverterFactory extends Converter.Factory {
    private ParserConfig mParserConfig = ParserConfig.getGlobalInstance();
    private int featureValues = JSON.DEFAULT_PARSER_FEATURE;
    private Feature[] features;

    public static DecodeConverterFactory create() {
        return new DecodeConverterFactory();
    }

    private DecodeConverterFactory() {

    }
    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new DecodeResponseBodyConverter<>(type, mParserConfig, featureValues, features);
    }
}
