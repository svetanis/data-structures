package com.svetanis.datastructures.graph.unionfind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

// 721. Accounts Merge
//
// Input: accounts -- accounts[i][0] is the name on account i, the rest are its emails. Two
// accounts belong to the same person when they share at least one email, and a person's accounts
// all carry one name. Returns one list per person: the name, then every email of that person,
// sorted and without repeats, in any order of the lists. On LeetCode: 1 <= accounts.length <= 1000,
// 2 <= accounts[i].length <= 10, 1 <= accounts[i][j].length <= 30, accounts[i][0] is a name made
// of letters, the other entries are valid emails.
//
// The one idea: union-find joins things that belong together, so first decide what one element
// IS. In the account-node versions one element is one account (its index); account i joins
// account j the first time an email appears in both. In the ByEmail version one element is one
// email. After every join, all accounts (or emails) with the same root are one person.
//
// The three 721 files are a ladder, and the rungs differ by WHAT A NODE IS:
//   1. MergeUserAccountsSubmit  -- accounts are nodes, int[] parent inlined, self-contained
//   2. MergeUserAccounts        -- accounts are nodes, the shared DisjointSet
//   3. MergeUserAccountsByEmail -- EMAILS are nodes, a DisjointSet nested in the file
// All three return the same groups. Rung 3 is the one to write: an email shared by
// two accounts is one node they both touch, so the merge across accounts happens
// without anything ever comparing two accounts.
//
// RUNG 1. Accounts are the nodes and the parent is a plain int[], which is what
// the account indices already are. Self-contained, so it pastes on its own.
//
// Time: O(m log m) -- m emails in all; each goes into a TreeSet (the log), and the unions and
// finds add a near-constant factor.
// Space: O(m) -- the email -> first account map, the parent array, and the grouped TreeSets.

public final class MergeUserAccountsSubmit {

  private int[] parent;

  public List<List<String>> merge(List<List<String>> accounts) {
    init(accounts.size());
    mergeEmails(accounts); // JOIN: accounts that share an email
    Map<Integer, Set<String>> merged = mergeAccounts(accounts); // GROUP: emails under their root
    List<List<String>> list = new ArrayList<>();
    for (int id : merged.keySet()) {
      String name = accounts.get(id).get(0); // NAME: still in the input, at the root account
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
        map.get(root).add(email); // COLLECT: sorted and without repeats
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
          parent[find(i)] = find(index); // JOIN: this account and the first one that had the email
        } else {
          map.put(email, i); // RECORD: the first account to carry it
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
      parent[x] = find(parent[x]); // WALK up to the root, COMPRESS on the way back
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
    // [[John, john_newyork@mail.com, john_work@mail.com, johnsmith@mail.com], [Mary, mary@mail.com], [John, johnny@mail.com]]
    System.out.println(mua.merge(accounts));
  }
}
