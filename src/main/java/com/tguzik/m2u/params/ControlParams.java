package com.tguzik.m2u.params;

import java.util.HashMap;
import java.util.Map;

public class ControlParams {

	static Map<String, String> paramsMap = new HashMap<>();
	
	public static void setParams(String key, String value){
		paramsMap.put(key, value);
	}
	
	public static String getParam(String key){
		return paramsMap.get(key);
	}
}
