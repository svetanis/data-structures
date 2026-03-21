# 📊 Data Structures - LeetCode Solutions

A **curated collection of LeetCode solutions organized by data structure type**. Perfect for interview prep and understanding which data structure to use for specific problems.

**Perfect for:**
- 🎓 Students learning data structures through practice
- 💼 Job seekers preparing for technical interviews
- 🚀 Engineers improving problem-solving skills
- 📚 Anyone wanting to recognize data structure patterns in real problems

---

## 🗂️ How This Repository is Organized

Solutions are organized by **data structure type**, not by difficulty. This helps you:
- ✅ Recognize which structure solves a problem
- ✅ See multiple problems solved by the same structure
- ✅ Build pattern recognition skills
- ✅ Master one data structure at a time

---

## 📚 Data Structure Categories (8 Core Types)

### 📦 [Arrays](src/main/java/com/svetanis/datastructures/array)
**When to use:** Random access, fixed-size storage, simple data collection

**Key Operations:**
- Access: O(1)
- Insert: O(N)
- Delete: O(N)
- Search: O(N) or O(log N) if sorted

**Common LeetCode Problems:**
- Two Sum
- Best Time to Buy and Sell Stock
- Contains Duplicate
- Valid Anagram
- Group Anagrams
- Longest Consecutive
- Rotate Image
- Set Matrix Zeroes
- Spiral Matrix
- Missing Number
- Product of Array Except Self
- Majority Element
- Maximum Subarray
- Merge Sorted Array
- Remove Duplicates

**Pattern Recognition:**
- Two pointers
- Sliding window
- Prefix/suffix sums
- In-place modifications

---

### 🔗 [Linked Lists](src/main/java/com/svetanis/datastructures/linkedlist)
**When to use:** Dynamic insertion/deletion, no random access needed

**Key Operations:**
- Access: O(N)
- Insert at head: O(1)
- Delete: O(N) for finding
- Search: O(N)

