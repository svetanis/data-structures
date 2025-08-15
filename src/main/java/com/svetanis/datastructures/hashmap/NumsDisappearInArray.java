package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.List;

// 448. Find All Numbers Disappeared in an Array

public final class NumsDisappearInArray {
	// Time Complexity: O(n)

	public static List<Integer> disappearedNums(int[] a) {
		int[] counts = new int[a.length + 1];
		for(int num : a) {
			counts[num]++;
		}
		List<Integer> list = new ArrayList<>();
		for(int i = 1; i < counts.length; i++) {
			if(counts[i] == 0) {
				list.add(i);
			}
		}
		return list;
	}

	public static void main(String[] args) {
		int[] a1 = { 4, 3, 2, 7, 8, 2, 3, 1 };
		System.out.println(disappearedNums(a1)); // 5,6
		int[] a2 = { 1, 1 };
		System.out.println(disappearedNums(a2)); // 2
	}
}