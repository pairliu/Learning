package com.paradise.algorithm.learning.sort.mergesort;

import java.lang.reflect.InvocationTargetException;

import com.paradise.algorithm.learning.sort.SortHelper;

public class MergeSort {
	public void mergeSort(int[] list) {
		mergeSort(list, 0, list.length - 1);
	}
	
	public void mergeSort(int[] list, int left, int right) {
		if (left == right) return;  // ������������г���Ϊ1ʱ���ݹ鿪ʼ���ݣ�����merge����
		
		int mid = (left + right) / 2;
		mergeSort(list, left, mid);
		mergeSort(list, mid + 1, right);
		merge(list, left, mid, right);
	}
	
	private void merge(int[] list, int left, int mid, int right) {
		int len = right - left + 1;
		int[] temp = new int[len];   // �����ռ�
		
		int index = 0;
		int i = left;      //���ߵ���ʼԪ��
		int j = mid + 1;   //�Ұ�ߵ���ʼԪ��
		while (i <= mid && j <= right) {
			temp[index++] = list[i] <= list[j] ? list[i++] : list[j++];  // ���Ⱥű�֤�鲢������ȶ���
		}
		
		while (i <= mid) temp[index++] = list[i++];
		while (j <= right) temp[index++] = list[j++];
		
		//����ƻ�ȥ
		for (int k = 0; k < len; k++) list[left++] = temp[k];
		
	}

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		SortHelper.sortFor(MergeSort.class, "mergeSort");
	}

}
