package com.svetanis.datastructures.tree.binary.bt.compare;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 951. Flip Equivalent Binary Trees (the same question; here values may repeat)
//
// Input: the roots of two binary trees; either may be empty. Two trees are isomorphic when one can
// be turned into the other by swapping the left and right children of some nodes. Returns whether
// src and dst are isomorphic. On LeetCode: 0 <= nodes <= 100 in each tree, every value unique,
// 0 <= Node.val <= 99.
//
// The one idea: at a pair of nodes the values must be equal, and then the children match either
// straight across (left with left, right with right) or crossed over (left with right, right with
// left); it is enough for one of the two to hold. A swap anywhere below is found by the same
// question one level down, so the answer for a pair is built from the answers for its children.
//
// Time: O(n * m) in the worst case -- a pair (a node of src, a node of dst) is looked at at most
// once, n and m the tree sizes. When the two values under a node differ, only one of the two
// orientations gets past the value check and the work is linear; when many values are equal, as in
// two perfect trees holding one value everywhere, both orientations are explored at every node and
// the work is quadratic.
// Space: O(h) -- the recursion stack. h is lg n only on a balanced tree; a skewed one makes it n.

public final class IsomorphicRecursive {

  public static boolean isIsomorphic(TreeNode src, TreeNode dst) {
    // base cases
    if (isNull(src) && isNull(dst)) {
      return true; // BASE: both empty
    }

    if (isNull(src) || isNull(dst)) {
      return false; // BASE: one empty, one not
    }

    if (src.val != dst.val) {
      return false; // COMPARE: the roots must match before the children matter
    }

    // there are two possible cases for source and target to be isomorphic
    // 1. the subtrees rooted at these nodes have Not been 'flipped'
    // both of these subtrees have to be isomorphic, hence the &&
    // 2. the subtrees rooted at these nodes have been 'flipped'

    boolean nonFlipped = isIsomorphic(src.left, dst.left)
                      && isIsomorphic(src.right, dst.right); // STRAIGHT: left with left, right with right

    boolean flipped = isIsomorphic(src.left, dst.right)
                   && isIsomorphic(src.right, dst.left); // CROSSED: left with right, right with left

    return flipped || nonFlipped; // RETURN: either orientation will do
  }

  public static void main(String[] args) {
    TreeNode root1 = newNode(1);
    root1.left = newNode(2);
    root1.right = newNode(3);
    root1.left.left = newNode(4);
    root1.left.right = newNode(5);
    root1.right.left = newNode(6);
    root1.left.right.left = newNode(7);
    root1.left.right.right = newNode(8);

    TreeNode root2 = newNode(1);
    root2.left = newNode(3);
    root2.right = newNode(2);
    root2.right.left = newNode(4);
    root2.right.right = newNode(5);
    root2.left.right = newNode(6);
    root2.right.right.left = newNode(8);
    root2.right.right.right = newNode(7);

    System.out.println(isIsomorphic(root1, root2)); // true
  }
}
