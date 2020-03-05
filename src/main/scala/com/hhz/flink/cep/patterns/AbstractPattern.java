package com.hhz.flink.cep.patterns;

import org.apache.flink.cep.scala.pattern.Pattern;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

public abstract class AbstractPattern<T> implements IPattern<T> {

    public String pattern;

    @Override
    public abstract Pattern<T, T> pattern() throws Exception;

    public abstract String begin();
    public abstract String content();
    public abstract String end();

    public String generate(){
        return begin()+
                content()+
                end();
    }

    public  String patternMid(String mid){
        String pattern = "where\\((_.*?)\\)";
        java.util.regex.Pattern r = java.util.regex.Pattern.compile(pattern);

        Matcher m = r.matcher(mid);
        Set<String> sets = new HashSet<String>();

        while(m.find()){
            sets.add(m.group(1).trim());
        }

        for(String s:sets){
            mid  = mid.replaceAll(s,conditons(s));
        }
        return mid;
    }

    public static String conditons(String where){
        String[] split = where.split("==");
        String field = split[0].split("\\.")[1].trim();

        String value = split[1].trim().replaceAll("\\\"","");
        StringBuilder sb = new StringBuilder();

        sb.append("new LogEventCondition(")
                .append("\"getField(").append(field).append(")")
                .append("==\\\\\"")
                .append(value)
                .append("\\\\\"")
                .append("\\\")");


        return sb.toString();

    }


    public  Invocable invocable(String script)throws Exception{
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine =  factory.getEngineByName("groovy");
        engine.eval(script);
        Invocable inv = (Invocable) engine;
        return inv;
    }


}
