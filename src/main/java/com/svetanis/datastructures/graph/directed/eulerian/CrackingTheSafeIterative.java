package com.svetanis.datastructures.graph.directed.eulerian;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

// 753. Cracking the Safe
//
// Input: n -- the length of the password -- and k -- the digits 0 .. k - 1 it is made of. After
// every digit typed the safe compares the last n digits typed with the password. Returns any
// string that contains every possible password as a substring; the shortest such string has
// k^n + n - 1 digits, and this method returns one of that length. On LeetCode: 1 <= n <= 4,
// 1 <= k <= 10, 1 <= k^n <= 4096.
//
// The one idea: the same walk as CrackingTheSafeStrings, with a stack in place of the recursion.
// One stack entry is an open call: an airport reached and not yet finished, together with the
// digit that was typed to reach it. The top is where the walk stands. Its tickets are taken in
// digit order, and only while it is on top, so one count per airport -- how many of its tickets
// are used -- replaces the Set: the next ticket is node + taken. If the top has a ticket left,
// fly it (push the airport it lands at); if not, pop it and write the digit that reached it.
// That is the order the recursion writes in, so the digits come out reversed.
//
// Time: O(k^n * n) -- k^n tickets, each a string of n digits built and hashed.
// Space: O(k^n * n) -- the counts, the stacks and the answer; no call stack, so no overflow.
//
// Siblings: CrackingTheSafeStrings is the recursive walk, shorter to write, which overflows a
// default Java stack at 2,401 tickets; CrackingTheSafe is the recursion with nodes as numbers.

public final class CrackingTheSafeIterative {

	public String crackSafe(int n, int k) {
		String start = "0".repeat(n - 1); // START: n - 1 zeros; every airport is balanced
		Map<String, Integer> taken = new HashMap<>(); // TAKEN: tickets used, per airport
		Deque<String> airports = new ArrayDeque<>(); // OPEN: airports reached and not yet finished
		// ARRIVED BY: the digit typed to reach each open airport; -1 for the start
		Deque<Integer> arrivals = new ArrayDeque<>();
		StringBuilder digits = new StringBuilder();
		airports.push(start);
		arrivals.push(-1);
		while (!airports.isEmpty()) {
			String node = airports.peek(); // PEEK: the top stays while it has tickets
			int d = taken.getOrDefault(node, 0); // TICKET: node + d, the next one in digit order
			if (d < k) {
				taken.put(node, d + 1);
				airports.push((node + d).substring(1)); // FOLLOW: the airport it lands at
				arrivals.push(d);
			} else {
				airports.pop(); // DONE: no tickets left here
				int digit = arrivals.pop();
				if (digit >= 0) {
					digits.append(digit); // WRITE: the digit that reached it
				}
			}
		}
		return start + digits.reverse(); // STRING: the start, then the digits in flying order
	}

	public static void main(String[] args) {
		CrackingTheSafeIterative cts = new CrackingTheSafeIterative();
		System.out.println(cts.crackSafe(1, 2)); // 01
		System.out.println(cts.crackSafe(2, 2)); // 00110
	}
}
