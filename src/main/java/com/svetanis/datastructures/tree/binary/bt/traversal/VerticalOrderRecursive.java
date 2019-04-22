package com.svetanis.datastructures.tree.binary.bt.traversal;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Multimaps.newMultimap;
import static com.svetanis.java.base.utils.Print.print;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class VerticalOrderRecursive {

  public static ImmutableMultimap<Integer, Integer> verticalOrder(Node root) {
    // Time Complexity: O(n)

    Multimap<Integer, Integer> mm = LinkedHashMultimap.create();
    verticalOrder(root, 0, mm);
    return newMultimap(mm);
  }

  private static void verticalOrder(Node node, int dist, Multimap<Integer, Integer> mm) {
    if (isNull(node)) {
      return;
    }
    mm.put(dist, node.data);
    verticalOrder(node.left, dist - 1, mm);
    verticalOrder(node.right, dist + 1, mm);
  }

  public static void main(String[] args) {
    Node root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.left = newNode(6);
    root.right.right = newNode(7);
    root.right.left.right = newNode(8);
    root.right.right.right = newNode(9);
    root.right.right.left = newNode(10);
    root.right.right.left.right = newNode(11);
    root.right.right.left.right.right = newNode(12);
    print(verticalOrder(root));
  }
  
      // -2: [4]
      // -1: [2]
      //  0: [1, 5, 6]
      //  1: [3, 8, 10]
      //  2: [7, 11]
      //  3: [9, 12]
}
