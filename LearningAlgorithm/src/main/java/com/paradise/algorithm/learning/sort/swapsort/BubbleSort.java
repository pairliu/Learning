package com.paradise.algorithm.learning.sort.swapsort;

import java.lang.reflect.InvocationTargetException;

import com.paradise.algorithm.learning.sort.SortHelper;

public class BubbleSort {
	
	public void bubbleSort(int[] list) {    //所以BubbleSort两层循环方向肯定是反的
		for (int j = list.length-1; j>0; j--) {
			for (int i=0; i<=j; i++) {
				if (i < j && list[i] > list[i+1]) swap(list, i, i+1);
			}
		}
		
	}
	
	private void swap(int[] list, int i, int j) {
		int temp = list[i];
		list[i] = list[j];
		list[j] = temp;
	}
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		SortHelper.sortFor(BubbleSort.class, "bubbleSort");
	}

}
