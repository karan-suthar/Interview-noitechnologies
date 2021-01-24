package com.noitechnologies.interview.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/*
 * Class used for storing two unique fields not duplicates are allowed 
 * for example if ("abc",123) is present then ("xyz",123) not allowed 
 * if ("abc",123) is present then ("abc" ,321) is not allowed
 */

public class MultiKeyMap<K1, K2, V> {

	private HashMap<K1, V> map1 = new HashMap<>();
	private HashMap<K2, V> map2 = new HashMap<>();

	public boolean put(K1 key1, K2 key2, V value) {
		if (map1.containsKey(key1))
			return false;
		else if (map2.containsKey(key2))
			return false;
		else {
			map1.put(key1, value);
			map2.put(key2, value);
			return true;
		}
	}

	public Map<K1, V> getMap() {
		return Collections.unmodifiableMap(map1);
	}

}
