package com.svetanis.datastructures.stack.expressions;

// 1963. Minimum Number of Swaps to Make the String Balanced

public final class MinSwapsToBalanceStr {
	// Time complexity: O(n)

	public static int minSwaps(String s) {
		int count = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '[') {
				count++;
			} else if (count > 0) {
				count--;
			}
		}
		return (count + 1) >> 1;
	}

	public static void main(String[] args) {
		System.out.println(minSwaps("][][")); // 1
		System.out.println(minSwaps("]]][[[")); // 2
		System.out.println(minSwaps("[]")); // 0
	}
}
