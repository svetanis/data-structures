package com.svetanis.datastructures.graph.directed.eulerian;

import static com.svetanis.datastructures.graph.directed.scc.StronglyConnected.isStronglyConnected;

import com.svetanis.datastructures.graph.directed.Graph;

public final class EulerianCircuit {

  public static boolean isEulerianCycle(Graph g) {
    // Time Complexity: O(V + E)

    int size = g.size();
    if (!isStronglyConnected(g)) {
      return false;
    }

    // check if in-degree and out-degree
    // of every vertex is same
    for (int i = 0; i < size; i++) {
      int in = g.inEdges(i).size();
      int out = g.outEdges(i).size();
      if (in != out) {
        return false;
      }
    }
    return true;
  }

  public static void main(String[] args) {
    Graph g = new Graph(5);
    g.addEdge(1, 0);
    g.addEdge(0, 2);
    g.addEdge(2, 1);
    g.addEdge(0, 3);
    g.addEdge(3, 4);
    g.addEdge(4, 0);
    System.out.println(isEulerianCycle(g));
  }
}
