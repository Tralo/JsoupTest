package com.study;

public class Test {
	
	public static void main(String[] args) {
		String str = "background-image: url(/content/images/2018/04/android-dev-weekly-issue-173.jpg)";
		System.out.println(
				str.substring(str.indexOf("(") + 1,str.indexOf(")"))
		);
	}
	
	
}
