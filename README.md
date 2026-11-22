# Data structures — LeetCode solutions in Java

Solutions grouped by **the data structure that solves them**, not by difficulty. Many problems are
solved more than once, and each file's header says what its version does differently from its
siblings.

- **[PROBLEMS.md](PROBLEMS.md)** — every LeetCode problem in this repo by number, with a link to
  each file. Generated, so it is always current.

Techniques that work on plain arrays and strings — dynamic programming, binary search, sliding
window, two pointers, backtracking — are in the sibling repo, `algorithms`.

## Folders

All under [`src/main/java/com/svetanis/datastructures/`](src/main/java/com/svetanis/datastructures).

| folder | what is in it |
|---|---|
| [array](src/main/java/com/svetanis/datastructures/array) | array problems that need no other structure: rotation, products, ranges, iterators |
| [graph](src/main/java/com/svetanis/datastructures/graph) | breadth- and depth-first search, islands and flood fill, directed graphs and topological sort, shortest paths, minimum spanning trees, union-find, cloning |
| [hashmap](src/main/java/com/svetanis/datastructures/hashmap) | counting and lookup with a hash map; LRU caches and other designs built on one |
| [heap](src/main/java/com/svetanis/datastructures/heap) | heaps built by hand, and problems a heap solves: top k words, connecting ropes, a news feed |
| [linkedlist](src/main/java/com/svetanis/datastructures/linkedlist) | singly and doubly linked lists: reverse, merge, find cycles, remove, add |
| [queue](src/main/java/com/svetanis/datastructures/queue) | queues built by hand, circular queues and deques, a queue from stacks, queue simulations |
| [stack](src/main/java/com/svetanis/datastructures/stack) | stacks built by hand, stack designs, matching brackets, evaluating expressions |
| [tree](src/main/java/com/svetanis/datastructures/tree) | binary trees and binary search trees, n-ary trees, tries, segment trees, dynamic programming on trees |

## Building

Java 25 and Maven: `mvn compile`. Some files use the author's utility library `java-base`, which
must be installed in the local Maven repository first (`mvn install` in that project).
