package com.svetanis.datastructures.graph.undirected.traverse;

import com.svetanis.datastructures.graph.undirected.Graph;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

// Depth-first traversal of every component of an undirected graph -- the same three methods as
// DFS, with an explicit stack instead of recursion.
//
// Input: a Graph g on the vertices 0 .. g.size() - 1. Not a LeetCode problem. dfs(g) prints each
// component's vertices in depth-first order, one component per line, then "components: " and
// their number. explore(g, v, visited) prints one component's vertices from v; dfs(g, v, visited)
// does the same walk without printing.
//
// The one idea: visited[v] says whether the walk has entered v. The stack holds the vertices on
// the walk from the start to the current one, and beside each its neighbours not yet read. The
// top vertex reads its next neighbour; an unmarked one is marked, printed and pushed, and a
// vertex with no neighbours left is popped -- the call and return of the recursive version, in
// the same order, so the printed order is the same.
//
// Time: O(V + E) -- each vertex is pushed once and each adjacency entry is read once.
// Space: O(V) -- the visited flags and the stack.
//
// Siblings: DFS -- recursive; overflows the default stack on a path of about 10,000 vertices,
// where this version does not.

public final class DFSIterative {

  public static void dfs(Graph g) {
    int size = g.size();
    int components = 0;
    boolean[] visited = new boolean[size];
    for (int v = 0; v < size; ++v) {
      if (!visited[v]) {
        components++; // NEW COMPONENT: v is in a part not yet reached
        explore(g, v, visited);
        System.out.println();
      }
    }
    System.out.println("components: " + components);
  }

  public static void explore(Graph g, int v, boolean[] visited) {
    walk(g, v, visited, true);
  }

  public static void dfs(Graph g, int v, boolean[] visited) {
    walk(g, v, visited, false);
  }

  // the walk shared by explore and dfs: the only difference is whether a vertex is printed
  private static void walk(Graph g, int start, boolean[] visited, boolean print) {
    Deque<Integer> vertices = new ArrayDeque<>(); // STACK: the walk from start to here
    Deque<Iterator<Integer>> unread = new ArrayDeque<>(); // neighbours each vertex has not read
    enter(start, visited, print);
    vertices.push(start);
    unread.push(g.adj(start).iterator());
    while (!vertices.isEmpty()) {
      Iterator<Integer> it = unread.peek();
      if (!it.hasNext()) {
        vertices.pop(); // RETURN: every neighbour is read
        unread.pop();
        continue;
      }
      int u = it.next();
      if (!visited[u]) {
        enter(u, visited, print);
        vertices.push(u); // WALK: go down into u
        unread.push(g.adj(u).iterator());
      }
    }
  }

  private static void enter(int v, boolean[] visited, boolean print) {
    visited[v] = true; // MARK: v is entered
    if (print) {
      System.out.print(v + " "); // PRINT: v is entered for the first and only time
    }
  }

  public static void main(String[] args) {
    Graph g = new Graph(8);
    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(0, 3);
    g.addEdge(1, 4);
    g.addEdge(3, 4);
    g.addEdge(5, 6);
    g.addEdge(5, 7);
    g.addEdge(6, 7);

    // prints "0 1 4 3 2 ", "5 6 7 " and "components: 2"
    dfs(g);
  }
}
