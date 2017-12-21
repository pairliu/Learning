package com.paradise.algorithm.learning.sort.swapsort;

import java.lang.reflect.InvocationTargetException;

import com.paradise.algorithm.learning.sort.SortHelper;

public class QuickSort {
	
	public void quickSort(int[] list) {
		quickSort(list, 0, list.length-1);
	}
	
	
	public void quickSort(int[] list, int lo, int hi) {
		
		if (lo >= hi) return;
		int pivotalPos = partition(list, lo, hi);
			
		quickSort(list, lo, pivotalPos - 1);
		quickSort(list, pivotalPos + 1, hi);
	}
	
	public int partition(int[] list, int lo, int hi) {
		int pivotal = list[lo];
		
		while(lo < hi) {   //外面一层表示直到lo, hi汇合。一次切分必须是lo和hi重合了
			while (hi > lo && list[hi] >= pivotal) hi--; //这里必须是>=，如果是>，那么碰到两个相等的就停下来赋值了，结果就是两个相等的值在不停的交换，没法停了
			list[lo] = list[hi];
			while (hi > lo && list[lo] <= pivotal) lo++;
			list[hi] = list[lo];
		}

		
		list[lo] = pivotal;
		
		return hi;
	}
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		SortHelper.sortFor(QuickSort.class, "quickSort");
	}

}
