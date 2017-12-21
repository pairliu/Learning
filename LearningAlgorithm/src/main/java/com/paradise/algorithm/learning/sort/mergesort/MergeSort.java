package com.paradise.algorithm.learning.sort.mergesort;

import java.lang.reflect.InvocationTargetException;

import com.paradise.algorithm.learning.sort.SortHelper;

public class MergeSort {
	public void mergeSort(int[] list) {
		mergeSort(list, 0, list.length - 1);
	}
	
	public void mergeSort(int[] list, int left, int right) {
		if (left == right) return;  // 当待排序的序列长度为1时，递归开始回溯，进行merge操作
		
		int mid = (left + right) / 2;
		mergeSort(list, left, mid);
		mergeSort(list, mid + 1, right);
		merge(list, left, mid, right);
	}
	
	private void merge(int[] list, int left, int mid, int right) {
		int len = right - left + 1;
		int[] temp = new int[len];   // 辅助空间
		
		int index = 0;
		int i = left;      //左半边的起始元素
		int j = mid + 1;   //右半边的起始元素
		while (i <= mid && j <= right) {
			temp[index++] = list[i] <= list[j] ? list[i++] : list[j++];  // 带等号保证归并排序的稳定性
		}
		
		while (i <= mid) temp[index++] = list[i++];
		while (j <= right) temp[index++] = list[j++];
		
		//最后复制回去
		for (int k = 0; k < len; k++) list[left++] = temp[k];
		
	}

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		SortHelper.sortFor(MergeSort.class, "mergeSort");
	}

}
