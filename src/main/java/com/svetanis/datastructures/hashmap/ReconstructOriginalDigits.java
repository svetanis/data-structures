package com.svetanis.datastructures.hashmap;

// 423. Reconstruct Original Digits from English
//
// reconstruct(s) takes the letters of some English digit words ("zero" to "nine") shuffled
// together, and returns the digits in increasing order. On LeetCode: 1 <= s.length <= 10^5,
// s is always the letters of some set of digit words.
//
// Five words each own a letter no other word has: z only in zero, w in two, u in four, x in
// six, g in eight. Their letter counts ARE those digits' counts. Five more letters appear in
// just one word besides the ones already found: h in three and eight, f in four and five, s
// in six and seven, o in zero, one, two, four, and i in five, six, eight, nine. Subtracting
// the known digits leaves the count of the remaining one. The order matters: each line uses
// only digits found above it.
//
// Siblings, same problem: none in this package.

public final class ReconstructOriginalDigits {
	// Time complexity: O(n), one pass to count the letters and one to write the digits
	// Auxiliary Space: O(1), 26 counts and 10 digits; the result string is extra

	public static String reconstruct(String s) {
		int[] count = count(s);
		int[] digits = digits(count);
		return convert(digits);
	}

	private static String convert(int[] digits) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < digits.length; i++) {
			for (int j = 0; j < digits[i]; j++) {
				sb.append(i); // WRITE: each digit as often as it was found, 0 first
			}
		}
		return sb.toString();
	}

	private static int[] digits(int[] count) {
		int[] digits = new int[10];
		digits[0] = count['z' - 'a']; // OWN: z is only in zero
		digits[2] = count['w' - 'a']; // OWN: w is only in two
		digits[4] = count['u' - 'a']; // OWN: u is only in four
		digits[6] = count['x' - 'a']; // OWN: x is only in six
		digits[8] = count['g' - 'a']; // OWN: g is only in eight
		digits[3] = count['h' - 'a'] - digits[8]; // SUBTRACT: h is in three and eight
		digits[5] = count['f' - 'a'] - digits[4]; // SUBTRACT: f is in four and five
		digits[7] = count['s' - 'a'] - digits[6]; // SUBTRACT: s is in six and seven
		digits[1] = count['o' - 'a'] - digits[0] - digits[2] - digits[4]; // SUBTRACT: o
		digits[9] = count['i' - 'a'] - digits[5] - digits[6] - digits[8]; // SUBTRACT: i
		return digits;
	}

	private static int[] count(String s) {
		int[] a = new int[26];
		for (char c : s.toCharArray()) {
			a[c - 'a']++; // COUNT
		}
		return a;
	}

	public static void main(String[] args) {
		System.out.println(reconstruct("owoztneoer")); // 012
		System.out.println(reconstruct("fviefuro")); // 45
	}
}