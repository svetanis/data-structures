package com.svetanis.datastructures.tree.trie.shortestuniqueprefix;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

// Shortest unique prefix for every word, with an explicit stack.
// For each word, the shortest prefix that no other word in the list starts with.
//   {zebra, dog, duck, dove}                   -> z, dog, du, dov
//   {geeksgeeks, geeksquiz, geeksforgeeks}     -> geeksg, geeksq, geeksf
// Given: no word is a prefix of another (so no duplicates either). A word
// that is -- dog beside dogs -- has no unique prefix, and gets no answer.
// Answers come out in trie order, not input order: the walk visits
// children in HashMap order, so {zebra, dog, duck, dove} -> du, dov, dog, z.
//
// The one idea: the same as ShortestUniquePrefixesRecursiveBacktrack -- put every word in a trie
// where each node counts the words that pass through it (freq), and walk the whole trie depth
// first with the letters of the path in a buffer; a node with freq 1 ends its branch and the
// buffer at that moment is a shortest unique prefix. Here the walk is a stack of frames, each
// holding a node, its depth and the letter on the edge into it. When a frame is popped the buffer
// is cut back to the frame's depth minus one and the frame's letter is added: that cut is the
// undo of the recursive version, so nothing is ever removed one letter at a time. The children
// of a node are pushed in reverse, so they come off the stack in the order the recursive version
// visits them and both files return the same list in the same order.
//
// Time: O(S) -- S = total letters in the words: each trie node is pushed and popped once
// Space: O(S) -- the trie, plus the stack and the buffer
//
// Siblings: ShortestUniquePrefixesRecursiveBacktrack is the recursive version; a word of roughly
// 10,000 letters overflows its stack, which this file does not.

public final class ShortestUniquePrefixesRecursiveBacktrackIterative {

  public static List<String> sup(List<String> words) {
    List<String> list = new ArrayList<>();
    Node root = Trie.build(words).getRoot(); // BUILD: every word, counting on each node
    Deque<Frame> stack = new ArrayDeque<>();
    stack.push(new Frame(root, 0, '0'));
    StringBuilder path = new StringBuilder(); // the letters on the path to the frame being visited
    while (!stack.isEmpty()) {
      Frame frame = stack.pop(); // VISIT the next node
      if (frame.depth() > 0) {
        path.setLength(frame.depth() - 1); // UNDO: drop the letters of branches already finished
        path.append(frame.letter()); // PUSH this frame's letter
      }
      if (frame.node().freq == 1) {
        list.add(path.toString()); // RECORD: the path to here is only one word's prefix
      } else {
        pushChildren(frame, stack);
      }
    }
    return Collections.unmodifiableList(list);
  }

  private static void pushChildren(Frame frame, Deque<Frame> stack) {
    List<Character> letters = new ArrayList<>(frame.node().children.keySet());
    for (int i = letters.size() - 1; i >= 0; i--) { // REVERSE: the first child must come off first
      char c = letters.get(i);
      stack.push(new Frame(frame.node().children.get(c), frame.depth() + 1, c)); // DESCEND
    }
  }

  public static void main(String[] args) {
    List<String> list = Arrays.asList("zebra", "dog", "duck", "dove");
    List<String> list2 = Arrays.asList("geeksgeeks", "geeksquiz", "geeksforgeeks");
    System.out.println(sup(list)); // [du, dov, dog, z]
    System.out.println(sup(list2)); // [geeksq, geeksf, geeksg]

    // letters repeat on the path
    List<String> repeats = Arrays.asList("aba", "abb", "abc");
    System.out.println(sup(repeats)); // [aba, abb, abc]
  }

  private record Frame(Node node, int depth, char letter) {
  }
}
