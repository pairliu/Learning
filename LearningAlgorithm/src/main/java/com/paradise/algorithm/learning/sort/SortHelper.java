package com.paradise.algorithm.learning.sort;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class SortHelper {
	public static void sortFor(Class clazz, String sortName) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		int[] list = {2, 4, 33, 7, 1, 4, 9, 4, 9, 2, 78, 67, 23, 6};
		Method m = clazz.getMethod(sortName, int[].class);
		System.out.println(m);
		m.invoke(clazz.newInstance(), list);
		printWithSpace(list);
	}
	
	public static void appendSpace(int a) {   //’‚”–µ„œÒdecorator
		System.out.print(a + " ");
	}
	
	public static void printWithSpace(int[] list) {
		Arrays.stream(list).forEach(SortHelper::appendSpace);
		System.out.println();
	}
	
}
