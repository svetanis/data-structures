package com.svetanis.datastructures.graph.mst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

// 1514. Path with Maximum Probability
// Dijkstra's Algorithm

public final class MaxProbabilityPath {
	// Time Complexity: O( (V + E) * log V);
	
	public static double maxProbability(int n, int[][] edges, double[] succProb, int src, int dst) {
		List<Pair>[] g = graph(n, edges, succProb);
		double[] probabilities = new double[n];
		probabilities[src] = 1.0;
		PriorityQueue<Pair> pq = new PriorityQueue<>(Comparator.comparingDouble(Pair::getVal).reversed());
		pq.offer(new Pair(src, 1.0));

		while (!pq.isEmpty()) {
			Pair pair = pq.poll();
			double cp = pair.getVal();
			int cn = pair.key;
			for (Pair neighbor : g[cn]) {
				int next = neighbor.key;
				double ep = neighbor.getVal();
				double np = cp * ep;
				if (probabilities[next] < np) {
					probabilities[next] = np;
					pq.offer(new Pair(next, np));
				}
			}
		}
		return probabilities[dst];
	}

	private static List<Pair>[] graph(int n, int[][] edges, double[] succProb) {
		List<Pair>[] g = new List[n];
		Arrays.setAll(g, k -> new ArrayList<>());
		for (int i = 0; i < edges.length; i++) {
			int from = edges[i][0];
			int to = edges[i][1];
			double prob = succProb[i];
			g[from].add(new Pair(to, prob));
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