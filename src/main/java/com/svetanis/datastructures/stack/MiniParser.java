package com.svetanis.datastructures.stack;

import java.util.ArrayDeque;
import java.util.Deque;

// 385. Mini Parser

public final class MiniParser {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public NestedInteger deserialize(String s) {
		if (s.charAt(0) != '[') {
			return new NestedInteger(Integer.parseInt(s));
		}
		int num = 0;
		boolean negative = false;
		Deque<NestedInteger> dq = new ArrayDeque<>();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (Character.isDigit(c)) {
				num = num * 10 + (c - '0');
			} else if (c == '-') {
				negative = true;
			} else if (c == '[') {
				dq.push(new NestedInteger());
			} else if (c == ',' || c == ']') {
				if (Character.isDigit(s.charAt(i - 1))) {
					num = negative ? -num : num;
					dq.peek().add(new NestedInteger(num));
				}
				num = 0;
				negative = false;
				if (c == ']' && dq.size() > 1) {
					NestedInteger top = dq.pop();
					dq.peek().add(top);
				}
			}
		}
		return dq.peek();
	}

	public static void main(String[] args) {
		MiniParser mp = new MiniParser();
		mp.deserialize("324");
		mp.deserialize("[123,[456,[789]]]");
	}
}
