package com.svetanis.datastructures.heap;

import java.util.PriorityQueue;
import java.util.Queue;

// given n ropes with different lengths,
// connect them into one big rope with min cost. 
// the cost of connecting two ropes is
// equal to the sum of their lengths

public final class ConnectNRopes {

	public static int minCost(int[] a) {
		// Time Complexity: O(n log n)

		int cost = 0;
		Queue<Integer> pq = build(a);

		while (pq.size() > 1) {
			// extract shortest two ropes from priority queue
			int first = pq.poll();
			int second = pq.poll();
			int sum = first + second;
			// connect the ropes: update result and
			// insert the new rope to priority queue
			cost += sum;
			pq.add(sum);
		}
		return cost;
	}

	private static Queue<Integer> build(int[] a) {
		Queue<Integer> pq = new PriorityQueue<>((x, y) -> x - y);
		for (int i = 0; i < a.length; i++) {
			pq.add(a[i]);
		}
		return pq;
	}

	public static void main(String[] args) {
		int[] a1 = { 4, 3, 2, 6 };
		System.out.println(minCost(a1));

		int[] a2 = { 1, 3, 11, 5 };
		System.out.println(minCost(a2));

		int[] a3 = { 3, 4, 5, 6 };
		System.out.println(minCost(a3));

		int[] a4 = { 1, 3, 11, 5, 2 };
		System.out.println(minCost(a4));
	}
}
