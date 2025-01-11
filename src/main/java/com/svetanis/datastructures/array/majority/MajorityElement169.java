package com.svetanis.datastructures.array.majority;

// 169. Majority element

// A majority element in an array a[] of size n 
// is an element that appears more than n/2 times 

public final class MajorityElement169 {
	// Time Complexity: O(n)
	// Space Complexity: O(1)

	public static int majorityElement(int[] a) {
		int count = 0;
		int candidate = 0;
		for (int curr : a) {
			if (count == 0) {
				candidate = curr;
				count = 1;
			} else {
				if (curr == candidate) {
					count++;
				} else {
					count--;
				}
			}
		}
		return candidate;
	}

	public static void main(String[] args) {
		int[] a = { 1, 3, 3, 3, 3, 1, 2 };
		System.out.println(majorityElement(a)); // 3
		int[] a1 = { 1, 2, 3, 1, 1, 2, 1 };
		System.out.println(majorityElement(a1)); // 1
		int[] a2 = { 3, 2, 3 }; // 3
		System.out.println(majorityElement(a2));
		int[] a3 = { 2, 2, 1, 1, 1, 2, 2 };
		System.out.println(majorityElement(a3)); // 2
	}
}