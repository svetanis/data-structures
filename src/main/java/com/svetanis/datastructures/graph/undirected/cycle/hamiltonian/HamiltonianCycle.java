package com.svetanis.datastructures.graph.undirected.cycle.hamiltonian;

import static java.util.Arrays.fill;

// Hamiltonian Path in an undirected graph is a path that visits each vertex exactly once. 
// A Hamiltonian cycle (or Hamiltonian circuit) is a Hamiltonian Path such that 
// there is an edge (in graph) from the last vertex to the first vertex of the Hamiltonian Path. 


public final class HamiltonianCycle {

  public static boolean cycle(int[][] g) {
    int n = g.length;
    int[] path = new int[n];
    fill(path, -1);
    path[0] = 0;

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
      return g[path[index - 1]][path[0]] == 1;
    }

    // try different vertices as a next candidate in Hamiltonian Cycle
    // we don't try for 0, as we included 0 as starting point
    for (int v = 1; v < n; ++v) {
      // check if this vertex can be added to Hamiltonian Cycle
      if (isSafe(v, g, path, index)) {
        path[index] = v;

        // recur to construct rest of the path
        if (cycle(g, path, index + 1)) {
          return true;
        }

        // if adding vertex doesn't lead to a solution
        // then remove it
        path[index] = -1;
      }
    }

    // if no color can be assigned to this vertex, return false
    return false;
  }

  private static boolean isSafe(int v, int[][] g, int[] path, int pos) {
    // check if this vertex is adj vertex 
    // of the previously added vertex
    if (g[path[pos - 1]][v] == 0) {
      return false;
    }

    // check if the vertex has already been included
    for (int i = 0; i < pos; ++i) {
      if (path[i] == v) {
        return false;
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

    cycle(g1);

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

    cycle(g2);
  }

}
