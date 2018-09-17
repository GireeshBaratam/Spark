package com.spark.practice;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.types.DataTypes;

import scala.Tuple2;

public class SparkPrimeCheckWithUDF {

	public static void main(String[] args) {
		SparkSession spark = SparkSession.builder().appName("UserDefinedUDF").master("local").getOrCreate();
		List<Integer> intList = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
		Dataset<Integer> dataSet = spark.createDataset(intList, Encoders.INT());
		dataSet.show();
		spark.udf().register("SparkprimecheckUDF", new SparkCustomUDF(), DataTypes.BooleanType);
		dataSet.withColumn("value", functions.callUDF("SparkprimecheckUDF", dataSet.col("value"))).show();
		JavaPairRDD<Integer, Integer> pair = dataSet.toJavaRDD().mapToPair(x -> new Tuple2<Integer, Integer>(x, x*x));
		pair.foreach(data -> System.out.println("Number="+ data._1 + " Square=" + data._2));
	}

}
