package com.hhz.flink.cep.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexpTest {
    public static void main(String[] args) {
        String pas = "(\"begin\").where(_.eventType == \"fail\")\n" +
                "    .next(\"next\").where(_.eventType == \"fail\")\n" +
                "      .within(Time.seconds(3))";

//        System.out.println(patternMid(pas));
    }

//    public static String patternMid(String mid){
//        String pattern = "(_.*?)==";
//        Pattern r = Pattern.compile(pattern);
//        Matcher m = r.matcher(mid);
//        Set<String> patterns = new HashSet<String>();
//        while(m.find()){
//            patterns.add(m.group(1).trim());
//        }
//
//        for(String p:patterns){
//            String[] split = p.split("\\.");
//            mid = mid.replaceAll(p,"new LogEventCondition(getField(\""+split[1]+"\")");
//        }
//
//        return mid;
//    }

}
