package com.svetanis.datastructures.graph.directed.scc;

import static com.svetanis.datastructures.graph.directed.scc.TransposeGraph.transpose;
import static com.svetanis.datastructures.graph.directed.traverse.DFS.explore;
import static com.svetanis.datastructures.graph.directed.ts.TopologicalSorting.fillStack;
import static java.util.Arrays.fill;

import java.util.Stack;

import com.svetanis.datastructures.graph.directed.Graph;

public final class SccKosarajuDfs {

  // Time complexity: O(V + E)
  public static void scc(Graph g) {

    int size = g.size();
    boolean[] visited = new boolean[size];
    Stack<Integer> stack = new Stack<Integer>();

    // fill vertices in stack
    // according to their finishing times
    fillStack(g, visited, stack);

    // create a reversed graph
    Graph reversed = transpose(g);

    // mark all the vertices
    // as not visited (for second DFS)
    fill(visited, false);

    // now process all vertices
    // in order defined by Stack
    process(reversed, visited, stack);
  }

  private static void process(Graph g, boolean[] visited, Stack<Integer> stack) {
    while (!stack.isEmpty()) {
      // pop a vertex from stack
      int v = stack.pop();
      // print strongly connected
      // component of the popped vertex
      if (!visited[v]) {
        explore(g, v, visited);
        System.out.println();
      }
    }
  }

  public static void main(String[] args) {
    Graph g = new Graph(5);
    g.addEdge(1, 0);
    g.addEdge(0, 2);
    g.addEdge(2, 1);
    g.addEdge(0, 3);
    g.addEdge(3, 4);
    scc(g);
  }
}
