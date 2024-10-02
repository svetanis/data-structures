package com.svetanis.datastructures.array.majority;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;

import com.google.common.base.Optional;

// A majority element in an array a[] of size n 
// is an element that appears more than n/2 times 

public final class MajorityElementMooreVotingAlgo {

	public static Optional<Integer> majorityElement(int[] a) {
		// Time Complexity: O(n)
		// Space Complexity: O(1)

		// 1. find candidate for majority
		int index = findCandidate(a);

		// 2. check if candidate is majority
		if (isMajority(a, index)) {
			return of(a[index]);
		}
		return absent();
	}

	private static boolean isMajority(int[] a, int index) {
		int n = a.length;
		return index + n / 2 < n && a[index + n / 2] == a[index];
	}

	private static int findCandidate(int[] a) {
		int index = 0;
		int count = 1;
		for (int i = 1; i < a.length; ++i) {
			if (a[i] == a[index]) {
				++count;
			} else {
				--count;
			}
			if (count == 0) {
				index = i;
				count = 1;
			}
		}
		return index;
	}

	public static void main(String[] args) {
		int[] a = { 1, 3, 3, 3, 3, 1, 2 };
		System.out.println(majorityElement(a));
	}
}