package com.paradise.algorithm.learning.sort.selectsort;

import java.lang.reflect.InvocationTargetException;

import com.paradise.algorithm.learning.sort.SortHelper;

public class SelectionSort2 {
	
	public void selectSort(int[] list) {
		for (int i=0; i < list.length; i++) {
			int min = list[i];
			int pos = i;
			for (int j = i+1; j < list.length; j++) {
				if (list[j] < min) {
					min = list[j];
					pos = j;
				}
			}
			int temp = list[i];
			list[i] = list[pos];
			list[pos] = temp;
		}
	}
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		SortHelper.sortFor(SelectionSort2.class, "selectSort");
	}

}
