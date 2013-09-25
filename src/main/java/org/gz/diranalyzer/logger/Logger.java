package org.gz.diranalyzer.logger;

public class Logger {
	
	public static void info(String message){
		System.out.println(message);
	}
	
	public static void error(String message){
		System.err.println(message);
	}
	
	public static void debug(boolean isDebug, String message){
		if(isDebug){
			System.out.println(message);
		}
	}
}
