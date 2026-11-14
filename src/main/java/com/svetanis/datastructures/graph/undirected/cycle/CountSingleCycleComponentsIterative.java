package com.svetanis.datastructures.graph.undirected.cycle;

import com.svetanis.datastructures.graph.undirected.Graph;
import java.util.ArrayDeque;
import java.util.Deque;

// Count the single-cycle components of an undirected simple graph -- the same count as
// CountSingleCycleComponents, with an explicit stack instead of recursion.
//
// Input: a Graph g, simple (no parallel edges, no self-loops). Not a LeetCode problem. A
// single-cycle component is a connected component in which every vertex has degree 2. Returns
// how many there are; an isolated vertex has degree 0, so it is not one.
//
// The one idea: a component is a single cycle exactly when every vertex in it has two
// neighbours. Walk each component once; the stack holds vertices found but not yet expanded, and
// a vertex is marked visited when it is pushed, so it is pushed once. While expanding, check the
// degree of each vertex -- one vertex of degree other than 2 spoils the whole component.
//
// Time: O(V + E) -- each vertex is pushed and popped once and each adjacency entry is read once.
// Space: O(V) -- the visited flags and the stack.
//
// Siblings: CountSingleCycleComponents -- recursive; overflows the default stack on a component
// of about 10,000 vertices, where this version does not.

public final class CountSingleCycleComponentsIterative {

  public static int count(Graph g) {
    int size = g.size();
    boolean[] visited = new boolean[size];

    int count = 0;
    for (int v = 0; v < size; ++v) {
      if (!visited[v] && singleCycle(g, v, visited)) { // NEW COMPONENT, then test it
        count++; // RECORD: every vertex of the component has degree 2
      }
    }
    return count;
  }

  // walks the whole component of start; true when every vertex in it has degree 2
  private static boolean singleCycle(Graph g, int start, boolean[] visited) {
    boolean allTwo = true;
    Deque<Integer> stack = new ArrayDeque<>();
    visited[start] = true;
    stack.push(start);
    while (!stack.isEmpty()) {
      int v = stack.pop();
      if (g.degree(v) != 2) { // CLASH: a dead end or a branch
        allTwo = false; // keep walking: the rest of the component must still be marked visited
      }
      for (int u : g.adj(v)) {
        if (!visited[u]) {
          visited[u] = true;
          stack.push(u); // WALK: expand u later
        }
      }
    }
    return allTwo;
  }

  public static void main(String[] args) {
    Graph g = new Graph(16);
    g.addEdge(1, 10);
    g.addEdge(1, 5);
    g.addEdge(5, 10);
    g.addEdge(2, 9);
    g.addEdge(9, 15);
    g.addEdge(2, 15);
    g.addEdge(2, 12);
    g.addEdge(12, 15);
    g.addEdge(13, 8);
    g.addEdge(6, 14);
    g.addEdge(14, 3);
    g.addEdge(3, 7);
    g.addEdge(7, 11);
    g.addEdge(11, 6);

    // the triangle 1-5-10 and the loop of five 3-6-7-11-14; the piece with 2, 9, 12, 15 has a
    // vertex of degree 3 and the pair 8-13 has two of degree 1
    System.out.println(count(g)); // 2
  }
}
