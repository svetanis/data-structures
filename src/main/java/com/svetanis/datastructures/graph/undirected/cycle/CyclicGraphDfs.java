package com.svetanis.datastructures.graph.undirected.cycle;

import com.svetanis.datastructures.graph.undirected.Graph;

// Does an undirected graph contain a cycle?
//
// Input: a Graph g, simple (no parallel edges, no self-loops). No statement fixes the sizes; this
// is not a LeetCode problem and has not been run against a judge. Returns true when some
// sequence of three or more distinct vertices is joined in a loop. Outside that input: a parallel
// edge or a self-loop is reported as a cycle (see main).
//
// The one idea: a graph without a cycle is a forest, and in a forest a depth-first walk reaches
// each vertex by exactly one edge, the edge from the vertex it came from, its parent. So while
// walking, a neighbour that has already been reached and is not the parent was reached some
// other way, and the two ways together make a cycle. The parent is carried down in the call,
// -1 for the first vertex of a component, which has none. It is compared as a vertex number, not
// as an edge, which is why a second copy of the parent edge counts as a cycle.
//
// Time: O(V + E) -- each vertex is entered once and each adjacency entry is read once.
// Space: O(V) -- the visited flags, plus the recursion, one call per vertex along a chain.
//
// Known limit: the recursion goes one call deeper per vertex along a chain, so a path of about
// 10,000 vertices overflows the default stack. CyclicGraphDfsIterative is the same test with an
// explicit stack.
//
// Siblings: CyclicGraphBfs -- the same test, breadth first, with a parent array.
// CyclicGraphDfsIterative -- this walk with an explicit stack.

public final class CyclicGraphDfs {

  public static boolean isCyclic(Graph g) {
    int size = g.size();
    boolean[] visited = new boolean[size];
    for (int v = 0; v < size; v++) {
      if (!visited[v] && isCyclic(g, v, visited, -1)) { // NEW COMPONENT: no parent
        return true;
      }
    }
    return false;
  }

  private static boolean isCyclic(Graph g, int v, boolean[] visited, int parent) {

    visited[v] = true;
    for (int u : g.adj(v)) {
      if (!visited[u]) {
        if (isCyclic(g, u, visited, v)) { // WALK: v becomes u's parent
          return true;
        }
      } else if (u != parent) {
        // if an adjacent is visited and
        // not parent of current vertex
        return true; // then there is a cycle
      }
    }
    return false;
  }

  public static void main(String[] args) {
    // a real triangle 0-1-2-0, with a tail hanging off it
    Graph g = new Graph(5);
    g.addEdge(0, 1);
    g.addEdge(1, 2);
    g.addEdge(2, 0);
    g.addEdge(0, 3);
    g.addEdge(3, 4);
    System.out.println(isCyclic(g)); // true

    // a tree: no cycle anywhere
    Graph g2 = new Graph(3);
    g2.addEdge(0, 1);
    g2.addEdge(1, 2);
    System.out.println(isCyclic(g2)); // false

    // the same pair added twice. There is no simple cycle here, and this
    // still reports one: u != parent compares node NUMBERS, so the second
    // copy of the edge looks like a second way back. Reading it as a
    // multigraph the answer is right; reading it as a set of edges it is not.
    Graph repeated = new Graph(3);
    repeated.addEdge(0, 1);
    repeated.addEdge(1, 0);
    System.out.println(isCyclic(repeated)); // true
  }
}
