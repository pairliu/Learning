package com.paradise.algorithm.learning.sort.insertsort;

import java.lang.reflect.InvocationTargetException;

import com.paradise.algorithm.learning.sort.SortHelper;

public class SimpleInsertSort {
	public void insertSort(int[] list) {   //����е㸴�ӡ��ڶ���ѭ�����ڸ����ź�����б�Ƚ�ʱ��Ȼ�Ǵ�ǰ���󣬾Ͳ��󷽱�
		for (int i = 1; i < list.length; i++) {
			for (int j = 0; j < i; j++) {
				if (list[i] < list[j]) {
					int temp = list[i];
					//����
					for (int k = i; k > j; k--) {
						list[k] = list[k-1];
					}
					list[j] = temp;
				}
			}
			
		}
	}
	
	public void insertSort2(int[] numbers) {  //��������ڲ�ѭ���д�������Ƚϣ�ÿ�Ƚ�һ������һ��
		int size = numbers.length;
		int temp = 0;
		int j = 0;

		for (int i = 1; i < size; i++) {
			temp = numbers[i];
			// ����temp��ǰ���ֵС����ǰ���ֵ����
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
