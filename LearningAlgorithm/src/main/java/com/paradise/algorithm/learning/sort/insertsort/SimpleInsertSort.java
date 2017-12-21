package com.paradise.algorithm.learning.sort.insertsort;

import java.lang.reflect.InvocationTargetException;

import com.paradise.algorithm.learning.sort.SortHelper;

public class SimpleInsertSort {
	public void insertSort(int[] list) {   //这个有点复杂。第二个循环，在跟已排好序的列表比较时仍然是从前往后，就不大方便
		for (int i = 1; i < list.length; i++) {
			for (int j = 0; j < i; j++) {
				if (list[i] < list[j]) {
					int temp = list[i];
					//右移
					for (int k = i; k > j; k--) {
						list[k] = list[k-1];
					}
					list[j] = temp;
				}
			}
			
		}
	}
	
	public void insertSort2(int[] numbers) {  //这个则是内部循环中从右向左比较，每比较一个就移一个
		int size = numbers.length;
		int temp = 0;
		int j = 0;

		for (int i = 1; i < size; i++) {
			temp = numbers[i];
			// 假如temp比前面的值小，则将前面的值后移
			for (j = i; j > 0 && temp < numbers[j - 1]; j--) {
				numbers[j] = numbers[j - 1];
			}
			numbers[j] = temp;
		}
	}
	
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		SortHelper.sortFor(SimpleInsertSort.class, "insertSort");
	}

}
