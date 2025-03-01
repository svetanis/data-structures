package com.svetanis.datastructures.hashmap;

// 1189. Maximum Number of Balloons

public final class MaxNumOfBalloons {
	// Time Complexity: O(n)

	public static int mnb(String s) {
		int[] a = freq(s);
		a['l' - 'a'] /= 2;
		a['o' - 'a'] /= 2;
		int maxBallons = Integer.MAX_VALUE;
		for (char c : "balloon".toCharArray()) {
			maxBallons = Math.min(maxBallons, a[c - 'a']);
		}
		return maxBallons;
	}

	private static int[] freq(String s) {
		int[] a = new int[26];
		for (char c : s.toCharArray()) {
			a[c - 'a']++;
		}

		return a;
	}

	public static void main(String[] args) {
		System.out.println(mnb("nlaebolko")); // 1
		System.out.println(mnb("loonbalxballpoon")); // 2
		System.out.println(mnb("leetcode")); // 0
	}
}