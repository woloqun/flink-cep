package com.hhz.flink.cep.patterns.aviator;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorString;

import java.util.Map;

public class GetFieldFunction extends AbstractFunction {

    @Override
    public String getName() {
        return "getField";
    }

    @Override
    public AviatorString call(Map<String, Object> params, AviatorObject arg1) {
        String stringValue = FunctionUtils.getStringValue(arg1, params);
        return new AviatorString(stringValue);
    }

/*
    public static void main(String[] args) {
        AviatorEvaluator.addFunction(new GetFieldFunction());
        Map<String,Object> m = new HashMap<String, Object>();
        m.put("uid","uid");
        System.out.println(AviatorEvaluator.execute("getField(eventType)==\"uid\"", m));
    }*/
}
