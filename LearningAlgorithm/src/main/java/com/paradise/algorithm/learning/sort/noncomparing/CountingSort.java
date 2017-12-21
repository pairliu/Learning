package com.paradise.algorithm.learning.sort.noncomparing;

import java.lang.reflect.InvocationTargetException;

import com.paradise.algorithm.learning.sort.SortHelper;

public class CountingSort {
	
	public void countingSort(int[] list) {
		int[] count = new int[100];   //要求list中的元素只能是在0~99之间的数。适合数量很大的数，但是都是在一个范围内的
		
		for (int i = 0; i < list.length; i++) {
			count[list[i]]++;     // 计数了
		}
		SortHelper.printWithSpace(count);
		System.out.println();
		
		//变成个递增的数组
		for (int i = 1; i < count.length; i++) {
			count[i] = count[i] + count[i - 1];
		}
		SortHelper.printWithSpace(count);
		System.out.println();
				
		int[] temp = new int[list.length];
		for (int i = list.length - 1; i >= 0; i--) {
			temp[--count[list[i]]] = list[i];    //这个是关键
		}
		SortHelper.printWithSpace(temp);
		System.out.println();
		
		for (int i = 0; i < list.length; i++) {
			list[i] = temp[i];
		}
	}
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		SortHelper.sortFor(CountingSort.class, "countingSort");
	}

}
