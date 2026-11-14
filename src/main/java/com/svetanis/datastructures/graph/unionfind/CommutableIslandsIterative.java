package com.svetanis.datastructures.graph.unionfind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

// Commutable Islands (minimum cost to join every island)
//
// Input: n islands numbered 1..n and a list of bridges, each a list [x, y, w]: a bridge between
// islands x and y that costs w. The islands can all be joined using the given bridges.
// On the original statement: 1 <= n <= 6 * 10^4, 1 <= bridges <= 6 * 10^4, 1 <= w <= 10^3.
// Returns the least total cost of a set of bridges that lets you walk between any two islands.
//
// The one idea: the cheapest set never needs a loop (drop the dearest bridge of a loop and every
// island stays joined), so it is a tree of n - 1 bridges. Look at the bridges from cheapest to
// dearest and build it greedily: a bridge joining two islands that are not yet connected goes
// in; a bridge between two islands that are already connected would only close a loop and is
// skipped. Choosing the cheapest bridge never has to be undone: any cheaper-or-equal way of
// reaching the same two groups of islands would have been looked at first. "Already connected?"
// is the union-find question: every island points to a root of its group, and two islands are
// connected when their roots are the same.
//
// Time: O(m log m) -- the sort; the union-find steps add at most O(m log n) after it (path
// compression alone, no union by size).
// Space: O(n + m) -- the parent array and the bridge list.
//
// Siblings: CommutableIslands is this file with the recursive find you would write at a
// whiteboard. ConnectCitiesMinCost and its two variants solve the same problem on int[][] (1135),
// and MinCostToConnectAllPointsKruskal uses the DisjointSet class.

public final class CommutableIslandsIterative {

  public static int minCost(int n, ArrayList<ArrayList<Integer>> lists) {
    List<Node> nodes = nodes(lists);
    Collections.sort(nodes, new WeightComparator()); // SORT: cheapest bridge first
    int[] parent = new int[n]; // CARDS: parent[i] -- the island above i in its group; -1 for a root
    Arrays.fill(parent, -1);
    int cost = 0;
    for (Node node : nodes) {
      int u = find(parent, node.x - 1);
      int v = find(parent, node.y - 1);
      if (u != v) { // TAKE: not connected yet, so this bridge is needed
        union(parent, u, v);
        cost += node.w;
      }
    }
    return cost;
  }

  // iterative, because a long chain of bridges built in cost order makes a tree as deep as the
  // number of islands, and a recursive find would run out of call stack on it
  private static int find(int[] parent, int p) {
    int root = p;
    while (parent[root] != -1) { // WALK: up to the root
      root = parent[root];
    }
    while (p != root) { // COMPRESS: point everything on the route straight at the root
      int next = parent[p];
      parent[p] = root;
      p = next;
    }
    return root;
  }

  private static void union(int[] parent, int p, int q) {
    int rootP = find(parent, p);
    int rootQ = find(parent, q);
    parent[rootP] = rootQ; // JOIN: one root goes under the other
  }

  private static List<Node> nodes(ArrayList<ArrayList<Integer>> lists) {
    List<Node> nodes = new ArrayList<>();
    for (List<Integer> list : lists) {
      Iterator<Integer> iter = list.iterator();
      Node node = new Node(iter.next(), iter.next(), iter.next());
      nodes.add(node);
    }
    return nodes;
  }

  public static void main(String[] args) {
    ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
    lists.add(new ArrayList<>(Arrays.asList(1, 2, 1)));
    lists.add(new ArrayList<>(Arrays.asList(2, 3, 4)));
    lists.add(new ArrayList<>(Arrays.asList(1, 4, 3)));
    lists.add(new ArrayList<>(Arrays.asList(4, 3, 2)));
    lists.add(new ArrayList<>(Arrays.asList(1, 3, 10)));
    System.out.println(minCost(4, lists)); // 6
  }

  private static class WeightComparator implements Comparator<Node> {

    @Override
    public int compare(Node node1, Node node2) {
      return Integer.compare(node1.w, node2.w);
    }
  }

  private static class Node {
    private int x;
    private int y;
    private int w;

    public Node(int x, int y, int w) {
      this.x = x;
      this.y = y;
      this.w = w;
    }

    @Override
    public String toString() {
      return x + ":" + y + ":" + w;
    }
  }

}
