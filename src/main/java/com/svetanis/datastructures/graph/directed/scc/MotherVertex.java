package com.svetanis.datastructures.graph.directed.scc;

import static com.svetanis.datastructures.graph.directed.traverse.DFS.dfs;
import static java.util.Arrays.fill;

import com.svetanis.datastructures.graph.directed.Graph;

// A mother vertex in a graph G = (V, E) is a vertex v such that all other vertices in G can be reached by a path from v.
// If there exist mother vertex (or vertices), then one of the mother vertices is the last finished vertex in DFS. 
// (Or a mother vertex has the maximum finish time in DFS traversal).

// A vertex is said to be finished in DFS if a recursive call for its DFS is over, i.e., all descendants of the vertex have been visited. 

public final class MotherVertex {

  public static int motherVertex(Graph g) {
    // Time Complexity: O(V + E)

    int size = g.size();
    boolean[] visited = new boolean[size];

    // mother vertex
    int vertex = motherVertex(g, visited);

    // check if v is a motherVertex
    fill(visited, false);
    dfs(g, vertex, visited);

    for (int i = 0; i < size; i++) {
      if (!visited[i]) {
        return -1;
      }
    }
    return vertex;
  }

  private static int motherVertex(Graph g, boolean[] visited) {
    int size = g.size();
    int vertex = 0;
    for (int v = 0; v < size; ++v) {
      if (!visited[v]) {
        dfs(g, v, visited);
        vertex = v;
      }
    }
    return vertex;
  }

  public static void main(String[] args) {
    Graph g = new Graph(7);
    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(1, 3);
    g.addEdge(4, 1);
    g.addEdge(6, 4);
    g.addEdge(5, 6);
    g.addEdge(5, 2);
    g.addEdge(6, 0);
    System.out.println(motherVertex(g));
  }
}
