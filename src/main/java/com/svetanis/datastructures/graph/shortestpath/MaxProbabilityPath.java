package com.svetanis.datastructures.graph.shortestpath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

// 1514. Path with Maximum Probability
//
// Input: n -- the number of nodes; edges[i] = { a, b } -- an undirected edge; succProb[i] -- the
// chance of getting across edge i; start and end -- the two nodes. Returns the largest chance of
// getting from start to end, which is the product of the edge chances along the best path, or 0 if
// there is no path. On LeetCode: 2 <= n <= 10^4, 0 <= start, end < n, start != end,
// 0 <= a, b < n, a != b, 0 <= succProb[i] <= 1, at most one edge between any two nodes.
//
// The one idea: probabilities[v] is the best chance found so far of reaching node v, and one heap
// entry is a node with the chance it was reached at, largest chance first. Walking an edge can
// only keep or shrink a chance, because each edge chance is at most 1, so the largest chance in
// the heap can never be beaten by going through a smaller one. That is the same reason Dijkstra
// works for sums of positive lengths, with the product and the maximum in place of the sum and the
// minimum. Whenever a node's best chance improves, it goes back into the heap.
//
// Time: O(E log E) heap work -- an entry is pushed only when a node's chance improves, at most
// once per edge end. There is no stale-entry skip, so a stale entry still rescans its node's
// edges, though it can never improve anything; that rescan is the part that can exceed E.
// Space: O(V + E) -- the adjacency lists, probabilities, and the heap.

public final class MaxProbabilityPath {

	public static double maxProbability(int n, int[][] edges, double[] succProb, int src, int dst) {
		List<Pair>[] g = graph(n, edges, succProb);
		double[] probabilities = new double[n];
		probabilities[src] = 1.0;
		PriorityQueue<Pair> pq = new PriorityQueue<>(Comparator.comparingDouble(Pair::getVal).reversed());
		pq.offer(new Pair(src, 1.0));

		while (!pq.isEmpty()) {
			Pair pair = pq.poll(); // PICK: the node with the largest chance so far
			double cp = pair.getVal();
			int cn = pair.key;
			for (Pair neighbor : g[cn]) {
				int next = neighbor.key;
				double ep = neighbor.getVal();
				double np = cp * ep;
				if (probabilities[next] < np) {
					probabilities[next] = np; // RECORD: a better chance of reaching next
					pq.offer(new Pair(next, np));
				}
			}
		}
		return probabilities[dst]; // RETURN: 0.0 when dst was never reached
	}

	private static List<Pair>[] graph(int n, int[][] edges, double[] succProb) {
		List<Pair>[] g = new List[n];
		Arrays.setAll(g, k -> new ArrayList<>());
		for (int i = 0; i < edges.length; i++) {
			int from = edges[i][0];
			int to = edges[i][1];
			double prob = succProb[i];
			g[from].add(new Pair(to, prob)); // BUILD: undirected, so both directions
			g[to].add(new Pair(from, prob));
		}
		return g;
	}

	public static void main(String[] args) {
		int[][] edges1 = { { 0, 1 }, { 1, 2 }, { 0, 2 } };
		double[] sp1 = { 0.5, 0.5, 0.2 };
		System.out.println(maxProbability(3, edges1, sp1, 0, 2)); // 0.25

		int[][] edges2 = { { 0, 1 }, { 1, 2 }, { 0, 2 } };
		double[] sp2 = { 0.5, 0.5, 0.3 };
		System.out.println(maxProbability(3, edges2, sp2, 0, 2)); // 0.3

		int[][] edges3 = { { 0, 1 } };
		double[] sp3 = { 0.5 };
		System.out.println(maxProbability(3, edges3, sp3, 0, 2)); // 0.0
	}

	private static class Pair {
		private int key;
		private double val;

		public Pair(int key, double val) {
			this.key = key;
			this.val = val;
		}

		public double getVal() {
			return val;
		}
	}
}