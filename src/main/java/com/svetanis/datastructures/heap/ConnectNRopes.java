package com.svetanis.datastructures.heap;

import java.util.PriorityQueue;
import java.util.Queue;

// 1167. Minimum Cost to Connect Sticks -- told here as ropes
//
// Input: a, the lengths of n ropes. Connecting two ropes costs the sum of their lengths and
// leaves one rope of that length. Returns the least total cost of connecting them all into one
// rope. On LeetCode: 1 <= n <= 10^4, 1 <= a[i] <= 10^4. The cost is an int: the joins form a
// tree whose cost is each rope's length times its depth, the cheapest tree costs no more than a
// balanced one, and a balanced tree over 10^4 ropes is 14 levels deep, so the total stays under
// 14 * 10^8, below 2^31.
//
// The one idea: one heap entry is the length of one rope still on the table. A rope's length is
// paid again by every join it takes part in, so the shortest ropes should be joined first and
// the long ones last: always join the two shortest ropes left -- the rule that builds a Huffman
// code. The joined rope goes back, because it may be among the two shortest on a later turn; a
// min-heap hands over the two shortest every time.
//
// Time: O(n log n) -- n - 1 joins, each two polls and one push, O(log n) apiece.
// Space: O(n) -- the heap.
//
// Siblings: MinCostConnectSticks (algorithms, search.heap) is the same problem and the same
// loop, with the sticks pushed straight into a default PriorityQueue.

public final class ConnectNRopes {

	public static int minCost(int[] a) {
		int cost = 0;
		Queue<Integer> pq = build(a); // MIN-HEAP of every rope

		while (pq.size() > 1) { // JOIN until one rope is left
			// extract shortest two ropes from priority queue
			int first = pq.poll(); // POLL the shortest
			int second = pq.poll(); // POLL the next shortest
			int sum = first + second;
			// connect the ropes: update result and
			// insert the new rope to priority queue
			cost += sum; // PAY this join's cost
			pq.add(sum); // PUSH the joined rope back
		}
		return cost; // RETURN the running total, not the last rope
	}

	private static Queue<Integer> build(int[] a) {
		Queue<Integer> pq = new PriorityQueue<>(Integer::compare); // ORDER: shortest on top
		for (int i = 0; i < a.length; i++) {
			pq.add(a[i]); // PUSH every rope
		}
		return pq;
	}

	public static void main(String[] args) {
		int[] a1 = { 4, 3, 2, 6 };
		System.out.println(minCost(a1)); // 29

		int[] a2 = { 1, 3, 11, 5 };
		System.out.println(minCost(a2)); // 33

		int[] a3 = { 3, 4, 5, 6 };
		System.out.println(minCost(a3)); // 36

		int[] a4 = { 1, 3, 11, 5, 2 };
		System.out.println(minCost(a4)); // 42
	}
}
