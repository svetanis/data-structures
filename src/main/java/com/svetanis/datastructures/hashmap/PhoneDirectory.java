package com.svetanis.datastructures.hashmap;

import java.util.Arrays;

// 379. Design Phone Directory

public final class PhoneDirectory {

	private boolean[] available;

	public PhoneDirectory(int max) {
		this.available = new boolean[max];
		Arrays.fill(available, true);
	}

	public int get() {
		for (int i = 0; i < available.length; i++) {
			if (available[i]) {
				available[i] = false;
				return i;
			}
		}
		return -1;
	}

	public boolean check(int num) {
		if (num < 0 || num >= available.length) {
			return false;
		}
		return available[num];
	}

	public void release(int num) {
		if (num >= 0 && num < available.length) {
			available[num] = true;
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