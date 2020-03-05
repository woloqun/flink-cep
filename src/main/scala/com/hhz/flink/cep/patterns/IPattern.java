package com.hhz.flink.cep.patterns;

import org.apache.flink.cep.scala.pattern.Pattern;

import java.io.Serializable;

interface IPattern<T> extends Serializable {

    public Pattern<T, T> pattern()throws Exception;

}