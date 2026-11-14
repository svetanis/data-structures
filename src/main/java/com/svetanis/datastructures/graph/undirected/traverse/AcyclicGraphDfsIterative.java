package com.svetanis.datastructures.graph.undirected.traverse;

import com.svetanis.datastructures.graph.undirected.Graph;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

// Depth-first traversal of an acyclic undirected graph (a tree), from vertex 1 -- the same walk
// as AcyclicGraphDfs, with an explicit stack instead of recursion.
//
// Input: a Graph g with no cycle, on the vertices 1 .. g.size() - 1; vertex 0 is not used. Not a
// LeetCode problem. Prints the vertices reachable from vertex 1 in depth-first order, each
// followed by a space.
//
// The one idea: in a graph with no cycle the only way back to a printed vertex is the edge to
// its parent, so skipping the parent replaces the visited flags. The stack holds the vertices on
// the walk from vertex 1 to the current one, and beside each its neighbours not yet read -- the
// call and return of the recursive version, in the same order. The parent of a vertex is the one
// below it on the stack; the parent of vertex 1 is 0, so vertex 0 must not be in the graph.
//
// Time: O(V + E) -- each vertex is pushed once and each adjacency entry is read once.
// Space: O(V) -- the stack.
//
// Siblings: AcyclicGraphDfs -- recursive; overflows the default stack on a path of about 10,000
// vertices, where this version does not.

public final class AcyclicGraphDfsIterative {

  public static void dfs(Graph g) {
    Deque<Integer> vertices = new ArrayDeque<>(); // STACK: the walk from vertex 1 to here
    Deque<Iterator<Integer>> unread = new ArrayDeque<>(); // neighbours each vertex has not read
    System.out.print(1 + " "); // PRINT: the start
    vertices.push(1);
    unread.push(g.adj(1).iterator());
    while (!vertices.isEmpty()) {
      Iterator<Integer> it = unread.peek();
      if (!it.hasNext()) {
        vertices.pop(); // RETURN: every neighbour is read
        unread.pop();
        continue;
      }
      int u = it.next();
      if (u != parentOf(vertices)) { // SKIP: the edge back to where we came from
        System.out.print(u + " "); // PRINT: u is reached for the first and only time
        vertices.push(u); // WALK: go down into u
        unread.push(g.adj(u).iterator());
      }
    }
  }

  // the parent of the vertex on top: the one below it on the stack, or 0 for the start
  private static int parentOf(Deque<Integer> vertices) {
    Iterator<Integer> it = vertices.iterator(); // iterates from the top down
    it.next(); // the top itself
    return it.hasNext() ? it.next() : 0;
  }

  public static void main(String[] args) {
    Graph g = new Graph(6);
    g.addEdge(1, 2);
    g.addEdge(1, 3);
    g.addEdge(2, 4);
    g.addEdge(3, 5);

    dfs(g); // prints 1 2 4 3 5
  }
}
