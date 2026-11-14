package com.svetanis.datastructures.graph.undirected.traverse;

import com.svetanis.datastructures.graph.undirected.Graph;
import java.util.ArrayDeque;
import java.util.Queue;

// K-cores of an undirected graph.
//
// Input: a simple Graph g (no parallel edges, no self-loops) and an integer k. No statement
// fixes the sizes; this is not a LeetCode problem and has not been run against a judge. The
// k-core is what is left after every vertex of degree less than k has been removed, repeatedly:
// taking a vertex out lowers its neighbours' degrees, which may push them under k too. Prints,
// for each vertex of the k-core in vertex order, the vertex and then "-> " and each of its
// neighbours that is in the core (the neighbours follow one another with no space between).
// Prints nothing when the core is empty.
//
// The one idea: degrees[v] holds the number of neighbours v still has in the graph. Put every
// vertex whose degree is under k in a queue, marked removed. Take a vertex from the queue and
// lower the degree of each neighbour not yet removed; a neighbour that drops under k is removed
// too and queued. When the queue is empty, the vertices not removed each still have k or more
// neighbours among themselves, and removing a vertex was forced (it can never have k neighbours
// in a core once it has fewer), so they are the k-core.
//
// Time: O(V + E) -- each vertex is queued at most once and each adjacency entry is read at most
// once in the removal, then once more in the printing.
// Space: O(V) -- the degrees, the removed flags and the queue.

public final class KCores {

  public static void kCores(Graph g, int k) {
    int size = g.size();
    boolean[] removed = new boolean[size];
    int[] degrees = new int[size];
    Queue<Integer> queue = new ArrayDeque<>();
    for (int v = 0; v < size; v++) {
      degrees[v] = g.adj(v).size();
      if (degrees[v] < k) {
        removed[v] = true; // REMOVE: v can never have k neighbours
        queue.offer(v);
      }
    }

    // taking v out of the graph lowers the degree of each neighbour still in it
    while (!queue.isEmpty()) {
      int v = queue.poll();
      for (int u : g.adj(v)) {
        if (!removed[u]) {
          degrees[u]--; // LOWER: v is gone
          if (degrees[u] < k) {
            removed[u] = true; // REMOVE: u fell under k
            queue.offer(u);
          }
        }
      }
    }

    // print K-cores
    for (int v = 0; v < size; v++) {
      if (degrees[v] >= k) {
        System.out.print(v + " ");
        for (int u : g.adj(v)) {
          if (degrees[u] >= k) {
            System.out.print("-> " + u);
          }
        }
        System.out.println();
      }
    }
  }

  public static void main(String[] args) {
    int k = 3;
    Graph g = new Graph(9);
    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(1, 2);
    g.addEdge(1, 5);
    g.addEdge(2, 3);
    g.addEdge(2, 4);
    g.addEdge(2, 5);
    g.addEdge(2, 6);
    g.addEdge(3, 4);
    g.addEdge(3, 6);
    g.addEdge(3, 7);
    g.addEdge(4, 6);
    g.addEdge(4, 7);
    g.addEdge(5, 6);
    g.addEdge(5, 8);
    g.addEdge(6, 7);
    g.addEdge(6, 8);
    // the 3-core is 2, 3, 4, 6, 7; prints
    // "2 -> 3-> 4-> 6", "3 -> 2-> 4-> 6-> 7", "4 -> 2-> 3-> 6-> 7", "6 -> 2-> 3-> 4-> 7",
    // "7 -> 3-> 4-> 6"
    kCores(g, k);

    // a triangle 0-1-2 with a tail 3 hanging off vertex 2: the 2-core is the triangle
    Graph tail = new Graph(4);
    tail.addEdge(0, 1);
    tail.addEdge(1, 2);
    tail.addEdge(2, 0);
    tail.addEdge(2, 3);
    // prints "0 -> 1-> 2", "1 -> 0-> 2", "2 -> 1-> 0"
    kCores(tail, 2);
  }
}
