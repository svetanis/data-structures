package com.svetanis.datastructures.graph.unionfind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

// 721. Accounts Merge
// The three 721 files are a ladder, and the rungs differ by WHAT A NODE IS:
//   1. MergeUserAccountsSubmit  -- accounts are nodes, int[] parent, self-contained
//   2. MergeUserAccounts        -- accounts are nodes, Map<Integer,Integer> parent
//   3. MergeUserAccountsByEmail -- EMAILS are nodes, shared DisjointSet
// All three return the same groups. Rung 3 is the one to write: an email shared by
// two accounts is one node they both touch, so the merge across accounts happens
// without anything ever comparing two accounts.
//
// RUNG 1. Accounts are the nodes and the parent is a plain int[], which is what
// the account indices already are. Self-contained, so it pastes on its own.

public final class MergeUserAccountsSubmit {

  private int[] parent;

  public List<List<String>> merge(List<List<String>> accounts) {
    init(accounts.size());
    mergeEmails(accounts);
    Map<Integer, Set<String>> merged = mergeAccounts(accounts);
    List<List<String>> list = new ArrayList<>();
    for (int id : merged.keySet()) {
      String name = accounts.get(id).get(0);
      List<String> account = new ArrayList<>();
      account.add(name);
      account.addAll(merged.get(id));
      list.add(account);
    }
    return list;
  }

  private Map<Integer, Set<String>> mergeAccounts(List<List<String>> accounts) {
    Map<Integer, Set<String>> map = new HashMap<>();
    for (int i = 0; i < accounts.size(); i++) {
      List<String> account = accounts.get(i);
      for (int j = 1; j < account.size(); j++) {
        String email = account.get(j);
        int root = find(i);
        if (!map.containsKey(root)) {
          map.put(root, new TreeSet<>());
        }
        map.get(root).add(email);
      }
    }
    return map;
  }

  private Map<String, Integer> mergeEmails(List<List<String>> accounts) {
    Map<String, Integer> map = new HashMap<>();
    for (int i = 0; i < accounts.size(); i++) {
      List<String> account = accounts.get(i);
      for (int j = 1; j < account.size(); j++) {
        String email = account.get(j);
        if (map.containsKey(email)) {
          int index = map.get(email);
          parent[find(i)] = find(index);
        } else {
          map.put(email, i);
        }
      }
    }
    return map;
  }

  private void init(int n) {
    parent = new int[n];
    for (int i = 0; i < n; i++) {
      parent[i] = i;
    }
  }

  private int find(int x) {
    if (parent[x] != x) {
      parent[x] = find(parent[x]);
    }
    return parent[x];
  }

  public static void main(String[] args) {
    List<List<String>> accounts = new ArrayList<>();
    accounts.add(Arrays.asList("John", "johnsmith@mail.com", "john_newyork@mail.com"));
    accounts.add(Arrays.asList("John", "johnsmith@mail.com", "john_work@mail.com"));
    accounts.add(Arrays.asList("Mary", "mary@mail.com"));
    accounts.add(Arrays.asList("John", "johnny@mail.com"));
    MergeUserAccountsSubmit mua = new MergeUserAccountsSubmit();
    System.out.println(mua.merge(accounts));
  }
}
