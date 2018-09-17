package com.spark.practice;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class OlympicDataAnalysis {

	public static void main(String[] args) {
		SparkSession spark = SparkSession.builder().appName("OlympicDataAnalysis").master("local").getOrCreate();
		Dataset<Row> dataSet = spark.read().option("header", true).csv("/Users/gireesh/Documents/workspace/input/athlete_events.csv");
		dataSet.show(10);
		dataSet.filter("Medal = 'Gold'").show(10);
		Dataset<Row> filteredData = dataSet.filter((dataSet.col("Medal").contains("Gold")).or(dataSet.col("Medal").contains("Bronze").or(dataSet.col("Medal").contains("Silver")))).sort("Year");
		filteredData.groupBy("Year", "Team", "Medal", "Event").count().toDF("Year", "Team", "Medal","Event","No. of Medals").coalesce(1).write().option("header", true).csv("/Users/gireesh/Documents/workspace/input/result");
	}

}
