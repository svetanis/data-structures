package com.svetanis.datastructures.graph.undirected.traverse;

import com.svetanis.datastructures.graph.undirected.Graph;

// Depth-first traversal of an acyclic undirected graph (a tree), from vertex 1.
//
// Input: a Graph g with no cycle, on the vertices 1 .. g.size() - 1; vertex 0 is not used. No
// statement fixes the sizes; this is not a LeetCode problem and has not been run against a
// judge. Prints the vertices reachable from vertex 1 in depth-first order, each followed by a
// space.
//
// The one idea: a graph with no cycle needs no visited flags. The only way back to a vertex
// already printed is the edge to the vertex we came from, its parent, so skipping the parent is
// enough to visit every vertex once. The parent of the start is 0, which is why vertex 0 must
// not be a vertex of the graph: if it were joined to vertex 1 it would be skipped as the parent.
//
// Time: O(V + E) -- each vertex is entered once and each adjacency entry is read once.
// Space: O(V) -- the recursion, one call per vertex along a chain.
//
// Known limit: the recursion goes one call deeper per vertex along a chain, so a path of about
// 10,000 vertices overflows the default stack. AcyclicGraphDfsIterative is the same walk with an
// explicit stack.
//
// Siblings: AcyclicGraphDfsIterative -- same walk, explicit stack. DFS -- the traversal with
// visited flags, which also works on graphs with cycles.

public final class AcyclicGraphDfs {

  private static int V = 5;

  public static void dfs(Graph g) {
    dfs(g, 1, 0);
  }

  private static void dfs(Graph g, int src, int parent) {
    System.out.print(src + " "); // PRINT: src is reached for the first and only time
    for (int u : g.adj(src)) {
      if (u != parent) { // SKIP: the edge back to where we came from
        dfs(g, u, src); // WALK
      }
    }
  }

  public static void main(String[] args) {
    Graph g = new Graph(V + 1);
    g.addEdge(1, 2);
    g.addEdge(1, 3);
    g.addEdge(2, 4);
    g.addEdge(3, 5);

    dfs(g); // prints 1 2 4 3 5
  }
}
