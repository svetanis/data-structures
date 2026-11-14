package com.svetanis.datastructures.graph.undirected.cycle.eulerian;

import static com.svetanis.datastructures.graph.undirected.traverse.DFS.dfs;

import com.svetanis.datastructures.graph.undirected.Graph;

// Does an undirected graph have an Eulerian path or an Eulerian circuit?
//
// Input: an undirected Graph g; parallel edges and self-loops are allowed (a self-loop adds 2 to
// its vertex's degree). No statement fixes the sizes; this is not a LeetCode problem and has not
// been run against a judge. An Eulerian path visits every edge exactly once; an Eulerian circuit
// is one that ends where it began. Returns 0 if the graph has neither, 1 if it has a path but no
// circuit (Semi-Eulerian), 2 if it has a circuit (Eulerian). A graph with no edges has nothing
// to trace and returns 2; isolated vertices are ignored.
//
// The one idea: a trace uses up edges two at a time at every vertex it passes through -- one in,
// one out -- so a vertex can have odd degree only if the trace starts or ends there. Hence a
// graph has an Eulerian circuit when every vertex has even degree, and an Eulerian path (not
// closed) when exactly two vertices have odd degree; the number of odd-degree vertices is never
// 1, since the degrees add up to twice the number of edges. And the trace has to be able to
// reach every edge, so the vertices that have edges must all be connected. The two tests are
// enough, which is the classical theorem; the code only counts, it does not build the trace.
//
// Time: O(V + E) -- one depth-first walk for the connectivity test, then one pass over the
// degrees.
// Space: O(V) -- the visited flags, plus the recursion of the walk.
//
// Known limit: the connectivity test uses the recursive walk DFS.dfs, one call deeper per vertex
// along a chain, so a connected piece of about 10,000 vertices in a chain overflows the default
// stack. DFSIterative.dfs is the same walk with an explicit stack.

public final class EulerianPathCircuit {

  // the function returns one of the following values
  // 0 --> if graph is not Eulerian
  // 1 --> if graph has an Eulerian path (Semi-Eulerian)
  // 2 --> if graph has an Euler Circuit (Eulerian)

  public static int isEulerian(Graph g) {

    // check if all non-zero degree vertices are connected
    if (!isConnected(g)) {
      return 0; // CLASH: some edge cannot be reached from the others
    }
    // count vertices with odd degree
    int odd = countOdd(g);

    // if count is more than 2, then graph is not Eulerian
    if (odd > 2) {
      return 0; // CLASH: too many places where a trace would have to start or end
    }
    // if odd count is 2, then semi-eulerian
    // if odd count is 0, then eulerian
    // note that odd count can never be 1 for undirected graph
    return odd == 2 ? 1 : 2; // RETURN
  }

  private static boolean isConnected(Graph g) {
    int size = g.size();
    boolean[] visited = new boolean[size];

    // find a vertex with non-zero degree
    int index = nonZeroDegreeVertex(g);

    // if there are no edges in the graph, return true
    if (index == size) {
      return true;
    }

    // start DFS traversal from a vertex with non-zero degree
    dfs(g, index, visited); // WALK

    // check if all non-zero degree vertices are visited
    return areAllVisited(g, visited);
  }

  // the first vertex with an edge; 0 when no vertex has one (every degree is then 0, so the walk
  // from vertex 0 and the visited test that follow are harmless), g.size() only for an empty graph
  private static int nonZeroDegreeVertex(Graph g) {
    int index = 0;
    for (int v = 0; v < g.size(); ++v) {
      if (g.degree(v) != 0) {
        index = v;
        break;
      }
    }
    return index;
  }

  private static boolean areAllVisited(Graph g, boolean[] visited) {
    for (int v = 0; v < g.size(); ++v) {
      if (!visited[v] && g.degree(v) > 0) {
        return false;
      }
    }
    return true;
  }

  private static int countOdd(Graph g) {
    int odd = 0;
    for (int v = 0; v < g.size(); ++v) {
      if (g.degree(v) % 2 == 1) {
        ++odd; // COUNT: v is a place where a trace starts or ends
      }
    }
    return odd;
  }

  public static void main(String[] args) {
    Graph g1 = new Graph(5);
    g1.addEdge(1, 0);
    g1.addEdge(0, 2);
    g1.addEdge(2, 1);
    g1.addEdge(0, 3);
    g1.addEdge(3, 4);
    test(g1); // Graph has a Euler path

    Graph g2 = new Graph(5);
    g2.addEdge(1, 0);
    g2.addEdge(0, 2);
    g2.addEdge(2, 1);
    g2.addEdge(0, 3);
    g2.addEdge(3, 4);
    g2.addEdge(4, 0);
    test(g2); // Graph has a Euler cycle

    Graph g3 = new Graph(5);
    g3.addEdge(1, 0);
    g3.addEdge(0, 2);
    g3.addEdge(2, 1);
    g3.addEdge(0, 3);
    g3.addEdge(3, 4);
    g3.addEdge(1, 3);
    test(g3); // Graph is not Eulerian

    // Let us create a graph with 3 vertices
    // connected in the form of cycle
    Graph g4 = new Graph(3);
    g4.addEdge(0, 1);
    g4.addEdge(1, 2);
    g4.addEdge(2, 0);
    test(g4); // Graph has a Euler cycle

    // Let us create a graph with all vertices
    // with zero degree
    Graph g5 = new Graph(3);
    test(g5); // Graph has a Euler cycle
  }

  public static void test(Graph g) {
    int res = isEulerian(g);
    if (res == 0) {
      System.out.println("Graph is not Eulerian");
    } else if (res == 1) {
      System.out.println("Graph has a Euler path");
    } else {
      System.out.println("Graph has a Euler cycle");
    }
  }
}
