package com.hhz.flink.cep.patterns.aviator;

import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorJavaType;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.util.Map;

public class GetDouble extends HhzFieldFunction {

    @Override
    public String getName() {
        return "getDouble";
    }

    @Override
    public AviatorDouble call(Map<String, Object> params, AviatorObject arg1) {
        AviatorJavaType field = (AviatorJavaType)arg1;
        String name = field.getName();

        if(name.contains(".")){
            return new AviatorDouble(Double.parseDouble(jsonValue(name, params).trim()));
        }
        Number numberValue = FunctionUtils.getNumberValue(arg1, params);
        return new AviatorDouble(numberValue.doubleValue());
    }

}