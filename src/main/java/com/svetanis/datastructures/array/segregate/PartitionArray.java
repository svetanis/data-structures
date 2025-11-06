package com.svetanis.datastructures.array.segregate;

import com.svetanis.java.base.utils.Print;

// 2161. Partition Array According to Given Pivot

public final class PartitionArray {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static int[] partition(int[] a, int pivot) {
		int[] partitioned = new int[a.length];
		int index = 0;
		for (int num : a) {
			if (num < pivot) {
				partitioned[index++] = num;
			}
		}
		for (int num : a) {
			if (num == pivot) {
				partitioned[index++] = num;
			}
		}
		for (int num : a) {
			if (num > pivot) {
				partitioned[index++] = num;
			}
		}
		return partitioned;
	}

	public static void main(String[] args) {
		int[] a1 = { 9, 12, 5, 10, 14, 3, 10 };
		Print.print(partition(a1, 10)); // 9 5 3 10 10 12 14

		int[] a2 = { -3, 4, 3, 2 };
		Print.print(partition(a2, 2)); // -3 2 4 3
	}
}
