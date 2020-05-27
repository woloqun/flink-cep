package com.hhz.flink.cep.patterns.aviator;

import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorJavaType;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorString;

import java.util.Map;

public class GetString  extends HhzFieldFunction {

    @Override
    public String getName() {
        return "getString";
    }

    @Override
    public AviatorObject call(Map<String, Object> params, AviatorObject arg1) {
        AviatorJavaType field = (AviatorJavaType)arg1;
        String name = field.getName();

        if(name.contains(".")){
            return new AviatorString(jsonValue(name, params));
        }

        String stringValue = FunctionUtils.getStringValue(arg1, params);
        return new AviatorString(stringValue);
    }

}
