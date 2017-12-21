package com.paradise.algorithm.learning.sort.noncomparing;

import java.lang.reflect.InvocationTargetException;

import com.paradise.algorithm.learning.sort.SortHelper;

public class RadixSort2 {
	
	public void radixSort(int[] list) {
		int d = 3;
		for (int i = 1; i <= d; i++) {    //从低位排起
//		for (int i = d; i >= 1; i--) {    //从高位排起  不行，有错误
			sortOnDigit(list, i);
		}
	}

	private void sortOnDigit(int[] list, int d) {
		
		int count[] = new int[10];
		
		for (int i = 0; i < list.length; i++) {
			count[getDigit(list[i], d)]++;
		}
		
		for (int i = 1; i < count.length; i++) {
			count[i] += count[i-1];
		}
		
		int[] temp = new int[list.length];
//		for (int i = 0; i < list.length; i++) {    //error
		for (int i = list.length - 1; i >=  0; i--) {    //这里倒序还很关键呢
			temp[--count[getDigit(list[i], d)]] = list[i];
		}
		
		for (int i = 0; i < list.length; i++) {
			list[i] = temp[i];
		}
		
	}

	private int getDigit(int num, int d) {
		int factor = 1;
		
		for (int i = 1; i < d; i++) factor *= 10;  //每次都计算这个有点低效
		
		return (num / factor) % 10;
	}
	
//	private int getDigit(int i, int d) {
//		int[] radix = {0, 1, 10, 100};      // 0 is not used
//		
//		return (i / radix[d]) % 10;
//	}
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		SortHelper.sortFor(RadixSort2.class, "radixSort");
	}
	
	

}
