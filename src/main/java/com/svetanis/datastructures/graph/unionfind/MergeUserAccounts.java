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
// RUNG 2. Accounts are the nodes, and account i joins account j the first time an
// email turns up in both. Finding that overlap is the work, and it needs a second
// map -- email -> the first account that carried it -- consulted on every email of
// every account. Rung 3 deletes that job rather than doing it faster: with emails
// as the nodes, a shared email IS one node both accounts touch, so no lookup for
// an overlap exists anywhere in it.
//
// What choosing accounts buys, and it is one thing: the NAME comes free.
// find(i) hands back an account index, and that account's name is still sitting in
// the input -- accounts.get(root).get(0). Rung 3's find hands back an email id,
// which says nothing about a name, so it carries a second map, email -> name.
// So each rung pays for one map: rung 2 for the overlap, rung 3 for the name.
//
// Two other differences, neither caused by the choice of node:
//   - TreeSet per group, not HashSet. 721 wants each group's emails sorted and
//     duplicate-free, and two accounts that merged both listed the email they
//     shared. TreeSet does both while inserting, so nothing is sorted at the end.
//     Rung 3 uses a List and sorts once at the end -- same result, later.
//   - the parent is a Map<Integer,Integer> keyed 0, 1, 2, ... which is what an
//     int[] already is, with boxing on top. Rung 1 uses the array. This is the one
//     place rung 2 is simply worse, and it buys nothing.

public final class MergeUserAccounts {

  private DisjointSet ds;
  private Map<String, Integer> emails;

  public List<List<String>> merge(List<List<String>> accounts) {
    this.ds = new DisjointSet(accounts.size());
    this.emails = new HashMap<>();
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
        int root = ds.find(i);
        if (!map.containsKey(root)) {
          map.put(root, new TreeSet<>());
        }
        map.get(root).add(email);
      }
    }
    return map;
  }

  private void mergeEmails(List<List<String>> accounts) {
    for (int i = 0; i < accounts.size(); i++) {
      List<String> account = accounts.get(i);
      for (int j = 1; j < account.size(); j++) {
        String email = account.get(j);
        if (emails.containsKey(email)) {
          int index = emails.get(email);
          ds.union(i, index);
        } else {
          emails.put(email, i);
        }
      }
    }
  }

  public static void main(String[] args) {
    List<List<String>> accounts = new ArrayList<>();
    accounts.add(Arrays.asList("John", "johnsmith@mail.com", "john_newyork@mail.com"));
    accounts.add(Arrays.asList("John", "johnsmith@mail.com", "john_work@mail.com"));
    accounts.add(Arrays.asList("Mary", "mary@mail.com"));
    accounts.add(Arrays.asList("John", "johnny@mail.com"));
    MergeUserAccounts mua = new MergeUserAccounts();
    System.out.println(mua.merge(accounts));

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
    System.out.println(new MergeUserAccounts().merge(crossName));
  }
}
