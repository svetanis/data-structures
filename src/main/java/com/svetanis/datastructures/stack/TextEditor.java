package com.svetanis.datastructures.stack;

// 2296. Design a Text Editor

public final class TextEditor {

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
			right.append(left.charAt(left.length() - 1));
			left.deleteCharAt(left.length() - 1);
		}
		return left.substring(Math.max(left.length() - 10, 0));
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
