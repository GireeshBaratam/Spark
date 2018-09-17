package com.spark.practice;

public class Test {
	
	private static int getNumberOfPossibilities(String input){
		int count = 0;
		char[] inputArray = input.toCharArray();
		int val = Integer.parseInt(input);
		if(inputArray.length==1){
			return count+1;
		}else if(val<27){
			return count+2;
		}
		
		else if(inputArray.length>1){
			count =1;
			int rem = val%26;
			char[] remArray = String.valueOf(rem).toCharArray();
			if(rem<10 && remArray.length==1){
				return count;
			}
			else{
				return count+2;
			}
		}
	
		return count;
	}

	public static void main(String[] args) {
		String input ="12";
		System.out.println(Test.getNumberOfPossibilities(input));

	}

}
