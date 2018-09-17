package com.spark.practice;

import java.util.Arrays;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class Spark2WordCount {

	public static void main(String[] args) {
		SparkSession spark = SparkSession.builder().appName("WordCount").master("local").getOrCreate();
		Dataset<String> dataSet = spark.read().textFile("/Users/gireesh/Documents/workspace/input/word.txt");
		dataSet.show();
		Dataset<String> words = dataSet.flatMap(word -> { return Arrays.asList(word.split(" ")).iterator();}, Encoders.STRING());
		Dataset<Row> count = words.groupBy(words.col("value")).count().toDF("Word", "Count");
		count.show();
	}

}
