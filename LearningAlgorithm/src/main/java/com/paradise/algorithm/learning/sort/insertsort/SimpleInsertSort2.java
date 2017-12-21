package com.paradise.algorithm.learning.sort.insertsort;

import java.lang.reflect.InvocationTargetException;

import com.paradise.algorithm.learning.sort.SortHelper;

public class SimpleInsertSort2 {
	public void insertSort(int[] list) {
		for (int i = 1; i < list.length; i++) {
			int curr = list[i];
			int pos = i;
			for (int j = i; j > 0 && curr < list[j-1] ; j--) {
				list[j] = list[j-1];
				pos = j-1;
			}
			list[pos] = curr;
		}
	}
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		SortHelper.sortFor(SimpleInsertSort2.class, "insertSort");
	}

}
