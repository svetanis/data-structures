package com.svetanis.datastructures.graph.directed.scc;

import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.datastructures.graph.directed.scc.StronglyConnected.isVisited;
import static com.svetanis.datastructures.graph.directed.scc.TransposeGraph.transpose;
import static java.util.Arrays.fill;

import java.util.Queue;

import com.svetanis.datastructures.graph.directed.Graph;

public final class SccKosarajuBfs {

  // Time complexity: O(V + E)
  public static boolean scc(Graph g) {

    int size = g.size();
    boolean[] visited = new boolean[size];

    bfs(g, 0, visited);

    boolean isVisited = isVisited(visited);

    // create a reversed graph
    Graph reversed = transpose(g);

    // mark all the vertices
    // as not visited (for second DFS)
    fill(visited, false);

    // now process all vertices
    // in order defined by Stack
    bfs(reversed, 0, visited);

    return isVisited && isVisited(visited);
  }

  private static void bfs(Graph g, int src, boolean[] visited) {
    // Time Complexity: O(V + E)

    visited[src] = true;
    Queue<Integer> queue = newLinkedList();
    queue.offer(src);
    while (!queue.isEmpty()) {
      int v = queue.poll();
      for (int u : g.adj(v)) {
        if (!visited[u]) {
          visited[u] = true;
          queue.offer(u);
        }
      }
    }
  }

  public static void main(String[] args) {
    Graph g1 = new Graph(5);
    g1.addEdge(0, 1);
    g1.addEdge(1, 2);
    g1.addEdge(2, 3);
    g1.addEdge(3, 0);
    g1.addEdge(2, 4);
    g1.addEdge(4, 2);
    System.out.println(scc(g1));

    Graph g2 = new Graph(4);
    g2.addEdge(0, 1);
    g2.addEdge(1, 2);
    g2.addEdge(2, 3);
    System.out.println(scc(g2));
  }
}
