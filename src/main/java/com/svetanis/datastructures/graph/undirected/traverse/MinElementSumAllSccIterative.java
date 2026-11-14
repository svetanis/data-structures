package com.svetanis.datastructures.graph.undirected.traverse;

import static java.lang.Math.min;

import com.svetanis.datastructures.graph.undirected.Graph;
import java.util.ArrayDeque;
import java.util.Deque;

// Sum of the minimum elements in all connected components of an undirected graph -- the same sum
// as MinElementSumAllScc, with an explicit stack instead of recursion.
//
// Input: a Graph g on the vertices 0 .. g.size() - 1; the element of a vertex is its number. Not
// a LeetCode problem. Returns the sum, over every connected component, of the smallest vertex
// number in it; a vertex with no edge is a component of its own, vertex 0 included.
//
// The one idea: the outer loop meets the vertices in increasing order, so the first unvisited
// vertex v is the smallest one of its component. The walk from v keeps the smallest vertex number
// it sees; the stack holds vertices that are marked but whose neighbours are not read yet, and a
// vertex is marked when it is pushed, so it is pushed once.
//
// Time: O(V + E) -- each vertex is pushed once and each adjacency entry is read once.
// Space: O(V) -- the visited flags and the stack.
//
// Siblings: MinElementSumAllScc -- recursive; overflows the default stack on a path of about
// 10,000 vertices, where this version does not.

public final class MinElementSumAllSccIterative {

  public static int dfs(Graph g) {
    int size = g.size();
    boolean[] visited = new boolean[size];

    int sum = 0;
    for (int v = 0; v < size; ++v) {
      if (!visited[v]) {
        sum += smallestInComponent(g, v, visited); // TAKE: the component's minimum
      }
    }
    return sum;
  }

  private static int smallestInComponent(Graph g, int start, boolean[] visited) {
    int smallest = start; // BASE: start is the smallest vertex of its component
    Deque<Integer> stack = new ArrayDeque<>();
    visited[start] = true;
    stack.push(start);
    while (!stack.isEmpty()) {
      int v = stack.pop();
      for (int u : g.adj(v)) {
        smallest = min(smallest, u); // RECORD: the smallest vertex seen in this component
        if (!visited[u]) {
          visited[u] = true; // MARK when pushed, so u is pushed once
          stack.push(u);
        }
      }
    }
    return smallest;
  }

  public static void main(String[] args) {
    Graph g1 = new Graph(11);
    g1.addEdge(1, 2);
    g1.addEdge(3, 4);
    g1.addEdge(5, 6);
    g1.addEdge(7, 8);
    g1.addEdge(9, 10);

    // components {0}, {1, 2}, {3, 4}, {5, 6}, {7, 8}, {9, 10}: 0 + 1 + 3 + 5 + 7 + 9
    System.out.println(dfs(g1)); // 25

    Graph g2 = new Graph(7); // the numbers 1 .. 6, so 7 vertices
    g2.addEdge(1, 4);
    g2.addEdge(4, 5);

    // components {0}, {1, 4, 5}, {2}, {3}, {6}: 0 + 1 + 2 + 3 + 6
    System.out.println(dfs(g2)); // 12
  }
}
