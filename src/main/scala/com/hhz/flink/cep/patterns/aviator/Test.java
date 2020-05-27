package com.hhz.flink.cep.patterns.aviator;

import com.googlecode.aviator.AviatorEvaluator;

import java.util.HashMap;
import java.util.Map;

public class Test {

    public static void main(String[] args) {
        AviatorEvaluator.addFunction(new GetString());
        Map<String,Object> m = new HashMap<String, Object>();
        m.put("uid","adfadfsadf");
        System.out.println(AviatorEvaluator.execute("getString(uid)>='12' ", m));

//        String aa = "aa==11 || aa=dff || dd=add";
//        String conditionSplit = getConditionSplit(aa);
//
//        System.out.println(conditionSplit.replaceAll("\\\\",""));
//        for(String line:aa.split(conditionSplit)){
//            System.out.println(line);
//        }


//        String patternStr = "begin('begin').where(eventType == fail || eventTime>1558430826).next('next').where(eventType == fail).within(Time.seconds(3))";
//        String pattern = "where\\((.*?)\\)";
//        String[] split = patternStr.split(pattern);
//        for(String s:split){
//            System.out.println(s);
//        }

    }

    public static  String getConditionSplit(String where){
        if(where.contains("&&")){
            return "&&";
        }else if(where.contains("||")){
            return "\\|\\|";
        }

        return null;
    }
}
