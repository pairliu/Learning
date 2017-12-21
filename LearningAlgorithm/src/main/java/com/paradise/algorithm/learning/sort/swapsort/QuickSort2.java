package com.paradise.algorithm.learning.sort.swapsort;

import java.lang.reflect.InvocationTargetException;
import com.paradise.algorithm.learning.sort.SortHelper;

public class QuickSort2 {
	
	public void quickSort(int[] list) {
		quickSort(list, 0, list.length - 1);
	}
	
	public void quickSort(int[] list, int lo, int hi) {
		if (lo >= hi) return;
		
		int pivotPos = partition(list, lo, hi);
		quickSort(list, lo, pivotPos - 1);
		quickSort(list, pivotPos + 1, hi);
	}
	
	public int partition(int[] list, int lo, int hi) {
		int pivot = list[lo];
		
		while (lo < hi) {
			while (lo < hi && list[hi] >= pivot) hi--;
			list[lo] = list[hi];
			while (lo < hi && list[lo] <= pivot) lo++;
			list[hi] = list[lo];
		}
		
		list[lo] = pivot;
		return lo;
	}
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		SortHelper.sortFor(QuickSort2.class, "quickSort");
	}

}
