package com.svetanis.datastructures.graph.directed.ts.schedule;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

// 1462. Course Schedule IV
//
// Input: n courses 0..n-1, int[][] prerequisites where [a, b] means course a must be taken BEFORE
// course b (the opposite order of 207 and 210), and int[][] queries of pairs [u, v]. On LeetCode:
// 2 <= n <= 100, pairs are distinct, the graph has no loop, at most n * (n - 1) / 2 pairs and queries.
// Returns, per query, whether u is a prerequisite of v -- directly OR through a chain of courses.
//
// The one idea: f[x][y] is true when x must come (somewhere) before y. One arrow a -> b gives
// f[a][b] = true. Take courses in an order where every course comes after all of its prerequisites
// (the topological order, by repeatedly taking a course nobody still waits on). When course c is
// taken, every course that reaches c is also a prerequisite of each course d that c points to, so
// for each arrow c -> d: f[c][d] = true, and every row x with f[x][c] true also gets f[x][d] = true.
// This is safe because c comes off the queue only after all arrows INTO c were processed, so
// column c is final. After that each query is one table lookup.
//
// Time: O(n * E + Q) -- every arrow merges one column of n entries; then one lookup per query.
// Space: O(n^2) -- the table f.

public final class CourseScheduleIV {

	public static List<Boolean> checkIfPrerequisite(int n, int[][] prerequisites, int[][] queries){
		int[] inDegree = new int[n];
		boolean[][] f = new boolean[n][n]; // CARDS: f[x][y] -- x must be taken before y
		List<List<Integer>> graph = graphInit(n);
		for (int[] prerequisite : prerequisites) {
			int course = prerequisite[1];        // 1462: [a, b] means a BEFORE b --
			int prereq = prerequisite[0];        // the opposite of 207 and 210
			graph.get(prereq).add(course);       // ARROW: prereq -> course
			inDegree[course]++;
		}
		topoSort(n, graph, inDegree, f);
		List<Boolean> list = new ArrayList<>();
		for(int[] query : queries) {
			list.add(f[query[0]][query[1]]);     // ANSWER: one lookup
		}
		return list;
	}

	private static void topoSort(int n, List<List<Integer>> g, int[] inDegree, boolean[][] f) {
		Queue<Integer> queue = sources(inDegree);
		while (!queue.isEmpty()) {
			int course = queue.poll();
			for (int neighbor : g.get(course)) {
				f[course][neighbor] = true;      // DIRECT: the arrow itself
				for(int pre = 0; pre < n; pre++) {
					// MERGE: everything that reaches course now reaches neighbor.
					// safe because course came off the queue, so its column is final
					f[pre][neighbor] |= f[pre][course];
				}
				inDegree[neighbor]--;
				if (inDegree[neighbor] == 0) {
					queue.add(neighbor);         // READY: its last prerequisite was just taken
				}
			}
		}
	}

	private static Queue<Integer> sources(int[] inDegree) {
		Queue<Integer> queue = new ArrayDeque<>();
		// all vertices with 0 in-degree
		for (int i = 0; i < inDegree.length; i++) {
			if (inDegree[i] == 0) {
				queue.add(i);
			}
		}
		return queue;
	}

	private static List<List<Integer>> graphInit(int n) {
		List<List<Integer>> list = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			list.add(new ArrayList<>());
		}
		return list;
	}

	public static void main(String[] args) {
		int[][] p1 = {{1,0}};
		int[][] q1 = {{0,1},{1,0}};
		System.out.println(checkIfPrerequisite(2, p1, q1)); // [false, true]
		int[][] p3 = {{1,2},{1,0},{2,0}};
		int[][] q3 = {{1,0},{1,2}};
		System.out.println(checkIfPrerequisite(3, p3, q3)); // [true, true]
	}
}
