package com.dataworks.spark.rdd.pair

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Sandeep on 10/28/16.
  */
object rightOuterJoinEx {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("rightOuterJoinEx").setMaster("local")
    val sc: SparkContext = new SparkContext(conf)
    sc.setLogLevel("OFF")

    rightJoinEx(sc)
  }

  def rightJoinEx(sc: SparkContext): Unit = {
    val thisRDD: RDD[String] = sc.textFile("/Users/Sandeep/dataworks/data/this")
      .filter(x => !x.isEmpty)
    val thatRDD: RDD[String] = sc.textFile("/Users/Sandeep/dataworks/data/that")
      .filter(x => !x.isEmpty)

    val thisPair: RDD[(Int, String)] = thisRDD.flatMap(x => x.split(" ")).map(x => (x.length, x))
    val thatPair: RDD[(Int, String)] = thatRDD.flatMap(x => x.split(" ")).map(x => (x.length, x))

    val rightJoinedRdd: RDD[(Int, (Option[String], String))] = thisPair.rightOuterJoin(thatPair)
    rightJoinedRdd.foreach(x => println(x))
  }

}
