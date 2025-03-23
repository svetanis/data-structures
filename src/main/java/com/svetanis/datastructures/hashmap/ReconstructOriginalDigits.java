package com.svetanis.datastructures.hashmap;

// 423. Reconstruct Original Digits from English

public final class ReconstructOriginalDigits {
	// Time Complexity: O(n)

	public static String reconstruct(String s) {
		int[] count = count(s);
		int[] digits = digits(count);
		return convert(digits);
	}

	private static String convert(int[] digits) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < digits.length; i++) {
			for (int j = 0; j < digits[i]; j++) {
				sb.append(i);
			}
		}
		return sb.toString();
	}

	private static int[] digits(int[] count) {
		int[] digits = new int[10];
		digits[0] = count['z' - 'a'];
		digits[2] = count['w' - 'a'];
		digits[4] = count['u' - 'a'];
		digits[6] = count['x' - 'a'];
		digits[8] = count['g' - 'a'];
		digits[3] = count['h' - 'a'] - digits[8];
		digits[5] = count['f' - 'a'] - digits[4];
		digits[7] = count['s' - 'a'] - digits[6];
		digits[1] = count['o' - 'a'] - digits[0] - digits[2] - digits[4];
		digits[9] = count['i' - 'a'] - digits[5] - digits[6] - digits[8];
		return digits;
	}

	private static int[] count(String s) {
		int[] a = new int[26];
		for (char c : s.toCharArray()) {
			a[c - 'a']++;
		}
		return a;
	}

	public static void main(String[] args) {
		System.out.println(reconstruct("owoztneoer")); // 012
		System.out.println(reconstruct("fviefuro")); // 45
	}
}