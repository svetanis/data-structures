package com.svetanis.datastructures.array;

// 495. Teemo Attacking

public final class TeemoAttacking {
	// Time Complexity: O(n)

	public static int poisonedDuration(int[] a, int duration) {
		if(a.length == 0) {
			return 0;
		}
		int total = duration;
		for(int i = 1; i < a.length; i++) {
			int diff = a[i] - a[i - 1];
			total += Math.min(duration, diff);
		}
		return total;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 4 };
		System.out.println(poisonedDuration(a1, 2)); // 4

		int[] a2 = { 1, 2 };
		System.out.println(poisonedDuration(a2, 2)); // 3

		int[] a3 = { 1,2,3,4,5};
		System.out.println(poisonedDuration(a3, 5)); // 9

	}
}
