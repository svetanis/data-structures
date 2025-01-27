package com.svetanis.datastructures.tree.binary.bt.traversal;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.java.base.utils.Print.print;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 145. Binary Tree Postorder Traversal

// Similar to preorder. Two differences :
//   1) Where pre-order prints, post-order puts the value into another stack to reverse the order
//   2) Order of left and right nodes is swapped

public final class PostOrderTwoStacks {

	public static List<Integer> postOrder(Node root) {
		if (root == null) {
			return new ArrayList<>();
		}
		Deque<Node> stack = new ArrayDeque<>();
		Deque<Node> out = new ArrayDeque<>();
		stack.push(root);
		while (!stack.isEmpty()) {
			Node node = stack.pop();
			out.push(node);

			if (node.left != null) {
				stack.push(node.left);
			}
			if (node.right != null) {
				stack.push(node.right);
			}
		}
		return toList(out);
	}

	public static List<Integer> toList(Deque<Node> queue) {
		List<Integer> list = new ArrayList<>();
		while (!queue.isEmpty()) {
			list.add(queue.poll().data);
		}
		return list;
	}

	public static void main(String[] args) {
		Node root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		root.left.left = newNode(4);
		root.left.right = newNode(5);
		root.right.right = newNode(6);
		print(postOrder(root));
	}
}
