package com.svetanis.datastructures.graph.undirected.path;

import static java.util.Arrays.fill;

import com.svetanis.datastructures.graph.undirected.Graph;
import java.util.ArrayDeque;
import java.util.Queue;

// Longest path in a tree, by two breadth-first walks.
//
// Input: a Graph g that is a tree -- connected, no cycle, n vertices and n - 1 edges. No
// statement fixes the sizes; this is not a LeetCode problem and has not been run against a
// judge. Prints "len= " and the length, in edges, of the longest path in the tree (its
// diameter), then "from " and the two vertices the path joins. On a graph that is not a tree the
// answer can be too small.
//
// The one idea: from any vertex of a tree, the vertex farthest away is one end of some longest
// path. So the first walk, from vertex 0, finds that end; the second walk, from it, finds the
// farthest vertex from there, and that distance is the length of the longest path. dist[v] holds
// the number of edges between the walk's start and v, -1 for a vertex not reached. This is a
// property of trees: where a cycle gives two routes to a vertex, the farthest vertex from the
// first start need not be an end of a longest path.
//
// Time: O(V + E) -- two walks, each reaching every vertex once and reading each adjacency entry
// once; in a tree E = V - 1.
// Space: O(V) -- the visited flags, the distances and the queue.

public final class LongestPathLenBfs {

  private static final int V = 10;

  public static void longestPathLen(Graph g) {
    int[] first = bfs(g, 0); // FARTHEST: from vertex 0, the vertex farthest away
    int[] second = bfs(g, first[1]); // FARTHEST: from that end, the vertex farthest away
    System.out.println("len= " + second[0]);
    System.out.println("from " + first[1] + " to " + second[1]);
  }

  // returns { the largest distance from src, a vertex at that distance }
  private static int[] bfs(Graph g, int src) {
    int n = g.size();
    boolean[] visited = new boolean[n];
    visited[src] = true;
    int[] dist = new int[n];
    fill(dist, -1);
    dist[src] = 0; // BASE: src is 0 edges from itself

    Queue<Integer> queue = new ArrayDeque<>();
    queue.offer(src);

    while (!queue.isEmpty()) {
      int v = queue.poll();
      for (int u : g.adj(v)) {
        if (!visited[u]) {
          visited[u] = true;
          dist[u] = dist[v] + 1; // RECORD: one edge farther than v
          queue.offer(u);
        }
      }
    }

    int max = 0;
    int index = 0;
    for (int i = 0; i < n; i++) {
      if (dist[i] > max) {
        max = dist[i]; // TAKE: the farthest vertex so far
        index = i;
      }
    }
    return new int[] {max, index};
  }

  public static void main(String[] args) {
    Graph g = new Graph(V);
    g.addEdge(0, 1);
    g.addEdge(1, 2);
    g.addEdge(2, 3);
    g.addEdge(2, 9);
    g.addEdge(2, 4);
    g.addEdge(4, 5);
    g.addEdge(1, 6);
    g.addEdge(6, 7);
    g.addEdge(6, 8);

    // prints "len= 5" and "from 5 to 7": 5-4-2-1-6-7
    longestPathLen(g);
  }

}
