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
		
		while(lo < hi) {   //����һ���ʾֱ��lo, hi��ϡ�һ���зֱ�����lo��hi�غ���
			while (hi > lo && list[hi] >= pivotal) hi--; //���������>=�������>����ô����������ȵľ�ͣ������ֵ�ˣ��������������ȵ�ֵ�ڲ�ͣ�Ľ�����û��ͣ��
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
