package com.svetanis.datastructures.tree.binary.bt.compare;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class IsomorphicRecursive {

  public static boolean isIsomorphic(Node src, Node dst) {
    // Time complexity: O(n + m)
    // n, m - number of nodes in given trees

    // base cases
    if (isNull(src) && isNull(dst)) {
      return true;
    }

    if (isNull(src) || isNull(dst)) {
      return false;
    }

    if (src.data != dst.data) {
      return false;
    }

    // there are two possible cases for source and target to be isomorphic
    // 1. the subtrees rooted at these nodes have Not been 'flipped'
    // both of these subtrees have to be isomorphic, hence the &&
    // 2. the subtrees rooted at these nodes have been 'flipped'

    boolean nonFlipped = isIsomorphic(src.left, dst.left) 
                      && isIsomorphic(src.right, dst.right);

    boolean flipped = isIsomorphic(src.left, dst.right) 
                   && isIsomorphic(src.right, dst.left);

    return flipped || nonFlipped;
  }

  public static void main(String[] args) {
    Node root1 = newNode(1);
    root1.left = newNode(2);
    root1.right = newNode(3);
    root1.left.left = newNode(4);
    root1.left.right = newNode(5);
    root1.right.left = newNode(6);
    root1.left.right.left = newNode(7);
    root1.left.right.right = newNode(8);

    Node root2 = newNode(1);
    root2.left = newNode(3);
    root2.right = newNode(2);
    root2.right.left = newNode(4);
    root2.right.right = newNode(5);
    root2.left.right = newNode(6);
    root2.right.right.left = newNode(8);
    root2.right.right.right = newNode(7);

    System.out.println(isIsomorphic(root1, root2));
  }
}
