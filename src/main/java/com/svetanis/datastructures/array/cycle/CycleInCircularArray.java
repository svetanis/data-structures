package com.svetanis.datastructures.array.cycle;

// given a circular array containing positive and negative numbers.
// suppose the array contains a number M at a particular index.
// now, if M is positive we will move forward M indices and 
// if M is negative move backwards M indices. 
// determine if the array has a cycle. the cycle should have more
// than one element and should follow one direction which means
// the cycle should not contain both forward and backward movements

public final class CycleInCircularArray {

	public static boolean hasCycle(int[] a) {
		// Time Complexity: O(n)

		for (int i = 0; i < a.length; i++) {
			int slow = i;
			int fast = i;
			boolean forward = a[i] >= 0;

			while (slow != -1 && fast != -1) {
				slow = next(a, forward, slow);
				fast = next(a, forward, fast);
				if (fast != -1) {
					fast = next(a, forward, fast);
				}
				if (slow != -1 && slow == fast) {
					return true;
				}
			}
		}
		return false;
	}

	private static int next(int[] a, boolean forward, int curr) {
		boolean direction = a[curr] >= 0;
		// change in direction not allowed
		if (forward != direction) {
			return -1;
		}
		int next = (curr + a[curr]) % a.length;
		// wrap around for negative numbers
		if (next < 0) {
			next += a.length;
		}
		// one element cycle not allowed
		if (next == curr) {
			return -1;
		}
		return next;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 2, -1, 2, 2 };
		System.out.println(hasCycle(a1));
		int[] a2 = { 2, 2, -1, 2 };
		System.out.println(hasCycle(a2));
		int[] a3 = { 2, 1, -1, -2 };
		System.out.println(hasCycle(a3));
	}
}
