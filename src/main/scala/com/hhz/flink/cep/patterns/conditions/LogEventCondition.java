package com.hhz.flink.cep.patterns.conditions;


import com.googlecode.aviator.AviatorEvaluator;
import com.hhz.flink.cep.patterns.Obj2Map;
import com.hhz.flink.cep.patterns.aviator.GetFieldFunction;
import com.hhz.flink.cep.pojo.LoginEvent;
import org.apache.flink.cep.pattern.conditions.SimpleCondition;

import java.io.Serializable;
import java.util.Map;

public class LogEventCondition  extends SimpleCondition<LoginEvent> implements Serializable {
    private String script;

    static {
        AviatorEvaluator.addFunction(new GetFieldFunction());
    }

    //getField(uid)=\"uid\"
    public LogEventCondition(String script){
        this.script = script;
    }

    @Override
    public boolean filter(LoginEvent value) throws Exception {
        Map<String, Object> stringObjectMap = Obj2Map.objectToMap(value);
        boolean result = (Boolean) AviatorEvaluator.execute(script, stringObjectMap);
        return result;
    }

}