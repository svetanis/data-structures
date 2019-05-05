package com.svetanis.datastructures.graph.directed.scc;

import static com.svetanis.datastructures.graph.directed.scc.TransposeGraph.transpose;
import static com.svetanis.datastructures.graph.directed.traverse.DFS.dfs;
import static java.util.Arrays.fill;

import com.svetanis.datastructures.graph.directed.Graph;

// A directed graph is strongly connected if there is a path between any two pair of vertices. 

// Kosaraju-based Algorithm
public final class StronglyConnected {

  // Time Complexity: O(V + E)
  public static boolean isStronglyConnected(Graph g) {

    int size = g.size();
    // Step 1: mark all the vertices
    // as not visited (for first DFS)
    boolean[] visited = new boolean[size];

    // Step 2: do DFS traversal
    // starting from first vertex
    dfs(g, 0, visited);

    // if DFS traversal doesn't visit
    // all vertices, then return false
    if (!isVisited(visited)) {
      return false;
    }

    // Step 3: Create a reversed graph
    Graph reversed = transpose(g);

    // Step 4: Mark all the vertices
    // as not visited (for second DFS);
    fill(visited, false);

    // Step 5: Do DFS for reversed graph
    // starting from first vertex.
    // Starting vertex must be same
    // starting point of first DFS
    dfs(reversed, 0, visited);

    // If all vertices are not visited
    // in second DFS, then return false
    return isVisited(visited);
  }

  public static boolean isVisited(boolean[] visited) {
    for (int i = 0; i < visited.length; ++i) {
      if (!visited[i]) {
        return false;
      }
    }
    return true;
  }

  public static void main(String[] args) {
    Graph g = new Graph(5);
    g.addEdge(0, 1);
    g.addEdge(1, 2);
    g.addEdge(2, 3);
    g.addEdge(3, 0);
    g.addEdge(2, 4);
    g.addEdge(4, 2);
    System.out.println(isStronglyConnected(g));

    Graph g2 = new Graph(4);
    g2.addEdge(0, 1);
    g2.addEdge(1, 2);
    g2.addEdge(2, 3);
    System.out.println(isStronglyConnected(g2));
  }
}
