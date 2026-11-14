package com.svetanis.datastructures.graph.directed.scc;

import static com.svetanis.datastructures.graph.directed.scc.TransposeGraph.transpose;
import static com.svetanis.datastructures.graph.directed.traverse.DFS.explore;
import static com.svetanis.datastructures.graph.directed.ts.TopologicalSorting.fillStack;
import static java.util.Arrays.fill;

import java.util.Stack;

import com.svetanis.datastructures.graph.directed.Graph;

// Print the strongly connected components of a directed graph (Kosaraju's algorithm).
//
// Input: g -- a directed graph on the vertices 0 .. n - 1. A strongly connected component is a
// largest set of vertices in which every vertex can reach every other. Returns: nothing; prints one
// line per component, its vertices separated by spaces (the components come in a definite order,
// the vertices inside a line in the order the second walk meets them).
//
// The one idea: walk the whole graph once and push each vertex on a stack at the moment its walk
// finishes. The vertex on top of the stack finished last, so it lies in a component that no edge
// from any other component leads into (a "source" component). Now reverse every edge and pop the
// stack: a walk from the top vertex in the reversed graph reaches exactly its own component, since
// the only way out of a source component is along an edge that has been turned round. Mark that
// component, print it, pop on; every later start is a vertex outside the components already
// printed, and the same argument holds for it.
//
// Time: O(V + E) -- two walks and one reversal.
// Space: O(V + E) -- the reversed graph, the stack and the marks.
//
// Siblings: StronglyConnected and SccKosarajuBfs only answer whether there is one component.

public final class SccKosarajuDfs {

  public static void scc(Graph g) {

    int size = g.size();
    boolean[] visited = new boolean[size];
    Stack<Integer> stack = new Stack<Integer>();

    // FIRST WALK: push vertices on the stack
    // in the order their walks finish
    fillStack(g, visited, stack);

    // create a reversed graph
    Graph reversed = transpose(g);

    // clear the marks for the second walk
    fill(visited, false);

    // SECOND WALK: pop in stack order, one component each
    process(reversed, visited, stack);
  }

  private static void process(Graph g, boolean[] visited, Stack<Integer> stack) {
    while (!stack.isEmpty()) {
      // pop a vertex from stack
      int v = stack.pop();
      // print strongly connected
      // component of the popped vertex
      if (!visited[v]) { // SKIP a vertex already printed in an earlier component
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
    // one line per component, each vertex followed by a space:
    // 0 1 2
    // 3
    // 4
    scc(g);
  }
}
