package com.svetanis.datastructures.tree.binary.bt.serialize;

import java.util.ArrayList;
import java.util.List;

// 331. Verify Preorder Serialization of a Binary Tree

public final class ValidSerialization {
	// Time Complexity: O(n)
	
	private static final String DEL = "#";

	public static boolean isValid(String s) {
		int count = 1;
		String[] nodes = s.split(",");
		for (String node : nodes) {
			count--;
			if (count < 0) {
				return false;
			}
			if (!node.equals(DEL)) {
				count += 2;
			}
		}
		return count == 0;
	}

	public static boolean valid(String s) {
		List<String> list = new ArrayList<>();
		String[] nodes = s.split(",");
		for (String node : nodes) {
			list.add(node);
			while (list.size() >= 3 
					&& list.get(list.size() - 1).equals(DEL) 
					&& list.get(list.size() - 2).equals(DEL)
					&& !list.get(list.size() - 3).equals(DEL)) {
				list.remove(list.size() - 1);
				list.remove(list.size() - 1);
				list.remove(list.size() - 1);
				list.add(DEL);
			}
		}
		return list.size() == 1 && list.get(0).equals(DEL);
	}

	public static void main(String[] args) {
		System.out.println(valid("9,3,4,#,#,1,#,#,2,#,6,#,#")); // true
		System.out.println(valid("1,#")); // false
		System.out.println(valid("9,#,#,1")); // false
	}
}
