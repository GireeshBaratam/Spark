package com.spark.practice;

import org.apache.spark.sql.api.java.UDF1;

public class SparkCustomUDF implements UDF1<Integer, Boolean>{
	
	@Override
	public Boolean call(Integer num){
		int count=0;
		Boolean res = false;
		
		for(int i=1; i<=num;i++){
			if(num%i==0){
				count++;
			}
		}
			if(count==2){
				res = true;
				return res;
			}
		return res;
		
	}

}
