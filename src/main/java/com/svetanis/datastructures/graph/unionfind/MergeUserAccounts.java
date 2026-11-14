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
// Time: O(m log m) -- m emails in all; each goes into a TreeSet (the log), and the unions and
// finds add a near-constant factor.
// Space: O(m) -- the email -> first account map, the union-find, and the grouped TreeSets.
//
// Another difference, not caused by the choice of node: TreeSet per group, not
// HashSet. 721 wants each group's emails sorted and duplicate-free, and two accounts
// that merged both listed the email they shared. TreeSet does both while inserting,
// so nothing is sorted at the end. Rung 3 uses a List and sorts once at the end --
// same result, later.

public final class MergeUserAccounts {

  private DisjointSet ds;
  private Map<String, Integer> emails;

  public List<List<String>> merge(List<List<String>> accounts) {
    this.ds = new DisjointSet(accounts.size());
    this.emails = new HashMap<>();
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
        int root = ds.find(i);
        if (!map.containsKey(root)) {
          map.put(root, new TreeSet<>());
        }
        map.get(root).add(email); // COLLECT: sorted and without repeats
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
          ds.union(i, index); // JOIN: this account and the first one that had the email
        } else {
          emails.put(email, i); // RECORD: the first account to carry it
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
    // [[John, john_newyork@mail.com, john_work@mail.com, johnsmith@mail.com], [Mary, mary@mail.com], [John, johnny@mail.com]]
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
    System.out.println(new MergeUserAccounts().merge(crossName)); // [[Alice, a@m.co, b@m.co, shared@m.co]]
  }
}
