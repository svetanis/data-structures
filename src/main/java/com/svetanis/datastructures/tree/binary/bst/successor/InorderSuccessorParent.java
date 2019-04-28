package com.svetanis.datastructures.tree.binary.bst.successor;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.parent.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.parent.Nodes.insert;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.parent.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.parent.Nodes.isNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.parent.Nodes.min;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.parent.Node;

// Step. 1 : if right subtree of node is not null,
// then succ lies in right subtree
// Go to right subtree and return
// node with min key value in right subtree

// Step. 2 : if right subtree of node is null,
// then succ is one of the ancestors
// Travel up using the parent pointer
// until you see a node which is
// left child of it's parent.
// The parent of such a node is the succ.

public final class InorderSuccessorParent {
  // Time complexity: O(h), h is height of tree

  public static Node inOrderSuccessor(Node node) {

    if (isNull(node)) {
      return null;
    }

    if (isNull(node.parent) || isNotNull(node.right)) {
      return min(node.right);
    } else {
      Node parent = node.parent;
      while (isNotNull(parent) && parent.left != node) {
        node = parent;
        parent = parent.parent;
      }
      return parent;
    }
  }

  public static void main(String[] args) {
    // inorder successor of 8 is 10,
    // inorder successor of 10 is 12
    // inorder successor of 14 is 20

    Node root = null;
    root = insert(root, 20);
    root = insert(root, 8);
    root = insert(root, 22);
    root = insert(root, 4);
    root = insert(root, 12);
    root = insert(root, 10);
    root = insert(root, 14);

    Node node = root.left.right.right;
    Node succ = inOrderSuccessor(node);

    inOrder(root);
    System.out.println();
    System.out.println("node: " + node + " succ: " + succ);
  }
}
