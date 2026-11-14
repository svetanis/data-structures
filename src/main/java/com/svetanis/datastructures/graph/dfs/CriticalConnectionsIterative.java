package com.svetanis.datastructures.graph.dfs;

import static java.util.Arrays.asList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

// 1192. Critical Connections in a Network
//
// Input: n servers numbered 0 .. n - 1; connections -- undirected links [a, b], and every server
// can reach every other. Returns every link whose removal cuts some server off (a bridge), in
// any order. On LeetCode: 2 <= n <= 10^5, n - 1 <= connections.length <= 10^5, no self-links, no
// repeated links.
//
// The one idea: the same walk and the same two numbers as CriticalConnections -- discovery[x] is
// how deep server x was when the walk first reached it, lowest[x] the shallowest depth x or
// anything below it can get back to by a link other than the one the walk came down, and the
// link from node to its child is critical exactly when lowest[child] > discovery[node]. A stack
// replaces the recursion. One stack entry is a server whose links are still being looked at;
// next[x] says which of x's links to look at next, so a server can be left on the stack while
// its child is walked and picked up where it stopped. When a server has no links left it is
// popped, and only then is everything below it known: that is the moment the recursive version
// returns from the child, so the parent's lowest is updated and the link is tested there.
//
// Time: O(V + E) -- every server is pushed once and every link is looked at twice.
// Space: O(V + E) -- the adjacency lists, the four arrays and the stack.
//
// Siblings: CriticalConnections is the recursive walk, shorter to write but it overflows a default
// Java stack on a very long path of servers.

public final class CriticalConnectionsIterative {

	public static List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
		int[] lowest = new int[n];
		int[] discovery = new int[n];
		int[] parent = new int[n];
		int[] next = new int[n]; // position in the server's list of the next link to look at
		List<Integer>[] graph = graph(n, connections);
		List<List<Integer>> list = new ArrayList<>();
		Deque<Integer> stack = new ArrayDeque<>();
		discovery[0] = lowest[0] = 1; // MARK: server 0 is at depth 1
		parent[0] = -1;
		stack.push(0);
		while (!stack.isEmpty()) {
			int node = stack.peek();
			if (next[node] < graph[node].size()) {
				int neighbor = graph[node].get(next[node]++); // TAKE: node's next link
				if (neighbor == parent[node]) {
					continue; // SKIP: the link the walk came down
				}
				if (discovery[neighbor] == 0) { // not visited yet
					discovery[neighbor] = lowest[neighbor] = discovery[node] + 1; // MARK: one deeper
					parent[neighbor] = node;
					stack.push(neighbor); // WALK: go one deeper; node waits on the stack
				} else {
					lowest[node] = Math.min(lowest[node], discovery[neighbor]); // RECORD: a link back up
				}
			} else {
				stack.pop(); // DONE: every link of node looked at, so everything below it is known
				int up = parent[node];
				if (up != -1) {
					lowest[up] = Math.min(lowest[up], lowest[node]); // RECORD: what the child can reach
					if (lowest[node] > discovery[up]) {
						list.add(Arrays.asList(up, node)); // TAKE: nothing under the child gets back past up
					}
				}
			}
		}
		return list;
	}

	private static List<Integer>[] graph(int n, List<List<Integer>> connections) {
		@SuppressWarnings("unchecked")
		List<Integer>[] graph = new List[n];
		Arrays.setAll(graph, k -> new ArrayList<>());
		for (List<Integer> edge : connections) { // BUILD: both directions
			int src = edge.get(0);
			int dst = edge.get(1);
			graph[src].add(dst);
			graph[dst].add(src);
		}
		return graph;
	}

	public static void main(String[] args) {
		List<List<Integer>> list1 = new ArrayList<>();
		list1.add(asList(0, 1));
		list1.add(asList(1, 2));
		list1.add(asList(2, 0));
		list1.add(asList(1, 3));
		System.out.println(criticalConnections(4, list1)); // [[1, 3]]

		List<List<Integer>> list2 = new ArrayList<>();
		list2.add(asList(0, 1));
		System.out.println(criticalConnections(2, list2)); // [[0, 1]]
	}
}
