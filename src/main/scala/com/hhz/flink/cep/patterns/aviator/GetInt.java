package com.hhz.flink.cep.patterns.aviator;

import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorBigInt;
import com.googlecode.aviator.runtime.type.AviatorJavaType;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.util.Map;

public class GetInt extends HhzFieldFunction {

    @Override
    public String getName() {
        return "getInt";
    }

    @Override
    public AviatorBigInt call(Map<String, Object> params, AviatorObject arg1) {
        AviatorJavaType field = (AviatorJavaType)arg1;
        String name = field.getName();

        if(name.contains(".")){
            return new AviatorBigInt(Long.parseLong(jsonValue(name, params).trim()));
        }
        Number numberValue = FunctionUtils.getNumberValue(arg1, params);
        return new AviatorBigInt(numberValue.intValue());
    }


}