package com.svetanis.datastructures.graph.undirected.traverse;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Lists.reverse;
import static com.svetanis.java.base.utils.Print.print;
import static java.util.Arrays.fill;

import java.util.List;
import java.util.Queue;

import com.svetanis.datastructures.graph.undirected.Graph;

public final class BidirectionalSearch {

  public static boolean search(Graph g, int src, int dst) {
    
    int n = g.size();

    boolean[] srcVisited = new boolean[n];
    srcVisited[src] = true;

    boolean[] dstVisited = new boolean[n];
    dstVisited[dst] = true;

    int[] srcParents = new int[n];
    fill(srcParents, -1);

    int[] dstParents = new int[n];
    fill(dstParents, -1);

    Queue<Integer> srcQueue = newLinkedList();
    srcQueue.offer(src);

    Queue<Integer> dstQueue = newLinkedList();
    dstQueue.offer(dst);

    int intersect = -1;

    while (!srcQueue.isEmpty() && !dstQueue.isEmpty()) {
      bfs(g, srcQueue, srcVisited, srcParents);
      bfs(g, dstQueue, dstVisited, dstParents);
      intersect = isIntersect(srcVisited, dstVisited);
      if (intersect != -1) {
        System.out.println("path from " + src + " to " + dst);
        System.out.println("intersection at " + intersect);
        printPath(src, dst, intersect, srcParents, dstParents);
        return true;
      }
    }
    return false;
  }

  private static void printPath(int src, int dst, int intersect, 
                            int[] srcParents, int[] dstParents) {
    List<Integer> path = newArrayList();
    path.add(intersect);
    int i = intersect;
    while (i != src) {
      path.add(srcParents[i]);
      i = srcParents[i];
    }
    List<Integer> reversed = reverse(path);
    i = intersect;
    while (i != dst) {
      reversed.add(dstParents[i]);
      i = dstParents[i];
    }
    print(reversed);
  }

  private static int isIntersect(boolean[] src, boolean[] dst) {
    for (int i = 0; i < src.length; i++) {
      if (src[i] && dst[i]) {
        return i;
      }
    }
    return -1;
  }

  private static void bfs(Graph g, Queue<Integer> queue, 
                      boolean[] visited, int[] parents) {
    int v = queue.poll();
    for (int u : g.adj(v)) {
      if (!visited[u]) {
        parents[u] = v;
        visited[u] = true;
        queue.offer(u);
      }
    }
  }

  public static void main(String[] args) {
    Graph graph = new Graph(15);
    graph.addEdge(0, 4);
    graph.addEdge(1, 4);
    graph.addEdge(2, 5);
    graph.addEdge(3, 5);
    graph.addEdge(4, 6);
    graph.addEdge(5, 6);
    graph.addEdge(6, 7);
    graph.addEdge(7, 8);
    graph.addEdge(8, 9);
    graph.addEdge(8, 10);
    graph.addEdge(9, 11);
    graph.addEdge(9, 12);
    graph.addEdge(10, 13);
    graph.addEdge(10, 14);

    search(graph, 0, 14);
  }
}
