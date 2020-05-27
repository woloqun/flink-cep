package com.hhz.flink.cep.patterns;

import org.apache.flink.cep.scala.pattern.Pattern;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public abstract class AbstractPattern<T> implements IPattern<T> {

    public String pattern;
    private Class<T> clazz;

    @Override
    public abstract Pattern<T, T> pattern() throws Exception;

    public abstract String begin();

    public abstract String content();

    public abstract String end();

    public String generate() {
        return begin() +
                content() +
                end();
    }

    public String patternMid(String mid) {
//        String pattern = "where\\((_.*?)\\)";
        String pattern = "where\\((.*?)\\)";
        java.util.regex.Pattern r = java.util.regex.Pattern.compile(pattern);

        Matcher m = r.matcher(mid);
        List<String> list = new ArrayList<String>();

        while (m.find()) {
            list.add(m.group(1).trim());
        }

        String[] split = mid.split(pattern);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            sb.append(split[i]);
            if (i < split.length - 1) {
                try {
                    sb.append("where(");
                    sb.append(conditons(list.get(i)));
                    sb.append(")");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }


    public String conditons(String where) throws Exception {


        String conditionSplit = getConditionSplit(where);
        String[] wheres = null;
        if (conditionSplit == null) {
            wheres = new String[]{where};
        } else {
            wheres = where.split(conditionSplit);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("new LogEventCondition(\"");
        int c = 0;
        for (String w : wheres) {
            if (conditionSplit != null && c > 0) {
                sb.append(" ");
                sb.append(conditionSplit.replaceAll("\\\\", ""));
                sb.append(" ");
            }
            sb.append(condition(w));
            c++;
        }

        sb.append("\")");

        return sb.toString();
    }

    public String condition(String where) throws Exception {
        if (this.clazz == null) getRealType();
        String expression = getExpression(where);
        String[] split = where.split(expression);
        String fieldName = split[0].trim();
        Class<?> type = null;
        if (fieldName.contains(".")) {
            if (fieldName.contains(":")) {
                String[] a = fieldName.split(":");
                fieldName = a[0];
                type = fieldType(a[1]);
            } else {
                type = String.class;
            }
        } else {
            Field field = this.clazz.getDeclaredField(fieldName);
            type = field.getType();
        }

        String value = split[1].trim();
        return spliceConditions(fieldName, type, value, expression);
    }

    public Class fieldType(String type) {
        type = type.trim().toLowerCase();
        if ("int".equals(type)) {
            return Integer.TYPE;
        } else if ("bigint".equals(type) || "long".equals(type)) {
            return Long.TYPE;
        } else if ("float".equals(type) || "double".equals(type)) {
            return Double.TYPE;
        }

        return String.class;

    }

    //拼接算术表达式
    public String spliceConditions(String fieldName, Class fieldType, String value, String expression) {
        StringBuilder sb = new StringBuilder();


        if (fieldType == String.class) {
            sb.append("getString(").append(fieldName).append(")")
                    .append(expression)
                    .append("'")
                    .append(value)
                    .append("'");

        } else if (fieldType == Long.TYPE) {
            sb.append("getLong(").append(fieldName).append(")")
                    .append(expression)
                    .append(value);

        } else if (fieldType == Integer.TYPE) {
            sb.append("getInt(").append(fieldName).append(")")
                    .append(expression)
                    .append(value);
        } else if (fieldType == Double.TYPE) {
            sb.append("getDouble(").append(fieldName).append(")")
                    .append(expression)
                    .append(value);
        }

        return sb.toString();
    }


    public static String getConditionSplit(String where) {
        if (where.contains("&&")) {
            return "&&";
        } else if (where.contains("||")) {
            return "\\|\\|";
        }

        return null;
    }

    //提取算式符（>,>=,<,<=,==）
    public String getExpression(String where) {
        String expression = "";
        if (where.contains("==")) {
            expression = "==";
        } else if (where.contains(">")) {
            if (where.contains(">=")) {
                expression = ">=";
            } else {
                expression = ">";
            }

        } else if (where.contains("<")) {
            if (where.contains("<=")) {
                expression = "<=";
            } else {
                expression = "<";
            }
        } else if (where.contains("!=")) {
            expression = "!=";
        }

        return expression;
    }


    public Invocable invocable(String script) throws Exception {
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("groovy");
        engine.eval(script);
        Invocable inv = (Invocable) engine;
        return inv;
    }


    public Class getRealType() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.clazz = (Class<T>) pt.getActualTypeArguments()[0];
        return clazz;
    }

}
