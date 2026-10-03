package com.svetanis.datastructures.stack.design;

// 2296. Design a Text Editor
//
// An editor with a cursor. addText types text at the cursor; deleteText(k) erases up to k
// characters left of the cursor and returns how many it erased; cursorLeft(k) and cursorRight(k)
// move the cursor up to k places and return up to 10 characters left of its new position.
//
// The text is split at the cursor into two builders used as stacks of characters. 'left' holds
// the text before the cursor in reading order, so its end touches the cursor. 'right' holds the
// text after the cursor reversed, so its end touches the cursor too. Every operation works at
// those two ends: moving the cursor one place moves one character from one end to the other.

public final class TextEditor {
	// Time Complexity: addText O(length of text); deleteText O(1), setLength only shortens;
	//   cursorLeft, cursorRight O(min(k, n)), one character per step, taken from the end
	// Space Complexity: O(n) for the n characters in the text

	private StringBuilder left;
	private StringBuilder right;

	public TextEditor() {
		this.left = new StringBuilder();
		this.right = new StringBuilder();
	}

	public void addText(String text) {
		left.append(text);
	}

	public int deleteText(int k) {
		int min = Math.min(left.length(), k);
		left.setLength(left.length() - min);
		return min;
	}

	public String cursorLeft(int k) {
		int min = Math.min(k, left.length());
		for (int i = 0; i < min; i++) {
			right.append(left.charAt(left.length() - 1)); // right's end is the cursor
			left.deleteCharAt(left.length() - 1); // the last index, so nothing shifts
		}
		return left.substring(Math.max(left.length() - 10, 0)); // at most the last 10
	}

	public String cursorRight(int k) {
		int min = Math.min(k, right.length());
		for (int i = 0; i < min; i++) {
			left.append(right.charAt(right.length() - 1));
			right.deleteCharAt(right.length() - 1);
		}
		return left.substring(Math.max(left.length() - 10, 0));
	}

	public static void main(String[] args) {
		TextEditor te = new TextEditor();
		te.addText("leetcode");
		System.out.println(te.deleteText(4)); // 4
		te.addText("practice");
		System.out.println(te.cursorRight(3)); // etpractice
		System.out.println(te.cursorLeft(8)); // leet
		System.out.println(te.deleteText(10)); // 4
		System.out.println(te.cursorLeft(2)); // ""
		System.out.println(te.cursorRight(6)); // practi
	}
}
