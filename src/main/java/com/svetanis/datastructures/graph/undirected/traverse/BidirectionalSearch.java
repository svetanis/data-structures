package com.svetanis.datastructures.graph.undirected.traverse;

import static java.util.Arrays.fill;

import com.svetanis.datastructures.graph.undirected.Graph;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

// Is there a path between two vertices? -- bidirectional search.
//
// Input: a Graph g on the vertices 0 .. g.size() - 1, a source vertex src and a destination
// vertex dst, both vertices of g. No statement fixes the sizes; this is not a LeetCode problem
// and has not been run against a judge. Returns true when dst is reachable from src. When it is,
// also prints "path from <src> to <dst>", "intersection at <v>" and the path as a list of
// vertices; the path is valid, and the code does not claim it is the shortest.
//
// The one idea: instead of one search that fans out from src until it meets dst, run two, one
// from each end, and stop when they meet; each only has to cover half the distance. Each side
// has its own visited flags, its own queue, and parents[v], the vertex v was reached from on
// that side. Each round expands one vertex from each queue, then looks for a vertex flagged by
// both sides -- the meeting point. The path is src back to the meeting point along the src-side
// parents, reversed, then the meeting point on to dst along the dst-side parents. If either
// queue runs dry first, one side has covered its whole component without meeting the other, so
// there is no path.
//
// Time: O(V^2 + E) -- the sides together read each adjacency entry once, but each round scans all
// V flags for a meeting point, and there are up to V rounds.
// Space: O(V) -- two sets of flags, two parent arrays and two queues.

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

    Queue<Integer> srcQueue = new ArrayDeque<>();
    srcQueue.offer(src);

    Queue<Integer> dstQueue = new ArrayDeque<>();
    dstQueue.offer(dst);

    int intersect = -1;

    while (!srcQueue.isEmpty() && !dstQueue.isEmpty()) {
      bfs(g, srcQueue, srcVisited, srcParents); // EXPAND one vertex from the src side
      bfs(g, dstQueue, dstVisited, dstParents); // EXPAND one vertex from the dst side
      intersect = isIntersect(srcVisited, dstVisited); // MEET: a vertex both sides reached
      if (intersect != -1) {
        System.out.println("path from " + src + " to " + dst);
        System.out.println("intersection at " + intersect);
        printPath(src, dst, intersect, srcParents, dstParents);
        return true;
      }
    }
    return false;
  }

  private static void printPath(int src, int dst, int intersect, int[] srcParents,
      int[] dstParents) {
    List<Integer> path = new ArrayList<>();
    path.add(intersect);
    int i = intersect;
    while (i != src) {
      path.add(srcParents[i]); // BACK from the meeting point to src
      i = srcParents[i];
    }
    Collections.reverse(path); // now src ... meeting point
    i = intersect;
    while (i != dst) {
      path.add(dstParents[i]); // ON from the meeting point to dst
      i = dstParents[i];
    }
    for (int v : path) {
      System.out.print(v);
      System.out.print(' ');
    }
    System.out.println();
  }

  private static int isIntersect(boolean[] src, boolean[] dst) {
    for (int i = 0; i < src.length; i++) {
      if (src[i] && dst[i]) {
        return i;
      }
    }
    return -1;
  }

  private static void bfs(Graph g, Queue<Integer> queue, boolean[] visited, int[] parents) {
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

    // prints "path from 0 to 14", "intersection at 7" and "0 4 6 7 8 10 14 "
    search(graph, 0, 14);
  }
}
