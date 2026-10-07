package com.svetanis.datastructures.queue;

import java.util.ArrayDeque;
import java.util.Deque;

// 2073. Time Needed to Buy Tickets
//
// tickets[i] is how many tickets person i wants; the people stand in line in index order. Each
// second the person at the front buys one ticket and, if they still want more, goes to the back.
// Returns the seconds until person k has bought all their tickets.
//
// timeToBuy runs that line with a queue of {tickets still wanted, person}. timeRequiredToBuy
// counts without the queue: k reaches the front tickets[k] times. A person up to k gets a turn
// before each of k's turns, so buys min(tickets[i], tickets[k]); a person behind k gets one turn
// fewer, because the count stops at k's last ticket, so buys min(tickets[i], tickets[k] - 1).

public final class TimeNeededToBuyTickets {
	// Time Complexity: timeRequiredToBuy O(n), one look at each person;
	//   timeToBuy O(n + tickets sold until k finishes), one poll per ticket -- a simulation
	// Space Complexity: timeRequiredToBuy O(1); timeToBuy O(n), one queue entry per person

	public static int timeRequiredToBuy(int[] tickets, int k) {
		int time = 0;
		int target = tickets[k];
		for (int i = 0; i < tickets.length; i++) {
			int curr = tickets[i];
			time += (i <= k) ? Math.min(curr, target) : Math.min(curr, target - 1);
		}
		return time;
	}

	public static int timeToBuy(int[] tickets, int k) {
		Deque<int[]> dq = init(tickets);
		int time = 0;
		while (!dq.isEmpty()) {
			int[] front = dq.poll();
			int remained = front[0] - 1;
			int index = front[1];
			time += 1;
			if (remained > 0) {
				dq.offer(new int[] { remained, index });
			}
			if (index == k && remained == 0) { // k just bought their last ticket
				return time;
			}
		}
		return time;
	}

	private static Deque<int[]> init(int[] tickets) {
		Deque<int[]> dq = new ArrayDeque<>();
		for (int i = 0; i < tickets.length; i++) {
			dq.offer(new int[] { tickets[i], i });
		}
		return dq;
	}

	public static void main(String[] args) {
		int[] a1 = { 2, 3, 2 };
		System.out.println(timeToBuy(a1, 2)); // 6
		int[] a2 = { 5, 1, 1, 1 };
		System.out.println(timeToBuy(a2, 0)); // 8
	}
}