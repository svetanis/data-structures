package com.svetanis.datastructures.hashmap;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

// 379. Design Phone Directory
//
// The constructor takes maxNumbers: the numbers 0 to maxNumbers - 1 start free. get() hands
// out a free number, or returns -1 when none is free; check(number) says whether it is free;
// release(number) makes it free again. On LeetCode: 1 <= maxNumbers <= 10^4,
// 0 <= number < maxNumbers, at most 2 * 10^4 calls.
//
// The free numbers wait in a queue, and the handed-out ones are kept in a set. get takes the
// queue's front, so no scan is needed. release puts a number back only if the set says it was
// handed out: releasing a free number twice would otherwise put it in the queue twice, and
// two later gets would hand out the same number. check asks the set, so a number outside the
// range counts as free; LeetCode never asks about one.
//
// Siblings, same problem:
//   PhoneDirectory  one flag per number and a scan in get: O(n), but always the smallest
//                   free number

public final class PhoneDirectoryQueue {
	// Time complexity: get, check, release O(1) on average, one queue step and one set update;
	//   the constructor O(n), it queues every number
	// Auxiliary Space: O(n), each number is in the queue or in the set, never both

	private int total;
	private Queue<Integer> available; // FREE: the numbers that can be handed out
	private Set<Integer> visited; // USED: the numbers handed out and not yet released

	public PhoneDirectoryQueue(int max) {
		this.available = new LinkedList<>();
		this.visited = new HashSet<>();
		this.total = max;
		for (int i = 0; i < max; i++) {
			available.add(i);
		}
	}

	public int get() {
		if (visited.size() == total) { // FULL: every number is handed out
			return -1;
		}
		int slot = available.poll(); // TAKE: the front of the free queue
		visited.add(slot); // MARK it handed out
		return slot;
	}

	public boolean check(int num) {
		return !visited.contains(num);
	}

	public void release(int num) {
		if (visited.contains(num)) { // USED? only then can it come back
			available.add(num); // RETURN it to the back of the queue
			visited.remove(num);
		}
	}

	public static void main(String[] args) {
		PhoneDirectoryQueue pd = new PhoneDirectoryQueue(3);
		System.out.println(pd.get()); // 0
		System.out.println(pd.get()); // 1
		System.out.println(pd.check(2)); // true
		System.out.println(pd.get()); // 2
		System.out.println(pd.check(2)); // false
		pd.release(2);
		System.out.println(pd.check(2)); // true
	}
}