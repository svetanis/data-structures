package com.svetanis.datastructures.graph.undirected.traverse;

import com.svetanis.datastructures.graph.undirected.Graph;

// Groups of friends in an undirected graph: the number of groups, and the product of their sizes.
//
// Input: a Graph g on V + 1 vertices, V = 6; the people are the vertices 1 .. V and an edge joins
// two friends. No statement is available for this file; it is not a LeetCode problem and has not
// been run against a judge, so the contract below is read from the code, not from a statement.
// Returns a Result: components -- the number of connected components found -- and groups -- the
// product of the component sizes, or 0 when components is 1.
//
// The one idea: a group of friends is a connected component, so one walk per component finds it
// and counts its members; visited[v] says whether v has been counted. groups multiplies the sizes
// of the components as they are found.
//
// Vertex 0 is no person: the loop runs v = 1 .. V, so it is never counted, and person V starts a
// walk of its own when nobody before it reached them.
//
// Time: O(V + E) -- each vertex is entered once and each adjacency entry is read once.
// Space: O(V) -- the visited flags, plus the recursion, one call per vertex along a chain.
//
// Siblings: DFS.dfs(Graph) walks every component of any Graph and counts them, vertex 0 included.

public final class CountGroupsInGraphOfFriends {

  private static int V = 6;

  public record Result(int components, int groups) {}

  public static Result countGroups(Graph g) {
    int groups = 1;
    int components = 0;
    boolean[] visited = new boolean[V + 1];

    for (int v = 1; v <= V; ++v) { // PEOPLE: 1 .. V, vertex 0 is no person
      if (!visited[v]) {
        components++; // NEW COMPONENT: v starts a group not yet counted
        groups *= dfs(g, v, visited); // TAKE: multiply by the group's size
      }
    }
    if (components == 1) {
      groups = 0;
    }
    return new Result(components, groups);
  }

  public static int dfs(Graph g, int v, boolean[] visited) {
    visited[v] = true;
    int count = 1; // BASE: v itself
    for (int u : g.adj(v)) {
      if (!visited[u]) {
        count += dfs(g, u, visited); // WALK: add the friends found beyond u
      }
    }
    return count;
  }

  public static void main(String[] args) {
    Graph g = new Graph(V + 1);
    g.addEdge(1, 2);
    g.addEdge(3, 4);
    g.addEdge(5, 6);

    System.out.println(countGroups(g)); // Result[components=3, groups=8]
  }
}
