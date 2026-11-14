package com.svetanis.datastructures.graph.undirected.traverse;

import com.svetanis.datastructures.graph.undirected.Graph;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

// Lexicographically smallest traversal of an undirected graph from a source vertex.
//
// Input: a Graph g on the vertices 0 .. g.size() - 1 and a source vertex src. No statement is
// available for this file; it is not a LeetCode problem and has not been run against a judge,
// so the contract below is read from the code. Returns the vertices reachable from src in the
// order produced by always taking the smallest vertex among those reached but not yet taken.
//
// The one idea: this is BFS with the queue replaced by a priority queue. The queue holds the
// vertices that are reached but not yet taken; the smallest comes out first, so at every step the
// traversal takes the smallest vertex it could take, which makes the sequence the smallest
// possible among all sequences where each vertex is next to one earlier in the sequence. It is
// not a level-by-level order: a vertex two edges from src can come before a vertex one edge
// away if it is smaller (in main, 3 is two edges from 1 and comes before 5, which is one edge
// away). A vertex is marked when it is queued, so it is queued once.
//
// Time: O(V log V + E) -- each vertex is queued once at O(log V), and each adjacency entry is
// read once.
// Space: O(V) -- the visited flags, the queue and the result.
//
// Siblings: BFS -- the same loop with a plain queue, which gives the ring-by-ring order.

public final class LexicographicallySmallestBFS {

  public static List<Integer> bfs(Graph g, int src) {
    int size = g.size();

    boolean[] visited = new boolean[size];
    visited[src] = true; // BASE: src is reached first

    Queue<Integer> queue = new PriorityQueue<>();
    queue.offer(src);

    List<Integer> list = new ArrayList<>();
    while (!queue.isEmpty()) {
      int v = queue.poll(); // TAKE: the smallest vertex reached so far
      list.add(v);
      for (int u : g.adj(v)) {
        if (!visited[u]) {
          visited[u] = true; // MARK when queued, so u is queued once
          queue.offer(u);
        }
      }
    }
    return list;
  }

  private static void print(List<Integer> list) {
    for (int v : list) {
      System.out.print(v);
      System.out.print(' ');
    }
    System.out.println();
  }

  public static void main(String[] args) {
    Graph graph = new Graph(6);
    graph.addEdge(1, 4);
    graph.addEdge(3, 4);
    graph.addEdge(5, 4);
    graph.addEdge(3, 2);
    graph.addEdge(1, 5);
    print(bfs(graph, 1)); // 1 4 3 2 5
  }
}
