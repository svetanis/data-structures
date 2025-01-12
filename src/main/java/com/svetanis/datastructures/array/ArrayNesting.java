package com.svetanis.datastructures.array;

// 565. Array Nesting

public final class ArrayNesting {
	// Time Complexity: O(n)

	public static int nest(int[] a) {
		int n = a.length;
		int max = Integer.MIN_VALUE;
		for(int start = 0; start < n; start++) {
			int count = 0;
			int curr = start;
			while(a[curr] < n) {
				int next = a[curr];
				a[curr] = n;
				curr = next;
				count++;
			}
			max = Math.max(max, count);
		}
		return max;
	}

	public static void main(String[] args) {
		int[] a1 = { 5, 4, 0, 3, 1, 6, 2 };
		System.out.println(nest(a1)); // 4

		int[] a2 = { 0, 1, 2 };
		System.out.println(nest(a2)); // 1
	}
}
