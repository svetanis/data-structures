package com.svetanis.datastructures.graph.undirected.cycle.eulerian;

import static com.svetanis.datastructures.graph.undirected.traverse.DFS.dfs;
import static com.svetanis.java.base.utils.Bits.isOdd;

import com.svetanis.datastructures.graph.undirected.Graph;

// Eulerian Path is a path in graph that visits every edge exactly once. 
// Eulerian Circuit is an Eulerian Path which starts and ends on the same vertex. 
// A graph is called Eulerian if it has an Eulerian Cycle and called Semi-Eulerian if it has an Eulerian Path. 

// “Is it possible to draw a given graph without lifting pencil from the paper 
// and without tracing any of the edges more than once”.

// Eulerian Cycle
// An undirected graph has Eulerian cycle if following two conditions are true.
// ….a) All vertices with non-zero degree are connected. 
// ….b) All vertices have even degree.

// Eulerian Path
// An undirected graph has Eulerian Path if following two conditions are true.
// ….a) All vertices with non-zero degree are connected. 
// ….b) If zero or two vertices have odd degree and all other vertices have even degree. 
//        Note that only one vertex with odd degree is not possible in an undirected graph 
//        (sum of all degrees is always even in an undirected graph)

public final class EulerianPathCircuit {

  // the function returns one of the following values
  // 0 --> if graph is not Eulerian
  // 1 --> if graph has an Eulerian path (Semi-Eulerian)
  // 2 --> if graph has an Euler Circuit (Eulerian)

  public static int isEulerian(Graph g) {

    // check if all non-zero degree vertices are connected
    if (!isConnected(g)) {
      return 0;
    }
    // count vertices with odd degree
    int odd = countOdd(g);

    // if count is more than 2, then graph is not Eulerian
    if (odd > 2) {
      return 0;
    }
    // if odd count is 2, then semi-eulerian
    // if odd count is 0, then eulerian
    // note that odd count can never be 1 for undirected graph
    return odd == 2 ? 1 : 2;
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
    dfs(g, index, visited);

    // check if all non-zero degree vertices are visited
    return areAllVisited(g, visited);
  }

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
      if (isOdd(g.degree(v))) {
        ++odd;
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
    test(g1);

    Graph g2 = new Graph(5);
    g2.addEdge(1, 0);
    g2.addEdge(0, 2);
    g2.addEdge(2, 1);
    g2.addEdge(0, 3);
    g2.addEdge(3, 4);
    g2.addEdge(4, 0);
    test(g2);

    Graph g3 = new Graph(5);
    g3.addEdge(1, 0);
    g3.addEdge(0, 2);
    g3.addEdge(2, 1);
    g3.addEdge(0, 3);
    g3.addEdge(3, 4);
    g3.addEdge(1, 3);
    test(g3);

    // Let us create a graph with 3 vertices
    // connected in the form of cycle
    Graph g4 = new Graph(3);
    g4.addEdge(0, 1);
    g4.addEdge(1, 2);
    g4.addEdge(2, 0);
    test(g4);

    // Let us create a graph with all veritces
    // with zero degree
    Graph g5 = new Graph(3);
    test(g5);
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
