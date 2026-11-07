package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

// 2254. Design Video Sharing Platform
//
// upload(video) stores a video, a string of digits, under the smallest id not in use and
// returns that id; the ids of removed videos are used again. remove(videoId) deletes the video
// and its counts. watch(videoId, startMinute, endMinute) adds a view and returns the video from
// startMinute to min(endMinute, length - 1), both included. like and dislike add one;
// getLikesAndDislikes returns [likes, dislikes]; getViews returns the views. A missing id
// gives "-1", [-1] or -1, and like, dislike and remove do nothing. On LeetCode:
// 1 <= video.length <= 10^5, 0 <= videoId <= 10^5, 0 <= startMinute < endMinute < 10^5,
// startMinute < video.length (so watch never cuts before the start), at most 10^5 calls.
//
// Ids: a counter for the next never-used id, and a min-heap of the removed ones. Every removed
// id is below the counter, so the heap's smallest, when there is one, is the smallest id not
// in use. Each count lives in its own map by id, and remove clears all of them, so a reused id
// starts again from zero views, likes and dislikes.
//
// Siblings, same problem: none in this package.

public final class VideoSharingPlatform {
	// Time complexity: upload, remove O(log r), r removed ids in the heap; watch O(m), the
	//   m-character substring; like, dislike, the getters O(1) on average
	// Auxiliary Space: O(L + u), L characters of stored video and u ids ever handed out

	private int videoId; // NEXT: the smallest id never handed out
	private PriorityQueue<Integer> usedIds; // FREED: ids of removed videos, smallest first
	private Map<Integer, String> vidToVideo;
	private Map<Integer, Integer> vidToViews;
	private Map<Integer, Integer> vidToLikes;
	private Map<Integer, Integer> vidToDislikes;

	public VideoSharingPlatform() {
		this.videoId = 0;
		this.usedIds = new PriorityQueue<>();
		this.vidToVideo = new HashMap<>();
		this.vidToViews = new HashMap<>();
		this.vidToLikes = new HashMap<>();
		this.vidToDislikes = new HashMap<>();
	}

	public int upload(String video) {
		int videoId = getVideoId();
		vidToVideo.put(videoId, video);
		return videoId;
	}

	private int getVideoId() {
		int result;
		if (usedIds.isEmpty()) {
			result = this.videoId; // NEW: no freed id, take the counter
			this.videoId += 1;
		} else {
			result = usedIds.poll(); // REUSE: the smallest freed id, below the counter
		}
		return result;
	}

	public void remove(int vid) {
		if (vidToVideo.containsKey(vid)) {
			usedIds.add(vid); // FREE the id
			vidToVideo.remove(vid);
			vidToViews.remove(vid); // CLEAR: a reused id starts from zero
			vidToLikes.remove(vid);
			vidToDislikes.remove(vid);
		}
	}

	public String watch(int vid, int startMinute, int endMinute) {
		if (!vidToVideo.containsKey(vid)) {
			return "-1";
		}
		vidToViews.merge(vid, 1, Integer::sum); // VIEW
		String video = vidToVideo.get(vid);
		int duration = Math.min(endMinute, video.length() - 1) - startMinute + 1; // CLIP: stop at the last minute
		return video.substring(startMinute, startMinute + duration);
	}

	public void like(int vid) {
		if (vidToVideo.containsKey(vid)) {
			vidToLikes.merge(vid, 1, Integer::sum);
		}
	}

	public void dislike(int vid) {
		if (vidToVideo.containsKey(vid)) {
			vidToDislikes.merge(vid, 1, Integer::sum);
		}
	}

	public int[] getLikesAndDislikes(int vid) {
		if (vidToVideo.containsKey(vid)) {
			int[] result = new int[2];
			result[0] = vidToLikes.getOrDefault(vid, 0); // ZERO: never liked yet
			result[1] = vidToDislikes.getOrDefault(vid, 0);
			return result;
		} else {
			return new int[] { -1 };
		}
	}

	public int getViews(int vid) {
		if (vidToVideo.containsKey(vid)) {
			return vidToViews.getOrDefault(vid, 0); // ZERO: never watched yet
		} else {
			return vidToViews.getOrDefault(vid, -1); // MISSING: remove cleared the views, so -1
		}
	}

	public static void main(String[] args) {
		VideoSharingPlatform vsp = new VideoSharingPlatform();
		System.out.println(vsp.upload("123")); // The smallest available videoId is 0, so return 0.
		System.out.println(vsp.upload("456")); // The smallest available videoId is 1, so return 1.
		vsp.remove(4); // There is no video associated with videoId 4, so do nothing.
		vsp.remove(0); // Remove the video associated with videoId 0.
		System.out.println(vsp.upload("789")); // Since the video associated with videoId 0 was deleted,
		// 0 is the smallest available videoId, so return 0.
		System.out.println(vsp.watch(1, 0, 5)); // The video associated with videoId 1 is "456".
		// The video from minute 0 to min(5, 3 - 1) = 2 is "456", so return "456".
		System.out.println(vsp.watch(1, 0, 1)); // The video associated with videoId 1 is "456".
		// The video from minute 0 to min(1, 3 - 1) = 1 is "45", so return "45".
		vsp.like(1); // Increase the number of likes on the video associated with videoId 1.
		vsp.dislike(1); // Increase the number of dislikes on the video associated with videoId 1.
		vsp.dislike(1); // Increase the number of dislikes on the video associated with videoId 1.
		print(vsp.getLikesAndDislikes(1)); // There is 1 like and 2 dislikes on the video associated with videoId
																							// 1, so return [1, 2].
		System.out.println(vsp.getViews(1)); // The video associated with videoId 1 has 2 views, so return 2.

		System.out.println();

		VideoSharingPlatform vsp1 = new VideoSharingPlatform();
		System.out.println(vsp1.upload("62370")); // 0
		System.out.println(vsp1.upload("75")); // 1
		System.out.println(vsp1.getViews(0)); // 0

		System.out.println();

		VideoSharingPlatform vsp2 = new VideoSharingPlatform();
		vsp2.remove(0); // There is no video associated with videoId 0, so do nothing.
		System.out.println(vsp2.watch(0, 0, 1)); // There is no video associated with videoId 0, so return "-1".
		vsp2.like(0); // There is no video associated with videoId 0, so do nothing.
		vsp2.dislike(0); // There is no video associated with videoId 0, so do nothing.
		print(vsp2.getLikesAndDislikes(0)); // There is no video associated with videoId 0, so return [-1].
		System.out.println(vsp2.getViews(0)); // There is no video associated with videoId 0, so return -1.
	}

	private static void print(int[] a) {
		for (int value : a) {
			System.out.print(value);
			System.out.print(' ');
		}
		System.out.println();
	}
}
