package com.hhz.flink.cep.patterns.aviator;

import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.*;

import java.util.Map;

public class GetLong extends HhzFieldFunction {

    @Override
    public String getName() {
        return "getLong";
    }

    @Override
    public AviatorBigInt call(Map<String, Object> params, AviatorObject arg1) {

        AviatorJavaType field = (AviatorJavaType)arg1;
        String name = field.getName();

        if(name.contains(".")){
            return new AviatorBigInt(Long.parseLong(jsonValue(name, params).trim()));
        }
        Number numberValue = FunctionUtils.getNumberValue(arg1, params);
        return new AviatorBigInt(numberValue.longValue());
    }


}