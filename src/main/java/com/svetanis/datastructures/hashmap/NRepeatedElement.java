package com.svetanis.datastructures.hashmap;

import java.util.HashSet;
import java.util.Set;

// 961. N-Repeated Element in Size 2N Array

public final class NRepeatedElement {

	public static int repeated(int[] a) {
		Set<Integer> set = new HashSet<>();
		for(int element : a) {
			if(!set.add(element)) {
				return element;
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		int[] a = { 1, 2, 3, 3 };
		System.out.println(repeated(a)); // 3
		int[] a1 = { 2, 1, 2, 5, 3, 2 };
		System.out.println(repeated(a1)); // 2
		int[] a2 = { 5, 1, 5, 2, 5, 3, 5, 4 };
		System.out.println(repeated(a2)); // 5
	}
}