package com.svetanis.datastructures.graph.undirected.bipartite;

import com.svetanis.datastructures.graph.undirected.Graph;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

// Is the part of a graph reachable from a source vertex bipartite? -- the same walk as
// BipartiteGraphDfs, with an explicit stack instead of recursion.
//
// Input: an undirected Graph g on the vertices 0 .. g.size() - 1, and a source vertex src. Not a
// LeetCode problem. Returns true when the vertices reachable from src can be split into two
// groups so that every edge joins one group to the other; a self-loop makes it false.
//
// The one idea: colors[v] holds the group of vertex v, 0 or 1, and visited[v] says whether v has
// a group yet. The stack holds the vertices on the walk from src to the current one, and beside
// each vertex the neighbours it has not read yet. The top vertex reads its next neighbour: a
// neighbour with no group gets the other group and goes on the stack; one with the same group
// closes an odd loop and the answer is false; a vertex with no neighbours left comes off the
// stack. That is the call and return of the recursive version, in the same order.
//
// Time: O(V + E) -- each vertex is pushed once and each adjacency entry is read once.
// Space: O(V) -- the arrays and the stack, which holds at most one entry per vertex.
//
// Siblings: BipartiteGraphDfs -- recursive; overflows the default stack on a path of about
// 10,000 vertices, where this version does not.

public final class BipartiteGraphDfsIterative {

  public static boolean isBipartite(Graph g, int src) {
    int n = g.size();
    boolean[] visited = new boolean[n + 1];
    int[] colors = new int[n + 1];
    visited[src] = true; // BASE: src is coloured first
    colors[src] = 0;
    Deque<Integer> vertices = new ArrayDeque<>(); // STACK: the walk from src to here
    Deque<Iterator<Integer>> unread = new ArrayDeque<>(); // neighbours each vertex has not read
    vertices.push(src);
    unread.push(g.adj(src).iterator());
    while (!vertices.isEmpty()) {
      int v = vertices.peek();
      Iterator<Integer> it = unread.peek();
      if (!it.hasNext()) {
        vertices.pop(); // RETURN: every neighbour of v is read
        unread.pop();
        continue;
      }
      int u = it.next();
      if (!visited[u]) {
        visited[u] = true;
        colors[u] = 1 - colors[v]; // COLOR: the other group
        vertices.push(u); // WALK: go down into u
        unread.push(g.adj(u).iterator());
      } else if (colors[u] == colors[v]) { // CLASH: same group on both ends of an edge
        return false;
      }
    }
    return true;
  }

  public static void main(String[] args) {
    Graph g = new Graph(7);
    g.addEdge(1, 2);
    g.addEdge(2, 3);
    g.addEdge(3, 4);
    g.addEdge(4, 5);
    g.addEdge(5, 6);
    g.addEdge(6, 1);

    System.out.println(isBipartite(g, 1)); // true, an even loop of six

    Graph triangle = new Graph(4);
    triangle.addEdge(1, 2);
    triangle.addEdge(2, 3);
    triangle.addEdge(3, 1);
    System.out.println(isBipartite(triangle, 1)); // false, an odd loop of three
  }
}
