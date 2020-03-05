package com.hhz.flink.cep.test

import java.util

import org.apache.flink.cep.PatternSelectFunction
import org.apache.flink.cep.scala.{CEP, PatternStream}
import org.apache.flink.cep.scala.pattern.Pattern
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time


case class FlowLog(uid:Long,status:String,time:Long)
object CepTest {

  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.setParallelism(1)
    val logStream = getLogs(env)
    val kstream = logStream.assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[FlowLog](Time.seconds(3)) {
      override def extractTimestamp(element: FlowLog): Long = element.time*1000
    }).keyBy(_.uid)

//    val loginFailPattern = beginnext()
   val loginFailPattern = timespattern()
    val patternStream = CEP.pattern(kstream, loginFailPattern)
    val loginFailDataStream = patternStream.select( new FlowMatch() )
    loginFailDataStream.print("begin")


    env.execute("cep test")

  }


  def getLogs(env:StreamExecutionEnvironment): DataStream[FlowLog] ={
    val logStream = env.fromElements(
      FlowLog(1,"fail",1558430857),
      FlowLog(1,"fail",1558430858),
      FlowLog(1,"fail",1558430859)

    )

    return logStream
  }

  def beginnext(): Pattern[FlowLog,FlowLog] ={
    return Pattern.begin[FlowLog]("begin").where(_.status == "fail")
      .next("next").where(_.status == "fail")
      .within(Time.seconds(3))

  }

  def timespattern(): Pattern[FlowLog,FlowLog] ={
    return Pattern.begin[FlowLog]("begin").where(_.status == "fail")
//      .times(3)
      .times(2,3)
      .within(Time.seconds(3))

  }


}

class FlowMatch() extends PatternSelectFunction[FlowLog, util.List[FlowLog]]{
  override def select(map: util.Map[String, util.List[FlowLog]]): util.List[FlowLog] = {
    val logs = map.get("begin")
    logs
  }
}
