package com.svetanis.datastructures.graph.bfs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import com.svetanis.java.base.utils.Print;

// 1129. Shortest Path with Alternating Colors

public final class ShortestAlternatingPaths {
	// Time Complexity: O(V + E)
	// Space Complexity: O(V + E)

	public static int[] bfs(int n, int[][] red, int[][] blue) {
		int[] path = new int[n];
		Arrays.fill(path, -1);
		boolean[][] visited = new boolean[n][2];
		List<Integer>[][] graph = graph(n, red, blue);
		Deque<Edge> queue = new ArrayDeque<>();
		queue.offer(new Edge(0, 0));
		queue.offer(new Edge(0, 1));
		int level = 0;
		while (!queue.isEmpty()) {
			for (int i = queue.size(); i > 0; i--) {
				Edge edge = queue.poll();
				int node = edge.node;
				int color = edge.color;
				if (path[node] == -1) {
					path[node] = level;
				}
				visited[node][color] = true;
				color ^= 1; // flip color
				for (int neighbour : graph[color][node]) {
					if (!visited[neighbour][color]) {
						queue.offer(new Edge(neighbour, color));
					}
				}
			}
			level += 1;
		}
		return path;
	}

	private static List<Integer>[][] graph(int n, int[][] red, int[][] blue) {
		List<Integer>[][] graph = new List[2][n];
		for (List<Integer>[] edges : graph) {
			Arrays.setAll(edges, e -> new ArrayList<>());
		}
		for (int[] edge : red) {
			graph[0][edge[0]].add(edge[1]);
		}
		for (int[] edge : blue) {
			graph[1][edge[0]].add(edge[1]);
		}
		return graph;
	}

	public static void main(String[] args) {
		int[][] red1 = { { 0, 1 }, { 1, 2 } };
		int[][] blue1 = {};
		Print.print(bfs(3, red1, blue1));

		int[][] red2 = { { 0, 1 } };
		int[][] blue2 = { { 2, 1 } };
		Print.print(bfs(3, red2, blue2));
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
