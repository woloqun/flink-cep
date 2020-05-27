package com.hhz.flink.cep

import com.hhz.flink.cep.patterns.conditions.LogEventCondition
import com.hhz.flink.cep.pojo.LoginEvent
import org.apache.flink.cep.scala.pattern.Pattern
import org.apache.flink.streaming.api.windowing.time.Time
def getP(){
//     return Pattern.<LoginEvent>begin("begin").where(new LogEventCondition("getField(eventType)=\"fail\""))
//            .next("next").where(new LogEventCondition("getField(eventType)=\"fail\""))
//            .within(Time.seconds(3))


    return Pattern.<LoginEvent>begin("begin").where(new LogEventCondition("getField(eventType)==\"fail\""))
            .next("next").where(new LogEventCondition("getField(eventType)==\"fail\"")).within(Time.seconds(3))


}

println('aaa')