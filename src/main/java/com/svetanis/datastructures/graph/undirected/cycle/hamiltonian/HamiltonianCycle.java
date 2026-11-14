package com.svetanis.datastructures.graph.undirected.cycle.hamiltonian;

import static java.util.Arrays.fill;

// Does an undirected graph have a Hamiltonian cycle?
//
// Input: g -- the graph as an n x n adjacency matrix, g[u][v] == 1 when u and v are joined and 0
// otherwise, symmetric, with n >= 3. No statement fixes the sizes; this is not a LeetCode
// problem and has not been run against a judge. A Hamiltonian path visits each vertex exactly
// once; a Hamiltonian cycle is one with an edge from its last vertex back to its first. Returns
// true when a cycle exists and prints it, starting and ending at vertex 0, otherwise prints
// "Solution doesn't exist" and returns false. For n = 2 with an edge it returns true and prints
// "0 1 0" (the one edge used twice); for n = 1 it returns false; n = 0 is not supported.
//
// The one idea: path[i] holds the vertex placed at position i of the cycle, -1 while the
// position is empty. Vertex 0 is fixed at position 0 -- a cycle through every vertex can be
// started anywhere. Fill positions 1, 2, ... in order, trying each vertex that is joined to the
// one before it and not placed yet. If a position has no such vertex, give up on the vertex put
// in the position before it and try the next one (backtrack). When all n positions are filled
// the cycle exists exactly if the last vertex is joined to vertex 0.
//
// Time: O(n * n!) -- up to (n - 1)! orderings of the other vertices are tried, each extended only
// along edges, and every placement is checked against the path so far in O(n).
// Space: O(n) -- the path and the recursion, n deep.

public final class HamiltonianCycle {

  public static boolean cycle(int[][] g) {
    int n = g.length;
    int[] path = new int[n];
    fill(path, -1);
    path[0] = 0; // BASE: the cycle starts at vertex 0

    if (!cycle(g, path, 1)) {
      System.out.println("Solution doesn't exist");
      return false;
    }

    print(path);
    return true;
  }

  private static boolean cycle(int[][] g, int[] path, int index) {
    int n = g.length;

    // base case: if all vertices are included
    // in Hamiltonian cycle then return true
    if (index == n) {
      // and if there is an edge from the last
      // included vertex to the first vertex
      return g[path[index - 1]][path[0]] == 1; // CLOSE: the last vertex must join vertex 0
    }

    // try different vertices as a next candidate in Hamiltonian Cycle
    // we don't try for 0, as we included 0 as starting point
    for (int v = 1; v < n; ++v) {
      // check if this vertex can be added to Hamiltonian Cycle
      if (isSafe(v, g, path, index)) {
        path[index] = v; // TAKE: put v at this position

        // recur to construct rest of the path
        if (cycle(g, path, index + 1)) {
          return true;
        }

        // if adding vertex doesn't lead to a solution
        // then remove it
        path[index] = -1; // UNDO: backtrack
      }
    }

    // no vertex fits at this position
    return false;
  }

  private static boolean isSafe(int v, int[][] g, int[] path, int pos) {
    // check if this vertex is adj vertex
    // of the previously added vertex
    if (g[path[pos - 1]][v] == 0) {
      return false; // SKIP: not joined to the vertex before it
    }

    // check if the vertex has already been included
    for (int i = 0; i < pos; ++i) {
      if (path[i] == v) {
        return false; // SKIP: already placed
      }
    }
    return true;
  }

  private static void print(int[] array) {
    for (int i = 0; i < array.length; i++) {
      System.out.print(array[i] + " ");
    }
    // print the first vertex again to show the complete cycle
    System.out.print(array[0]);
    System.out.println();
  }

  public static void main(String[] args) {

    /* Let us create the following graph
    (0)--(1)--(2)
     |   / \   |
     |  /   \  |
     | /     \ |
    (3)-------(4)    */

    int[][] g1 = { //
        { 0, 1, 0, 1, 0 }, //
        { 1, 0, 1, 1, 1 }, //
        { 0, 1, 0, 0, 1 }, //
        { 1, 1, 0, 0, 1 }, //
        { 0, 1, 1, 1, 0 },//
    };//

    cycle(g1); // prints 0 1 2 4 3 0

    /* Let us create the following graph
    (0)--(1)--(2)
     |   / \   |
     |  /   \  |
     | /     \ |
    (3)       (4)    */

    int[][] g2 = { //
        { 0, 1, 0, 1, 0 }, //
        { 1, 0, 1, 1, 1 }, //
        { 0, 1, 0, 0, 1 }, //
        { 1, 1, 0, 0, 0 }, //
        { 0, 1, 1, 0, 0 },//
    };//

    cycle(g2); // prints Solution doesn't exist
  }

}
