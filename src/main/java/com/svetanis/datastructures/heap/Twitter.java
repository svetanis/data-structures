package com.svetanis.datastructures.heap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

// 355. Design Twitter

public final class Twitter {
	// Time Complexity: O(users * tweets + k log k)

	private int timeStamp;
	private Map<Integer, List<int[]>> tweets;
	private Map<Integer, Set<Integer>> followees;

	public Twitter() {
		this.timeStamp = 0;
		this.tweets = new HashMap<>();
		this.followees = new HashMap<>();
	}

	public void postTweet(int uid, int tid) {
		tweets.putIfAbsent(uid, new ArrayList<>());
		tweets.get(uid).add(new int[] { tid, timeStamp++ });
	}

	public List<Integer> getNewsFeed(int uid) {
		Set<Integer> set = followees.getOrDefault(uid, new HashSet<>());
		set.add(uid); // incl user's own tweets
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[1] - a[1]);
		for (int fid : set) {
			List<int[]> list = tweets.getOrDefault(fid, new ArrayList<>());
			for (int i = list.size() - 1, limit = 10; i >= 0 && limit > 0; i--, limit--) {
				pq.add(list.get(i));
			}
		}
		return newsFeeds(pq);
	}

	private List<Integer> newsFeeds(PriorityQueue<int[]> pq) {
		List<Integer> list = new ArrayList<>();
		int limit = 10;
		while (!pq.isEmpty() && list.size() < limit) {
			list.add(pq.poll()[0]);
		}
		return list;
	}

	public void follow(int uid, int fid) {
		followees.putIfAbsent(uid, new HashSet<>());
		followees.get(uid).add(fid);
	}

	public void unfollow(int uid, int fid) {
		if (uid != fid && followees.containsKey(uid)) {
			followees.get(uid).remove(fid);
		}
	}

	public static void main(String[] args) {
		Twitter twt = new Twitter();
		twt.postTweet(1, 5);
		System.out.println(twt.getNewsFeed(1)); // 5
		twt.follow(1, 2);
		twt.postTweet(2, 6);
		System.out.println(twt.getNewsFeed(1)); // 6, 5
		twt.unfollow(1, 2);
		System.out.println(twt.getNewsFeed(1)); // 5
	}
}
