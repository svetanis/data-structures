package com.svetanis.datastructures.graph.unionfind;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 721. Accounts Merge
// The three 721 files are a ladder, and the rungs differ by WHAT A NODE IS:
//   1. MergeUserAccountsSubmit  -- accounts are nodes, int[] parent, self-contained
//   2. MergeUserAccounts        -- accounts are nodes, Map<Integer,Integer> parent
//   3. MergeUserAccountsByEmail -- EMAILS are nodes, shared DisjointSet
// All three return the same groups. Rung 3 is the one to write: an email shared by
// two accounts is one node they both touch, so the merge across accounts happens
// without anything ever comparing two accounts.
//
// RUNG 3, and the version to carry.
// The emails are the nodes, not the accounts.
// MergeUserAccounts takes the other model: accounts are the nodes, joined when they
// share an email. Both are correct; this one needs no cross-account comparison at all,
// because two accounts in the same row of the answer are joined THROUGH the email they
// share -- it is one node that both of them touched.

public final class MergeUserAccountsByEmail {
  // Time Complexity: O(m log m) -- m emails, the log from sorting each group
  // Space Complexity: O(m)

  // email -> a small id, so the int[] parent array can be used unchanged.
  // a hash code will not do: it is negative, and far larger than any array.
  private Map<String, Integer> ids;
  // email -> the name on the account it was first seen in. keyed by EMAIL, never by
  // name: the statement's own example has two different people both called John.
  private Map<String, String> names;
  private DisjointSet ds;

  public List<List<String>> accountsMerge(List<List<String>> accounts) {
    this.ids = new HashMap<>();
    this.names = new HashMap<>();
    buildMaps(accounts);
    // the size is known only after the first pass, so joining is a second pass
    this.ds = new DisjointSet(ids.size());
    join(accounts);
    return collect(group());
  }

  // one id per distinct email, handed out in order of first appearance
  private void buildMaps(List<List<String>> accounts) {
    int id = 0;
    for (List<String> account : accounts) {
      String name = account.get(0);
      // index 0 is the name; the emails start at 1
      for (int i = 1; i < account.size(); i++) {
        String email = account.get(i);
        if (ids.containsKey(email)) {
          continue;
        }
        ids.put(email, id++);
        names.put(email, name);
      }
    }
  }

  // every email of one account belongs to one person, so join them to the first one.
  // nothing here compares two accounts: a shared email is a single node that both
  // accounts join to, which merges them without anyone looking for the overlap.
  private void join(List<List<String>> accounts) {
    for (List<String> account : accounts) {
      int first = ids.get(account.get(1));
      for (int i = 2; i < account.size(); i++) {
        ds.union(first, ids.get(account.get(i)));
      }
    }
  }

  // root -> that person's emails. built AFTER every union: a root recorded during
  // the joining pass can be demoted by a later union and stop being a root.
  private Map<Integer, List<String>> group() {
    Map<Integer, List<String>> grouped = new HashMap<>();
    for (String email : ids.keySet()) {
      int root = ds.find(ids.get(email));
      grouped.computeIfAbsent(root, key -> new ArrayList<>()).add(email);
    }
    return grouped;
  }

  // the statement wants the name first, then the emails sorted
  private List<List<String>> collect(Map<Integer, List<String>> grouped) {
    List<List<String>> merged = new ArrayList<>();
    for (List<String> emails : grouped.values()) {
      List<String> sorted = new ArrayList<>(emails);
      Collections.sort(sorted);
      List<String> row = new ArrayList<>();
      // any email of the group names the person: they all belong to one person,
      // and the statement guarantees that person's accounts carry one name
      row.add(names.get(sorted.get(0)));
      row.addAll(sorted);
      merged.add(row);
    }
    return merged;
  }

  public static void main(String[] args) {
    List<List<String>> accounts = new ArrayList<>();
    accounts.add(List.of("John", "johnsmith@mail.com", "john_newyork@mail.com"));
    accounts.add(List.of("John", "johnsmith@mail.com", "john00@mail.com"));
    accounts.add(List.of("Mary", "mary@mail.com"));
    accounts.add(List.of("John", "johnnybravo@mail.com"));
    MergeUserAccountsByEmail merger = new MergeUserAccountsByEmail();
    // [John, john00, john_newyork, johnsmith] | [Mary, mary] | [John, johnnybravo]
    for (List<String> row : merger.accountsMerge(accounts)) {
      System.out.println(row);
    }
    // two DIFFERENT names sharing an email. LC 721 merges them: a shared email
    // means one person, and the name is never consulted. A variant that refuses to
    // merge across names is a different problem, and this is the input that tells
    // the two apart. LC 721 separately guarantees that one person's accounts all
    // carry one name, so this input cannot occur there -- it is here to pin down
    // which rule the code follows.
    List<List<String>> crossName = new ArrayList<>();
    crossName.add(new ArrayList<>(List.of("Alice", "shared@m.co", "a@m.co")));
    crossName.add(new ArrayList<>(List.of("Bob", "shared@m.co", "b@m.co")));
    // one row: [Alice, a@m.co, b@m.co, shared@m.co]
    System.out.println(new MergeUserAccountsByEmail().accountsMerge(crossName));
  }

  private static final class DisjointSet {

    private final int[] parent;
    private final int[] size;

    DisjointSet(int n) {
      this.parent = new int[n];
      this.size = new int[n];
      for (int i = 0; i < n; i++) {
        parent[i] = i;
        size[i] = 1;
      }
    }

    int find(int x) {
      int root = parent[x];
      while (root != parent[root]) {
        root = parent[root];
      }
      parent[x] = root;
      return root;
    }

    boolean union(int a, int b) {
      int ra = find(a);
      int rb = find(b);
      if (ra == rb) {
        return false;
      }
      if (size[ra] > size[rb]) {
        parent[rb] = ra;
        size[ra] += size[rb];
      } else {
        parent[ra] = rb;
        size[rb] += size[ra];
      }
      return true;
    }

  }
}
