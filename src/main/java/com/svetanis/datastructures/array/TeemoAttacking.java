package com.svetanis.datastructures.array;

// 495. Teemo Attacking
//
// Input: timeSeries, the seconds at which Teemo attacks, in non-decreasing order, and a duration.
// An attack at second t poisons the target for the seconds t .. t + duration - 1; an attack while
// still poisoned restarts the timer, it does not add. Returns how many seconds the target is
// poisoned. On LeetCode: 1 <= timeSeries.length <= 10^4, 0 <= timeSeries[i], duration <= 10^7.
//
// The one idea: count the poisoned seconds attack by attack. Each attack adds at most duration
// seconds, but the next attack cuts it short if it comes sooner: the poison from attack i is only
// worth min(duration, gap to attack i + 1). The last attack has no next one, so it gives its full
// duration. The total is the sum of those.
//
// Time: O(n) -- one pass.
// Space: O(1) -- one running total.

public final class TeemoAttacking {

	public static int poisonedDuration(int[] a, int duration) {
		if (a.length == 0) {
			return 0;
		}
		int total = duration; // LAST ATTACK: gives its full duration
		for (int i = 1; i < a.length; i++) {
			int diff = a[i] - a[i - 1];
			total += Math.min(duration, diff); // TAKE: attack i - 1 is cut short by attack i
		}
		return total;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 4 };
		System.out.println(poisonedDuration(a1, 2)); // 4

		int[] a2 = { 1, 2 };
		System.out.println(poisonedDuration(a2, 2)); // 3

		int[] a3 = { 1, 2, 3, 4, 5 };
		System.out.println(poisonedDuration(a3, 5)); // 9
	}
}
