package com.svetanis.datastructures.graph.dfs;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 1192. Critical Connections in a Network
//
// Input: n servers numbered 0 .. n - 1; connections -- undirected links [a, b], and every server
// can reach every other. Returns every link whose removal cuts some server off (a bridge), in
// any order. On LeetCode: 2 <= n <= 10^5, n - 1 <= connections.length <= 10^5, no self-links, no
// repeated links.
//
// The one idea: walk the network from server 0, going one link deeper each step. discovery[x]
// holds how deep server x was when the walk first reached it (server 0 is 1). lowest[x] holds the
// shallowest depth that x, or anything below it in the walk, can get back to by one link that is
// not the one the walk came down. A link from node to its child neighbor is critical exactly
// when lowest[neighbor] > discovery[node]: nothing under the child can reach node or anything
// above it except through this link. A link to an already-reached server is a way back up, so it
// can lower lowest[node] to that server's depth. The link back to the parent is skipped, since
// it is the link the walk came down (no repeated links, so it is the only one).
//
// Known limit: the walk is one call deeper per server along a path, so a path of 10^4 servers
// overflows a default JVM stack and LeetCode allows 10^5. The code stays recursive.
//
// Time: O(V + E) -- every server is entered once and every link is looked at twice.
// Space: O(V + E) -- the adjacency lists and the two arrays, plus the recursion.
//
// Siblings: CriticalConnectionsIterative is the same walk with a stack, for very long paths of
// servers.

public final class CriticalConnections {

	public static List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
		int[] lowest = new int[n];
		int[] discovery = new int[n];
		List<Integer>[] graph = graph(n, connections);
		List<List<Integer>> list = new ArrayList<>();
		dfs(graph, 0, -1, 0, discovery, lowest, list);
		return list;
	}

	private static void dfs(List<Integer>[] g, int node, int parent, int current,
			int[] discovery, int[] lowest, List<List<Integer>> list) {
		current++;
		discovery[node] = lowest[node] = current; // MARK: depth of this server

		for (int neighbor : g[node]) {
			if (neighbor == parent) {
				continue; // SKIP: the link the walk came down
			}
			// not visited yet
			if (discovery[neighbor] == 0) {
				dfs(g, neighbor, node, current, discovery, lowest, list); // WALK: go one deeper
				lowest[node] = Math.min(lowest[node], lowest[neighbor]); // RECORD: what the child can reach
				// low time of neighbor is greater than
				// the discovery time of current node
				// this is a critical edge
				if (lowest[neighbor] > discovery[node]) {
					list.add(Arrays.asList(node, neighbor)); // TAKE: nothing under the child gets back past node
				}
			} else {
				// neighbor has been visited
				lowest[node] = Math.min(lowest[node], discovery[neighbor]); // RECORD: a link back up
			}
		}
	}

	private static List<Integer>[] graph(int n, List<List<Integer>> connections) {
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
