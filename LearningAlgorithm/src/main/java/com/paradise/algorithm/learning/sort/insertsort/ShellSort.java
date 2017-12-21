package com.paradise.algorithm.learning.sort.insertsort;

import java.lang.reflect.InvocationTargetException;

import com.paradise.algorithm.learning.sort.SortHelper;

public class ShellSort {
	
	public void shellSort(int[] list) {
		int h = 0;
		while (h <= list.length) {    //生成初始增量
			h = 3 * h + 1;
		}
		
		while (h >= 1) {   //最后一遍一定是步长为1  //其实跟插入排序一模一样，只是外面多了个循环来选择不同的步长
			System.out.println("Step is: " + h);
			for (int i = h; i < list.length; i++) {
				int curr = list[i];
				int pos = i;
				for (int j = i - h; j >= 0 && list[j] > curr; j = j - h) {
					list[j + h] = list[j];
					pos = j;
				}
				list[pos] = curr;
			}
			
			h = (h - 1) / 3;
			
		}
		
		
	}
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		SortHelper.sortFor(ShellSort.class, "shellSort");
	}

}
