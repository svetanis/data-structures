package com.svetanis.datastructures.heap;

import static java.util.Comparator.comparingInt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

// 355. Design Twitter
//
// postTweet(uid, tid) records that user uid posted tweet tid. getNewsFeed(uid) returns the ids
// of the 10 most recent tweets posted by uid or by anyone uid follows, most recent first (fewer
// if there are fewer). follow(uid, fid) and unfollow(uid, fid) start and stop uid following fid.
// On LeetCode: 1 <= user ids <= 500, 0 <= tid <= 10^4, every tweet id is different, at most
// 3 * 10^4 calls in all.
//
// The one idea: every tweet is stored as { tid, time }, where time is a counter that goes up by
// one per post, so a larger time is a more recent tweet. Each user's tweets sit in a list in the
// order they were posted, so a user's 10 most recent are the last 10 of the list. A feed needs at
// most 10 tweets, so only the last 10 of each followed user's list (and the user's own) can be
// in it. One heap entry is one of those candidate tweets, and the heap keeps the largest time on
// top; polling it 10 times gives the feed, newest first.
//
// Time: postTweet, follow, unfollow O(1); getNewsFeed O(f log f), f = the users uid follows plus
// uid -- at most 10 candidates per user go into the heap, then at most 10 polls.
// Space: O(t + e) -- t tweets stored, e follow pairs; a feed's heap holds at most 10 * f entries.

public final class Twitter {

	private int timeStamp;
	private Map<Integer, List<int[]>> tweets;
	private Map<Integer, Set<Integer>> followees;

	public Twitter() {
		this.timeStamp = 0;
		this.tweets = new HashMap<>(); // user -> { tid, time } in posting order
		this.followees = new HashMap<>(); // user -> the users they follow
	}

	public void postTweet(int uid, int tid) {
		tweets.putIfAbsent(uid, new ArrayList<>());
		tweets.get(uid).add(new int[] { tid, timeStamp++ }); // POST: stamped with the next time
	}

	public List<Integer> getNewsFeed(int uid) {
		Set<Integer> set = followees.getOrDefault(uid, new HashSet<>());
		set.add(uid); // SELF: incl user's own tweets
		PriorityQueue<int[]> pq = new PriorityQueue<>(comparingInt((int[] a) -> a[1]).reversed()); // ORDER: newest on top
		for (int fid : set) { // EACH user in the feed
			List<int[]> list = tweets.getOrDefault(fid, new ArrayList<>());
			for (int i = list.size() - 1, limit = 10; i >= 0 && limit > 0; i--, limit--) {
				pq.add(list.get(i)); // CANDIDATE: only this user's 10 newest can make the feed
			}
		}
		return newsFeeds(pq); // RETURN the 10 newest candidates
	}

	private List<Integer> newsFeeds(PriorityQueue<int[]> pq) {
		List<Integer> list = new ArrayList<>();
		int limit = 10;
		while (!pq.isEmpty() && list.size() < limit) {
			list.add(pq.poll()[0]); // TAKE the newest left, keep its id
		}
		return list;
	}

	public void follow(int uid, int fid) {
		followees.putIfAbsent(uid, new HashSet<>());
		followees.get(uid).add(fid); // FOLLOW
	}

	public void unfollow(int uid, int fid) {
		if (uid != fid && followees.containsKey(uid)) { // KEEP SELF: a user always sees their own tweets
			followees.get(uid).remove(fid); // UNFOLLOW
		}
	}

	public static void main(String[] args) {
		Twitter twt = new Twitter();
		twt.postTweet(1, 5);
		System.out.println(twt.getNewsFeed(1)); // [5]
		twt.follow(1, 2);
		twt.postTweet(2, 6);
		System.out.println(twt.getNewsFeed(1)); // [6, 5]
		twt.unfollow(1, 2);
		System.out.println(twt.getNewsFeed(1)); // [5]
	}
}