**Common LeetCode Problems:**
- Reverse Linked List
- Merge Two Sorted Lists
- Merge K Sorted Lists
- Remove Duplicates from Sorted List
- Remove Nth Node From End of List
- Reorder List
- Cycle Detection (Floyd's Algorithm)
- Intersection of Two Linked Lists
- Copy List with Random Pointer
- Palindrome Linked List
- Add Two Numbers
- Partition List
- Rotate List
- Swap Nodes in Pairs
- Reverse Nodes in K-Group

**Pattern Recognition:**
- Fast and slow pointers
- Reverse operations
- Cycle detection
- Finding middle element

---

### 📚 [Stacks](src/main/java/com/svetanis/datastructures/stack)
**When to use:** LIFO access, expression evaluation, backtracking

**Key Operations:**
- Push: O(1)
- Pop: O(1)
- Peek: O(1)

**Common LeetCode Problems:**
- Valid Parentheses
- Evaluate Reverse Polish Notation
- Daily Temperatures
- Next Greater Element
- Largest Rectangle in Histogram
- Trapping Rain Water
- Decode String
- Remove K Digits
- Asteroid Collision
- Remove Duplicate Letters
- Basic Calculator
- Minimum Remove to Make Valid Parentheses
- Online Stock Span
- Simplify Path
- Binary Search Tree Iterator

**Pattern Recognition:**
- Matching pairs
- Previous/next greater element
- Expression parsing
- Monotonic stack tricks

---

### 🚶 [Queues](src/main/java/com/svetanis/datastructures/queue)
**When to use:** FIFO access, BFS, level-order processing

**Key Operations:**
- Enqueue: O(1)
- Dequeue: O(1)
- Peek: O(1)

**Common LeetCode Problems:**
- Number of Islands
- Rotting Oranges
- Walls and Gates
- Shortest Path in Binary Matrix
- Zigzag Level Order Traversal
- Populating Next Right Pointers
- Binary Tree Level Order Traversal
- Word Ladder
- Perfect Squares
- Max Area of Island
- As Far from Land as Possible
- 01 Matrix
- Sliding Window Maximum
- Task Scheduler

**Pattern Recognition:**
- BFS traversal
- Level-order processing
- Shortest path (unweighted)
- Connected components

---

### 🌳 [Trees](src/main/java/com/svetanis/datastructures/tree)
**When to use:** Hierarchical data, searching, range queries

**Key Operations:**
- Insert: O(log N) to O(N)
- Delete: O(log N) to O(N)
- Search: O(log N) to O(N)

**Common LeetCode Problems:**
- Binary Tree Inorder Traversal
- Binary Tree Level Order Traversal
- Lowest Common Ancestor of BST
- Validate Binary Search Tree
- Kth Smallest Element in BST
- Binary Tree Maximum Path Sum
- Invert Binary Tree
- Same Tree
- Symmetric Tree
- Balanced Binary Tree
- Path Sum
- Sum Root to Leaf Numbers
- Binary Search Tree Iterator
- Serialize and Deserialize Binary Tree
- Construct Binary Tree from Inorder/Postorder
- Flatten Binary Tree to Linked List
- Recover Binary Search Tree
- Word Search II (using Trie)
- LRU Cache (using Tree/Hash)
- Employee Importance (Tree traversal)

**Tree Types in Problems:**
- Binary Search Tree
- Balanced Binary Tree
- Binary Indexed Tree (Fenwick)
- Trie (prefix tree)
- Segment Tree

**Pattern Recognition:**
- Tree traversals (inorder, preorder, postorder, level-order)
- DFS vs BFS
- Path problems
- LCA (Lowest Common Ancestor)
- Serialization

---

### 🗺️ [Hash Maps / Hash Tables](src/main/java/com/svetanis/datastructures/hashmap)
**When to use:** Fast lookups, counting, grouping, caching

**Key Operations:**
- Insert: O(1) average
- Delete: O(1) average
- Search: O(1) average

**Common LeetCode Problems:**
- Two Sum
- Isomorphic Strings
- Valid Anagram
- Group Anagrams
- Happy Number
- Majority Element
- Intersection of Two Arrays
- Happy Number
- Word Pattern
- Ransom Note
- First Unique Character in a String
- Longest Substring Without Repeating Characters
- Minimum Window Substring
- Subarray Sum Equals K
- LRU Cache
- LFU Cache
- Design Hashmap
- Design HashMap
- Contains Duplicate
- Valid Sudoku
- Alien Dictionary
- Verifying an Alien Dictionary
- Most Common Word
- Uncommon from Sentences
- Majority Element II

**Pattern Recognition:**
- Counting elements
- Finding pairs/duplicates
- Grouping by key
- Caching strategies
- Anagram detection

---

### 🔺 [Heaps](src/main/java/com/svetanis/datastructures/heap)
**When to use:** Priority-based access, K largest/smallest, median finding

**Key Operations:**
- Insert: O(log N)
- Remove min/max: O(log N)
- Get min/max: O(1)

**Common LeetCode Problems:**
- K Closest Points to Origin
- K Largest Elements
- Top K Frequent Elements
- Merge K Sorted Lists
- Find Median from Data Stream
- Sliding Window Maximum
- Reorganize String
- Rearrange String K Distance Apart
- Ugly Number II
- Smallest Range Covering Elements from K Lists
- Minimum Cost to Connect Sticks
- Last Stone Game
- Furthest Building You Can Reach
- Maximum Performance of a Team
- IPO (Investment Problems)
- Minimize Deviation in Array

**Pattern Recognition:**
- K largest/smallest problems
- Median finding
- Frequency-based sorting
- Priority-based selection
- Top K patterns

---

### 📈 [Graphs](src/main/java/com/svetanis/datastructures/graph)
**When to use:** Networks, relationships, connectivity, shortest paths

**Key Algorithms:**
- **BFS:** O(N+E), shortest path (unweighted)
- **DFS:** O(N+E), connected components
- **Dijkstra:** O((N+E)log N), shortest path (weighted)
- **Topological Sort:** O(N+E), dependency resolution

**Common LeetCode Problems:**
- Number of Connected Components
- Clone Graph
- Course Schedule (cycle detection)
- Course Schedule II (topological sort)
- Alien Dictionary (topological sort + graph)
- Graph Valid Tree
- Number of Islands
- Pacific Atlantic Water Flow
- Surrounded Regions
- Walls and Gates
- Path Sum III (tree as graph)
- Network Delay Time (Dijkstra)
- Minimum Cost to Connect All Cities (MST)
- Reconstruct Itinerary (Eulerian path)
- Evaluating Division
- Word Ladder
- Shortest Bridge
- Reachable Nodes In Subdivided Graph
- Find Judges in Town (in-degree, out-degree)
- All Paths From Source to Target

**Pattern Recognition:**
- Connected components
- Cycle detection
- Shortest path
- Topological sort
- Path finding
- Tree as special graph

---

## 🎯 How to Use This Repository

### 1️⃣ **Encounter a LeetCode Problem**
```
You see: "Two Sum" or "Merge K Sorted Lists"
↓
Identify the data structure involved
↓
Navigate to that folder in this repo
↓
Browse the solutions
```

### 2️⃣ **Study Solutions**
```
For each problem:
1. Try to solve it on LeetCode first!
2. If stuck, come here to see approach
3. Understand the logic
4. Code it yourself without looking
5. Compare with solution here
```

### 3️⃣ **Pattern Recognition**
```
See multiple problems in same folder?
↓
Recognize the common pattern
↓
Learn to apply pattern to new problems
↓
Master the data structure
```

### 4️⃣ **Interview Preparation**
```
Weak in arrays? Linked lists?
↓
Go to that folder
↓
Solve all the problems there
↓
Build confidence in that structure
```

---

## 💡 Problem Statistics

| Data Structure | # Problems | Difficulty Range |
|---|---|---|
| Arrays | 15+ | Easy to Hard |
| Linked Lists | 15+ | Easy to Hard |
| Stacks | 12+ | Medium to Hard |
| Queues | 12+ | Medium to Hard |
| Hash Maps | 15+ | Easy to Hard |
| Trees | 20+ | Easy to Hard |
| Heaps | 12+ | Medium to Hard |
| Graphs | 15+ | Medium to Hard |

---

## 🗺️ Data Structure Quick Reference

### Need Fast Lookup?
- **Hash Map** - Two Sum, Anagrams, Contains Duplicate
- **Tree** - Kth Smallest, Validate BST
- **Array** - Binary search (if sorted)

### Need to Process Level-by-Level?
- **Queue with BFS** - Number of Islands, Rotting Oranges

### Need to Process All Paths?
- **Stack/DFS** - Daily Temperatures, Trapping Rain Water

### Need Priority-Based Selection?
- **Heap** - K Largest, Top K Frequent, Merge K Lists

### Need to Find Relationships?
- **Graph** - Course Schedule, Clone Graph, Word Ladder

### Need Reversing or Pairs Matching?
- **Stack** - Valid Parentheses, Decode String
- **Linked List** - Reverse List, Palindrome Check

### Need Dynamic Size with Quick Insert/Delete?
- **Linked List** - Merge Sorted Lists, Partition List
- **Hash Map** - LRU Cache, Word Pattern

---

## 🚀 Getting Started

### Setup
```bash
git clone https://github.com/svetanis/data-structures.git
cd data-structures
mvn clean install
```

### Find a Solution
```
1. Go to LeetCode problem (e.g., "Two Sum")
2. Identify data structure (e.g., "Hash Map")
3. Navigate to: src/main/java/com/svetanis/datastructures/hashmap/
4. Browse the implementation
5. Study and learn
```

### Code Along
```
1. Read problem on LeetCode
2. Try to solve without looking at this repo
3. Compare with solution here
4. Understand the approach
5. Close repo and code from memory
6. Repeat next time faster
```

---

## 🎓 Interview Tips

### Arrays
- Think about edge cases: empty, single element
- Consider space-time trade-offs
- Multiple passes vs single pass

### Linked Lists
- Draw the list while solving
- Use fast/slow pointer technique
- Be careful with null pointers

### Stacks
- When pushing, think about what you need
- Monotonic stack is a powerful pattern
- Recursion is implicit stack usage

### Queues
- BFS is the natural algorithm
- Level-order processing
- Distance/shortest path problems

### Hash Maps
- Count things with map
- Track what you've seen
- Group similar items

### Trees
- Recursion feels natural here
- Think about base cases
- Both DFS and BFS have their place

### Heaps
- Min heap for "smallest K"
- Max heap for "largest K"
- Frequency problems often use heaps

### Graphs
- Build adjacency list first
- Track visited to avoid cycles
- Choose BFS vs DFS wisely

---

## 📖 Common Problem Patterns

### Two Sum / Multiple Sum
- **Structure:** Hash Map or Sorted Array + Two Pointers
- **Folders:** Arrays, Hash Maps

### Merge K Sorted Lists
- **Structure:** Heap or Binary Search
- **Folders:** Linked Lists, Heaps

### Find K Largest / K Smallest
- **Structure:** Heap or Sorting
- **Folders:** Heaps, Arrays

### Path / Connectivity Problems
- **Structure:** Graph (DFS/BFS)
- **Folders:** Graphs

### Expression Evaluation
- **Structure:** Stack
- **Folders:** Stacks

### LRU / LFU Cache
- **Structure:** Hash Map + Linked List or Tree
- **Folders:** Hash Maps, Linked Lists

### Tree Traversal
- **Structure:** Recursion or Stack/Queue
- **Folders:** Trees

---

## 🎯 Study by Difficulty

### Warm-Up (Easy)
- Two Sum (Hash Map)
- Reverse Linked List (Linked List)
- Valid Parentheses (Stack)
- Binary Tree Level Order (Tree)
- Majority Element (Array)

### Building Confidence (Medium)
- Merge K Sorted Lists (Heap)
- Word Ladder (Graph)
- Number of Islands (Graph)
- Serialize Binary Tree (Tree)
- LRU Cache (Hash Map + Linked List)

### Interview Ready (Hard)
- Trapping Rain Water (Stack)
- Sliding Window Maximum (Deque)
- Employee Free Time (Heap + Merge)
- Word Search II (Trie)
- Reconstruct Itinerary (Graph + DFS)

---

## 💪 Mastery Checklist

**Arrays**
- [ ] Two pointer technique
- [ ] Prefix/suffix sums
- [ ] In-place modifications
- [ ] Sliding window

**Linked Lists**
- [ ] Fast/slow pointers
- [ ] Reversing
- [ ] Cycle detection
- [ ] Merge operations

**Stacks**
- [ ] Matching pairs
- [ ] Monotonic stack
- [ ] Expression evaluation
- [ ] DFS variant

**Queues**
- [ ] BFS traversal
- [ ] Level-order processing
- [ ] Shortest path
- [ ] Connected components

**Hash Maps**
- [ ] Counting patterns
- [ ] Grouping patterns
- [ ] Caching patterns
- [ ] Two-pass solutions

**Trees**
- [ ] All traversals
- [ ] BST properties
- [ ] Path problems
- [ ] LCA problems

**Heaps**
- [ ] K largest/smallest
- [ ] Heap sort
- [ ] Median finding
- [ ] Priority selection

**Graphs**
- [ ] Graph construction
- [ ] BFS & DFS
- [ ] Topological sort
- [ ] Shortest path

---

## ⏱️ Recommended Pace

### Week 1: Arrays
- Solve 5-10 array problems
- Understand two-pointer pattern

### Week 2: Linked Lists
- Solve 5-10 linked list problems
- Master reversal and fast/slow

### Week 3: Stacks & Queues
- Solve 5 stack problems
- Solve 5 queue problems

### Week 4: Hash Maps
- Solve 8-10 hash map problems
- Build confidence with counting

### Week 5: Trees
- Solve 10-15 tree problems
- Master all traversals

### Week 6: Heaps
- Solve 6-8 heap problems
- Understand priority problems

### Week 7: Graphs
- Solve 8-10 graph problems
- Practice BFS and DFS

### Week 8: Integration
- Mix problems from multiple structures
- Build problem-solving instinct

---

## 🎓 Learning Philosophy

> "Study the **solutions** to understand the **patterns**. The goal is to **recognize patterns** in new problems, not memorize answers."

✅ **DO:**
- Try problems first
- Review multiple approaches
- Understand the why
- Solve similar problems

❌ **DON'T:**
- Copy paste solutions
- Memorize without understanding
- Stop after one solution
- Skip edge cases

---

## 🤝 Contributing

Found a better approach? Missing a problem solution?
- Submit a pull request
- Add optimized solutions
- Improve code clarity
- Share insights

---

## 📞 Problem Not Found?

If you don't find a specific LeetCode problem:
1. Check if it's in a different folder (e.g., some array problems are in hash map)
2. The solution might use hybrid approaches
3. Similar problems in the repo can teach the same pattern
4. Use the folder organization to understand what structure could solve it

---

**Happy Solving! 🚀**

Master one data structure at a time through real LeetCode problems. Pattern recognition is key!