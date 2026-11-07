package com.svetanis.datastructures.hashmap;

import java.util.Arrays;

// 379. Design Phone Directory
//
// The constructor takes maxNumbers: the numbers 0 to maxNumbers - 1 start free. get() hands
// out a free number, or returns -1 when none is free; check(number) says whether it is free;
// release(number) makes it free again. On LeetCode: 1 <= maxNumbers <= 10^4,
// 0 <= number < maxNumbers, at most 2 * 10^4 calls.
//
// One flag per number: true while it is free. get scans from 0 for the first free flag, so
// it always hands out the smallest free number. Simple, but get pays for the scan; check and
// release touch one flag. A number outside the range is reported as not free and ignored by
// release.
//
// Siblings, same problem:
//   PhoneDirectoryQueue  free numbers in a queue and handed-out ones in a set: get is O(1),
//                        and numbers come back in the order they were released

public final class PhoneDirectory {
	// Time complexity: get O(n), the scan for a free flag; check and release O(1)
	// Auxiliary Space: O(n), one flag per number

	private boolean[] available; // FREE: available[i] is true while number i is free

	public PhoneDirectory(int max) {
		this.available = new boolean[max];
		Arrays.fill(available, true);
	}

	public int get() {
		for (int i = 0; i < available.length; i++) { // FIND: the smallest free number
			if (available[i]) {
				available[i] = false; // TAKE
				return i;
			}
		}
		return -1; // FULL: every number is handed out
	}

	public boolean check(int num) {
		if (num < 0 || num >= available.length) {
			return false;
		}
		return available[num];
	}

	public void release(int num) {
		if (num >= 0 && num < available.length) {
			available[num] = true; // FREE it again
		}
	}

	public static void main(String[] args) {
		PhoneDirectory pd = new PhoneDirectory(3);
		System.out.println(pd.get()); // 0
		System.out.println(pd.get()); // 1
		System.out.println(pd.check(2)); // true
		System.out.println(pd.get()); // 2
		System.out.println(pd.check(2)); // false
		pd.release(2);
		System.out.println(pd.check(2)); // true
	}
}