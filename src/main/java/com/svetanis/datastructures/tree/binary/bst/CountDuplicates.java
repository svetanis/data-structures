package com.svetanis.datastructures.tree.binary.bst;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.insert;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class CountDuplicates {

  public static int duplicates(Node root) {
    return duplicates(root, root);
  }

  private static int duplicates(Node root, Node prev) {
    int count = 0;
    if (isNull(root)) {
      return count;
    }
    count += duplicates(root.left, prev);
    if (root != prev && root.data == prev.data) {
      count++;
    }
    count += duplicates(root.right, root);
    return count;
  }

  public static void main(String[] args) {
    Node root = newNode(5);
    insert(root, 5);
    insert(root, 3);
    insert(root, 2);
    insert(root, 2);
    insert(root, 4);
    insert(root, 4);
    insert(root, 6);
    insert(root, 6);

    inOrder(root);
    System.out.println();
    System.out.println(duplicates(root));
  }
}