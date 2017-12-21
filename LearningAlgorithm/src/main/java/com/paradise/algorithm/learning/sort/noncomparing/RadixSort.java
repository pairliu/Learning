package com.paradise.algorithm.learning.sort.noncomparing;

import java.lang.reflect.InvocationTargetException;

import com.paradise.algorithm.learning.sort.SortHelper;

public class RadixSort {
	public final int DIGIT_NUMBER = 3;  //��ֻ����3λ�����ڵ�
	
	//�ӵ�λ��ʼ���л���Ϊ10�ļ�������
	public void lsdRadixSort(int[] list) {
		for (int d = 1; d <= DIGIT_NUMBER; d++) {
			countingSort(list, d);     //���ݵ�dλ���ֶ�list��������
		}
	}

	private void countingSort(int[] list, int d) {
		int[] count = new int[10];  //10�͹���
		
		for (int i = 0; i < list.length; i++) {
			count[getDigit(list[i], d)]++;
		}
		
		for (int j = 1; j < count.length; j++) {
			count[j] += count[j - 1];
		}
		
		int[] temp = new int[list.length];
		for (int i = list.length - 1; i >=  0; i--) {
			
			temp[--count[getDigit(list[i], d)]] = list[i];
		}
		
		for (int i = 0; i < list.length; i++) {
			list[i] = temp[i];
		}
	}

	private int getDigit(int i, int d) {
		int[] radix = {0, 1, 10, 100};      // 0 is not used
		
		return (i / radix[d]) % 10;
	}
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		SortHelper.sortFor(RadixSort.class, "lsdRadixSort");
	}

}
