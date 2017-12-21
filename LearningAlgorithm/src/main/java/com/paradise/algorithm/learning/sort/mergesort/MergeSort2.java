package com.paradise.algorithm.learning.sort.mergesort;

import java.lang.reflect.InvocationTargetException;

import com.paradise.algorithm.learning.sort.SortHelper;

public class MergeSort2 {
	
	public void mergeSort(int[] list) {
		mergeSort(list, 0, list.length - 1);
	}

	private void mergeSort(int[] list, int left, int right) {
		if ( left == right) return;
		
		int middle = (left + right) / 2;
		mergeSort(list, left, middle);
		mergeSort(list, middle + 1, right);
		merge(list, left, middle, right);
		
	}

	private void merge(int[] list, int left, int middle, int right) {
		int i = left;               //beginning of the left part
		int j = middle + 1;         //beginning of the right part
		
		int[] temp = new int[right - left + 1];
		int index = 0;
		while (i <= middle && j <= right) {
			if (list[i] <= list[j]) temp [index++] = list[i++];
			else temp[index++] = list[j++];
		}
		
		while (i <= middle) temp[index++] = list[i++];
		while (j <= right) temp[index++] = list[j++];
		
		for (int k = 0; k < temp.length; k++) {
			list[left++] = temp[k];
		}
		
	}
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		SortHelper.sortFor(MergeSort2.class, "mergeSort");
	}

}
