package com.svetanis.datastructures.tree.binary.bt.construction;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static java.util.Arrays.asList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 106. Construct Binary Tree from Inorder and Postorder Traversal (List version)
//
// Input: in -- the in-order values, post -- the post-order values of the same binary tree; all
// values are distinct. On LeetCode: 1 <= n <= 3000, -3000 <= value <= 3000.
// Returns: the root of the tree both sequences describe.
//
// The one idea: post-order lists a node after both of its subtrees, so the last value of any
// post-order slice is the root of that slice's tree. In-order lists the left subtree, then the
// root, then the right subtree, so the root's in-order position cuts the in-order slice in two:
// its left part is the left subtree and its right part the right subtree. The size of the left part
// says how many post-order values (right after the slice start) belong to the left subtree; the
// rest, up to the last value, belong to the right subtree. Repeat on each side.
//
// Time: O(n) -- one map lookup per node, and a node is built once.
// Space: O(n) -- the value -> in-order index map, plus the recursion (as deep as the tree is tall).
//
// Siblings: BinaryTreeFromInPostOrder106 is the same method on int[] with a length instead of an
// end index.

public final class BinaryTreeFromInPostOrder {

  public static TreeNode construct(List<Integer> in, List<Integer> post) {
    Map<Integer, Integer> map = indexMap(in); // INDEX: value -> its in-order position
    return construct(in, 0, in.size() - 1, post, 0, post.size() - 1, map);
  }

  private static TreeNode construct(List<Integer> in, int inStart, int inEnd, List<Integer> post, int postStart,
      int postEnd, Map<Integer, Integer> map) {

    if (inStart > inEnd || postStart > postEnd) { // BASE: empty slice, no node
      return null;
    }

    TreeNode node = new TreeNode(post.get(postEnd)); // ROOT: last post-order value of the slice
    int root = map.get(node.val); // SPLIT POINT: root's in-order position
    int left = root - inStart; // size of left subtree

    node.left = construct(in, inStart, root - 1, post, postStart, postStart + left - 1, map); // LEFT: first `left` post values
    node.right = construct(in, root + 1, inEnd, post, postStart + left, postEnd - 1, map); // RIGHT: the rest, minus the root
    return node;
  }

  private static Map<Integer, Integer> indexMap(List<Integer> list) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < list.size(); i++) {
      map.put(list.get(i), i); // RECORD: value -> position
    }
    return map;
  }

  public static void main(String[] args) {
    List<Integer> in = asList(4, 8, 2, 5, 1, 6, 3, 7);
    List<Integer> post = asList(8, 4, 5, 2, 6, 7, 3, 1);
    TreeNode root = construct(in, post);
    inOrder(root); // 4 8 2 5 1 6 3 7
    System.out.println();
  }
}
