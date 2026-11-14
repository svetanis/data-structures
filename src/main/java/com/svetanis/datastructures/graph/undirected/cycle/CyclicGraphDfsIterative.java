package com.svetanis.datastructures.graph.undirected.cycle;

import com.svetanis.datastructures.graph.undirected.Graph;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

// Does an undirected graph contain a cycle? -- the same test as CyclicGraphDfs, with an explicit
// stack instead of recursion.
//
// Input: a Graph g, simple (no parallel edges, no self-loops). Not a LeetCode problem. Returns
// true when some sequence of three or more distinct vertices is joined in a loop. As in the
// recursive version, a parallel edge or a self-loop is reported as a cycle.
//
// The one idea: in a forest a depth-first walk reaches each vertex by exactly one edge, the edge
// from its parent; a neighbour already reached that is not the parent was reached some other
// way, and the two ways make a cycle. Each vertex is entered once, so parent[v] holds the vertex
// it was entered from (-1 for the first vertex of a component). The stack holds the vertices on
// the walk from there to the current one, and beside each its neighbours not yet read -- the call
// and return of the recursive version, in the same order. The parent is compared as a vertex
// number, not an edge, so a second copy of the parent edge counts as a cycle.
//
// Time: O(V + E) -- each vertex is pushed once and each adjacency entry is read once.
// Space: O(V) -- the visited flags, the parent array and the stack.
//
// Siblings: CyclicGraphDfs -- recursive; overflows the default stack on a path of about 10,000
// vertices, where this version does not. CyclicGraphBfs -- the same test, breadth first.

public final class CyclicGraphDfsIterative {

  public static boolean isCyclic(Graph g) {
    int size = g.size();
    boolean[] visited = new boolean[size];
    int[] parent = new int[size];
    for (int v = 0; v < size; v++) {
      if (!visited[v] && isCyclic(g, v, visited, parent)) { // NEW COMPONENT: no parent
        return true;
      }
    }
    return false;
  }

  private static boolean isCyclic(Graph g, int src, boolean[] visited, int[] parent) {
    visited[src] = true;
    parent[src] = -1; // BASE: src has no parent
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
        parent[u] = v; // RECORD: v becomes u's parent
        vertices.push(u); // WALK: go down into u
        unread.push(g.adj(u).iterator());
      } else if (u != parent[v]) { // CYCLE: u was reached another way
        return true;
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

    // the same pair added twice: reported as a cycle, as in CyclicGraphDfs
    Graph repeated = new Graph(3);
    repeated.addEdge(0, 1);
    repeated.addEdge(1, 0);
    System.out.println(isCyclic(repeated)); // true
  }
}
