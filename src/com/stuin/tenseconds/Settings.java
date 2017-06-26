package com.stuin.tenseconds;
import java.util.*;
import android.content.*;
import android.preference.*;

public class Settings
{
	private static Map<String, Boolean> values = new HashMap<>();
	private static Map<Integer, String> ids = new HashMap<>();
	private static String[] KEYS;
	private static SharedPreferences preferences;
	
	public static void Load(SharedPreferences preferences, String[] KEYS) {
		Settings.preferences = preferences;
		Settings.KEYS = KEYS;
		
		for(String key : KEYS) {
			values.put(key, preferences.getBoolean(key, false));
		}
	}
	
	public static void Set(String key, boolean value) {
		if(values.containsKey(key)) {
			preferences.edit().putBoolean(key, value).apply();
			values.remove(key);
			values.put(key, value);
		}
	}
	
	public static boolean Get(String key) {
		return values.get(key);
	}
	
	public static boolean LinkId(int id, String key) {
		ids.put(id, key);
		return Get(key);
	}
	
	public static void SetId(int id, boolean value) {
		if(ids.containsKey(id)) {
			Set(ids.get(id), value);
		}
	}
}
