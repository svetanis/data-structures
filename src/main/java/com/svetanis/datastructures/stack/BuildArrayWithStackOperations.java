package com.svetanis.datastructures.stack;

import java.util.ArrayList;
import java.util.List;

// 1441. Build an Array With Stack Operations

public final class BuildArrayWithStackOperations {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static List<String> buildArray(int n, int[] a) {
		int current = 0;
		List<String> operations = new ArrayList<>();
		for (int target : a) {
			while (++current < target) {
				operations.add("Push");
				operations.add("Pop");
			}
			operations.add("Push");
		}
		return operations;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 3 };
		System.out.println(buildArray(3, a1)); // push, push, pop, push

		int[] a2 = { 1, 2, 3 };
		System.out.println(buildArray(3, a2)); // push, push, push

		int[] a3 = { 1, 2 };
		System.out.println(buildArray(4, a3)); // push, push
	}
}
