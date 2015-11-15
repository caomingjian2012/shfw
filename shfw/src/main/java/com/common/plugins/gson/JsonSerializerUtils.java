package com.common.plugins.gson;

import java.sql.Timestamp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonSerializerUtils {
	private static Gson gson;
	static {
		gson = new GsonBuilder()
		.registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter())
		.setDateFormat("yyyy-MM-dd HH:mm:ss")
		.create();
	}
	
	public static String toJson(Object object) {
		return gson.toJson(object);
	}

	
}
