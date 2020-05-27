package com.hhz.flink.cep.patterns.aviator;

import com.alibaba.fastjson.JSONObject;
import com.googlecode.aviator.runtime.function.AbstractFunction;

import java.util.Map;

public abstract class HhzFieldFunction extends AbstractFunction {


    public String jsonValue(String fieldName, Map<String, Object> params){
        String[] arr = fieldName.split("\\.");
        String json = params.get(arr[0]).toString();
        JSONObject object = JSONObject.parseObject(json);
        return object.getString(arr[1]);
    }
}
