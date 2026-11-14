package com.svetanis.datastructures.graph.directed.eulerian;

import static com.svetanis.datastructures.graph.directed.scc.TransposeGraph.transpose;
import static com.svetanis.datastructures.graph.directed.traverse.DFS.dfs;

import com.svetanis.datastructures.graph.directed.Graph;

// Does a directed graph have an Eulerian circuit?
//
// Input: g -- a directed graph on the vertices 0 .. n - 1; a vertex may have no edge at all.
// Returns: true if one walk can follow every edge exactly once and end where it began (a graph
// with no edges: true, the empty walk).
//
// The one idea: a walk that uses every edge once and comes home needs two things. (1) Each time it
// comes into a vertex it must leave again, so every vertex has as many edges entering as leaving.
// (2) The walk is one piece, so every vertex WITH AN EDGE can be reached from every other one. A
// vertex with no edge is never on the walk and does not count. Those two together are enough as
// well as necessary.
//
// Time: O(V * (V + E)) -- the connectivity test is O(V + E), but inEdges(i) searches every
// adjacency list, O(V + E), and it is called once per vertex.
// Space: O(V + E) -- the reversed graph inside the connectivity test.
//
// Siblings: Chained asks the question for strings, with a single walk for connectivity and a count
// of arrows in and out. StronglyConnected is the same two-walk test over every vertex, so it would
// wrongly count a vertex with no edge.

public final class EulerianCircuit {

  public static boolean isEulerianCycle(Graph g) {
    int size = g.size();
    if (!edgesInOnePiece(g)) { // (2) one piece
      return false;
    }

    // (1) check if in-degree and out-degree
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

  // true if every vertex with an edge leaving it reaches, and is reached from, one such vertex;
  // a vertex with only edges entering it fails the in == out check instead
  private static boolean edgesInOnePiece(Graph g) {
    int size = g.size();
    int start = -1;
    for (int i = 0; i < size; i++) {
      if (!g.adj(i).isEmpty()) {
        start = i; // START: any vertex with an edge leaving it
        break;
      }
    }
    if (start == -1) {
      return true; // NO EDGES: nothing to connect
    }
    boolean[] forward = new boolean[size];
    dfs(g, start, forward); // FORWARD: what start reaches
    boolean[] backward = new boolean[size];
    dfs(transpose(g), start, backward); // BACKWARD: what reaches start
    for (int i = 0; i < size; i++) {
      if (!g.adj(i).isEmpty() && !(forward[i] && backward[i])) {
        return false; // a vertex with an edge outside start's piece
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
    System.out.println(isEulerianCycle(g)); // true -- two loops, 0-2-1 and 0-3-4, meeting at 0

    Graph g2 = new Graph(3);
    g2.addEdge(0, 1);
    g2.addEdge(1, 2);
    System.out.println(isEulerianCycle(g2)); // false -- 2 cannot be left, so not one piece

    Graph g3 = new Graph(3);
    g3.addEdge(0, 1);
    g3.addEdge(1, 0);
    g3.addEdge(1, 2);
    g3.addEdge(2, 1);
    g3.addEdge(2, 0);
    System.out.println(isEulerianCycle(g3)); // false -- one piece, but 0 has 1 edge out and 2 in

    Graph g4 = new Graph(3);
    g4.addEdge(0, 1);
    g4.addEdge(1, 0);
    System.out.println(isEulerianCycle(g4)); // true -- 0-1-0 uses both edges; 2 has none

    Graph g5 = new Graph(4);
    g5.addEdge(0, 1);
    g5.addEdge(1, 0);
    g5.addEdge(2, 3);
    g5.addEdge(3, 2);
    System.out.println(isEulerianCycle(g5)); // false -- two separate loops, no single walk
  }
}
