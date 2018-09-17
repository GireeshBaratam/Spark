package com.spark.practice;

import java.util.Arrays;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;

import scala.Tuple2;

public class SparkWordCount {
	
	private static void setLogger(){
		 // creates pattern layout
        PatternLayout layout = new PatternLayout();
        String conversionPattern = "%-7p %d [%t] %c %x - %m%n";
        layout.setConversionPattern(conversionPattern);
        FileAppender fileAppender = new FileAppender();
        fileAppender.setFile("applog3.txt");
        fileAppender.setLayout(layout);
        fileAppender.activateOptions();
 
        // configures the root logger
        Logger rootLogger = Logger.getRootLogger();
        rootLogger.setLevel(Level.DEBUG);
        rootLogger.addAppender(fileAppender);
	}
	
	
	public static void main(String ar[]){
		SparkConf conf = new SparkConf().setAppName("SparkWordCount").setMaster("local");
		JavaSparkContext context = new JavaSparkContext(conf);
		JavaRDD<String> textFileLoad = context.textFile("/Users/gireesh/Documents/workspace/input/word.txt");
		 // Split in to list of words
		  JavaRDD <String> words = textFileLoad.flatMap(l -> Arrays.asList(l.split(" ")).iterator());
		  // Transform into pairs and count.
		  JavaPairRDD < String, Integer > pairs = words.mapToPair(w -> new Tuple2(w, 1));
		  JavaPairRDD < String, Integer > counts = pairs.reduceByKey((x, y) -> x + y);
		  System.out.println(counts.collect());
		  SQLContext sqlContext = new SQLContext(context);
		  Dataset set = sqlContext.read().format("jdbc").option("url", "jdbc:mysql://localhost:3306/gireesh").option("driver", "com.mysql.jdbc.Driver").option("dbtable", "emp").option("user", "root").option("password", "baratam@1387").load();
		  set.createOrReplaceTempView("emp");
		  Dataset set1 = sqlContext.sql("select * from emp");
		  set1.show();
		  SparkSession spark = SparkSession.builder().appName("Build a DataFrame from Scratch").master("local[*]")
		            .getOrCreate();
		  set1.write().option("header", true).csv("/Users/gireesh/Documents/workspace/output");
		  
		  
	}

}
