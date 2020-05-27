package com.hhz.flink.cep.test;

import com.hhz.flink.cep.Constants;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class MessageProducer {
    private static KafkaProducer<String, String> producer;
    public static String[] data = null;

    static {
        Properties kfkProperties = new Properties();
        kfkProperties.put("bootstrap.servers", Constants.BROKERS);
        kfkProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kfkProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(kfkProperties);
    }



    public static void main(String[] args) {
        long st = System.currentTimeMillis();
        for(int i=0;i<10000;i++){
            int c = i%data.length;
            String line = data[c]+(System.currentTimeMillis()/1000);
            ProducerRecord<String, String> record = new ProducerRecord<>("flinkcep", "name", line);
            producer.send(record);
        }
        producer.close();

        System.out.println((System.currentTimeMillis()-st)/1000.0);

    }


    static{
        data = new String[]{
                "5402,83.149.11.115,success,"
                ,"5692,66.249.3.15,fail,"
                ,"5692,80.149.25.29,fail,"
                ,"7233,86.226.15.75,success,"
                ,"5692,80.149.25.29,success,"
                ,"29607,66.249.73.135,success,"
                ,"1035,83.149.9.216,fail,"
                ,"1035,83.149.9.216,fail,"
                ,"1035,83.149.24.26,fail,"
                ,"7328,193.114.45.13,success,"
                ,"29607,66.249.73.135,success,"
                ,"2133,50.16.19.13,success,"
                ,"6745,66.249.73.185,success,"
                ,"76456,110.136.166.128,success,"
                ,"8345,46.105.14.53,success,"
                ,"76456,110.136.166.128,success,"
                ,"76456,110.136.166.128,success,"
                ,"76456,110.136.166.128,fail,"
                ,"76456,110.136.166.128,success,"
                ,"3464,123.125.71.35,success,"
                ,"76456,110.136.166.128,success,"
                ,"65322,50.150.204.184,success,"
                ,"23565,207.241.237.225,fail,"
                ,"8455,200.49.190.101,success,"
                ,"8455,200.49.190.100,success,"
                ,"8455,200.49.190.101,success,"
                ,"8455,200.49.190.101,success,"
                ,"32031,66.249.73.185,success,"
                ,"12018,66.249.73.135,success,"
                ,"12018,66.249.73.135,success,"
                ,"12018,66.249.73.135,success,"
                ,"21419,67.214.178.190,success,"
                ,"21419,67.214.178.190,success,"
                ,"23565,207.241.237.220,success,"
                ,"2386,46.105.14.53,success,"
                ,"23565,207.241.237.227,success,"
                ,"83419,91.177.205.119,success,"
                ,"83419,91.177.205.119,fail,"
                ,"83419,91.177.205.119,success,"
                ,"83419,91.177.205.119,fail,"
                ,"83419,91.177.205.119,success,"
                ,"83419,91.177.205.119,success,"
                ,"4325,26.249.73.15,success,"
                ,"2123,207.241.237.228,success,"
                ,"21083,207.241.237.101,success,"
                ,"13490,87.169.99.232,success,"
                ,"93765,209.85.238.199,success,"
                ,"93765,209.85.238.199,success,"
        };
    }
}
