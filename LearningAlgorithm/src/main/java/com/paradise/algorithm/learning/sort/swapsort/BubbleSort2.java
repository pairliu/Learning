package com.paradise.algorithm.learning.sort.swapsort;

import java.lang.reflect.InvocationTargetException;

import com.paradise.algorithm.learning.sort.SortHelper;

public class BubbleSort2 {
	
	public void bubbleSort(int[] list) {
		for (int i = 0; i < list.length; i++) {
			for (int j = list.length - 1; j > i; j--) {
				if (list[j] < list[j-1]) swap(list, j, j-1);  
			}
		}
	}
	
	private void swap(int[] list, int i, int j) {
		int temp = list[i];
		list[i] = list[j];
		list[j] = temp;
	}
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		SortHelper.sortFor(BubbleSort2.class, "bubbleSort");
	}

}
