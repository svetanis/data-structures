package com.svetanis.datastructures.tree.trie;

// 616. Add Bold Tag in String

public final class AddBoldTagSubmit {

	public static String addBoldTag(String s, String[] words) {
		int n = words.length;
		if (n == 0) {
			return s;
		}
		boolean[] buffer = buffer(s, words);
		StringBuilder sb = new StringBuilder();
		boolean isAppend = false;
		for (int i = 0; i < buffer.length; i++) {
			if (buffer[i] && !isAppend) {
				sb.append("<b>");
				isAppend = true;
			}
			if (!buffer[i] && isAppend) {
				sb.append("</b>");
				isAppend = false;
			}
			sb.append(s.charAt(i));
		}
		if (isAppend) {
			sb.append("</b>");
		}
		return sb.toString();
	}

	private static boolean[] buffer(String s, String[] words) {
		int n = words.length;
		int m = s.length();
		boolean[] buffer = new boolean[m];
		for (int i = 0; i < n; i++) {
			String word = words[i];
			int index = s.indexOf(word);
			while (index != -1) {
				for (int j = index; j < index + word.length(); j++) {
					buffer[j] = true;
				}
				index = s.indexOf(word, index + 1);
			}
		}
		return buffer;
	}

	public static void main(String[] args) {
		String[] words = { "abc", "123" };
		System.out.println(addBoldTag("abcxyz123", words));
	}
}
