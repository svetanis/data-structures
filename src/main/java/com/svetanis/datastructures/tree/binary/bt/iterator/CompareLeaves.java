package com.svetanis.datastructures.tree.binary.bt.iterator;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Compare leaf nodes in left to right order

public final class CompareLeaves {

  public static boolean equal(Node root1, Node root2) {
    LeafIterator iter1 = new LeafIterator(root1);
    LeafIterator iter2 = new LeafIterator(root2);
    while (iter1.hasNext() && iter2.hasNext()) {
      Node leaf1 = iter1.next();
      Node leaf2 = iter2.next();
      if (leaf1.data != leaf2.data) {
        return false;
      }
    }
    if (iter1.hasNext() || iter2.hasNext()) {
      return false;
    }
    return true;
  }

  public static void main(String[] args) {
    Node root1 = newNode(1);
    root1.left = newNode(2);
    root1.right = newNode(3);
    root1.left.left = newNode(4);
    root1.left.right = newNode(5);
    root1.right.right = newNode(6);

    Node root2 = newNode(1);
    root2.left = newNode(2);
    root2.left.left = newNode(4);
    root2.left.right = newNode(3);
    root2.left.right.left = newNode(5);
    root2.left.right.right = newNode(6);

    System.out.println(equal(root1, root2));
  }
}
