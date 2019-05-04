package com.svetanis.datastructures.graph.unionfind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public final class CommutableIslands {

  public static int minCost(int n, ArrayList<ArrayList<Integer>> lists) {
    List<Node> nodes = nodes(lists);
    Collections.sort(nodes, new WeightComparator());
    int[] parent = new int[n];
    Arrays.fill(parent, -1);
    int cost = 0;
    for (Node node : nodes) {
      int u = find(parent, node.x - 1);
      int v = find(parent, node.y - 1);
      if (u != v) {
        union(parent, u, v);
        cost += node.w;
      }
    }
    return cost;
  }

  private static int find(int[] parent, int p) {
    if (parent[p] == -1) {
      return p;
    }
    return find(parent, parent[p]);
  }

  private static void union(int[] parent, int p, int q) {
    int rootP = find(parent, p);
    int rootQ = find(parent, q);
    parent[rootP] = rootQ;
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
    System.out.println(minCost(4, lists));
  }

  private static class WeightComparator implements Comparator<Node> {

    @Override
    public int compare(Node node1, Node node2) {
      return node1.w - node2.w;
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
