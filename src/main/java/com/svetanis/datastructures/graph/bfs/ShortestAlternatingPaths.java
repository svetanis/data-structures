package com.svetanis.datastructures.graph.bfs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

// 1129. Shortest Path with Alternating Colors
//
// Input: n -- nodes numbered 0 to n - 1; red and blue -- two lists of directed edges {from, to}, one
// list per colour. Returns an array answer where answer[x] is the length of the shortest path from
// node 0 to node x whose edges alternate in colour (red, blue, red, ... or blue, red, blue, ...),
// or -1 if there is none. On LeetCode: 1 <= n <= 100, 0 <= red.length, blue.length <= 400,
// 0 <= from, to < n; self-edges and parallel edges are allowed.
//
// The one idea: being at a node is not enough, because which edge you may take next depends on the
// colour of the edge you arrived by. So the thing the queue holds is a node TOGETHER with the colour
// of the last edge used, so there are 2 * n states, not n nodes. From a state with last colour c you
// may follow only edges of the other colour. Node 0 starts in both states, since its first edge can
// be either colour. Every edge costs one, so breadth first search in rounds gives the shortest
// lengths: the first round in which node x comes off the queue in either state is answer[x]. A state
// is marked visited when it comes off the queue, and an entry whose state is already marked is
// skipped: a state can be queued several times in one round, and expanding every copy would double
// the queue each round.
//
// Time: O(n + R + B) -- each of the 2n states is expanded once and scans the edges of one colour.
// Space: O(n + R + B) -- the two adjacency lists, visited, and the queue.
// (R and B are the numbers of red and blue edges.)

public final class ShortestAlternatingPaths {

	public static int[] bfs(int n, int[][] red, int[][] blue) {
		int[] path = new int[n];
		Arrays.fill(path, -1); // ANSWER: -1 until a node is reached
		boolean[][] visited = new boolean[n][2]; // STATE: visited[node][colour of the last edge]
		List<Integer>[][] graph = graph(n, red, blue);
		Deque<Edge> queue = new ArrayDeque<>();
		queue.offer(new Edge(0, 0)); // START: node 0, as if the last edge were red
		queue.offer(new Edge(0, 1)); // START: node 0, as if the last edge were blue
		int level = 0; // LEVEL: edges used by the paths in the current round
		while (!queue.isEmpty()) {
			for (int i = queue.size(); i > 0; i--) { // ROUND: one level
				Edge edge = queue.poll(); // TAKE
				int node = edge.node;
				int color = edge.color;
				if (visited[node][color]) {
					continue; // SKIP: this state was already expanded
				}
				if (path[node] == -1) {
					path[node] = level; // RECORD: first time the node is reached in any colour
				}
				visited[node][color] = true;
				color ^= 1; // flip color
				for (int neighbour : graph[color][node]) { // WALK: edges of the other colour only
					if (!visited[neighbour][color]) {
						queue.offer(new Edge(neighbour, color));
					}
				}
			}
			level += 1;
		}
		return path; // RETURN
	}

	private static List<Integer>[][] graph(int n, int[][] red, int[][] blue) {
		List<Integer>[][] graph = new List[2][n];
		for (List<Integer>[] edges : graph) {
			Arrays.setAll(edges, e -> new ArrayList<>());
		}
		for (int[] edge : red) {
			graph[0][edge[0]].add(edge[1]); // graph[0] holds the red edges
		}
		for (int[] edge : blue) {
			graph[1][edge[0]].add(edge[1]); // graph[1] holds the blue edges
		}
		return graph;
	}

	public static void main(String[] args) {
		int[][] red1 = { { 0, 1 }, { 1, 2 } };
		int[][] blue1 = {};
		System.out.println(Arrays.toString(bfs(3, red1, blue1))); // [0, 1, -1]

		int[][] red2 = { { 0, 1 } };
		int[][] blue2 = { { 2, 1 } };
		System.out.println(Arrays.toString(bfs(3, red2, blue2))); // [0, 1, -1]

		int[][] red3 = { { 0, 1 } };
		int[][] blue3 = { { 1, 2 } };
		System.out.println(Arrays.toString(bfs(3, red3, blue3))); // [0, 1, 2]
	}

	private static class Edge {
		private int node;
		private int color;

		public Edge(int node, int color) {
			this.node = node;
			this.color = color;
		}
	}
}
