package com.pdmi.util;

public class MathUtil {

	public static long randomLong(long min, long max) {
		return (long) (min + Math.random() * (max - min + 1));
	}

}
