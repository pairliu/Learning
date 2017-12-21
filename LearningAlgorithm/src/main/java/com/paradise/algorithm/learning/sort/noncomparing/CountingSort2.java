package com.paradise.algorithm.learning.sort.noncomparing;

import java.lang.reflect.InvocationTargetException;

import com.paradise.algorithm.learning.sort.SortHelper;

public class CountingSort2 {
	
	public void countingSort(int[] list) {
		int[] count = new int[100];   // 0 ~ 99 data
		
		for (int i = 0; i < list.length; i++) {
			count[list[i]] ++;
		}		
		SortHelper.printWithSpace(count);
		
		for (int i = 1; i < count.length; i++) {
			count[i] += count[i-1];
		}		
		SortHelper.printWithSpace(count);
		
		int[] temp = new int[list.length];
		for (int i = 0; i < list.length; i++) {   //为啥单独的countingSort正序反序都没问题呢？但是radixSort就有问题呢？
			temp[--count[list[i]]] = list[i];
		}
		
		for (int i = 0; i < list.length; i++) {
			list[i] = temp[i];
		}
	}
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		SortHelper.sortFor(CountingSort2.class, "countingSort");
	}

}
