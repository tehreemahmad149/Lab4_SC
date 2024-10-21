/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * SocialNetwork provides methods that operate on a social network.
 * 
 * A social network is represented by a Map<String, Set<String>> where map[A] is
 * the set of people that person A follows on Twitter, and all people are
 * represented by their Twitter usernames. Users can't follow themselves. If A
 * doesn't follow anybody, then map[A] may be the empty set, or A may not even exist
 * as a key in the map; this is true even if A is followed by other people in the network.
 * Twitter usernames are not case sensitive, so "ernie" is the same as "ERNie".
 * A username should appear at most once as a key in the map or in any given
 * map[A] set.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {
						    /**
						     * Guess who might follow whom, from evidence found in tweets.
						     * 
						     * @param tweets
						     *            a list of tweets providing the evidence, not modified by this
						     *            method.
						     * @return a social network (as defined above) in which Ernie follows Bert
						     *         if and only if there is evidence for it in the given list of
						     *         tweets.
						     *         One kind of evidence that Ernie follows Bert is if Ernie
						     *         @-mentions Bert in a tweet. This must be implemented. Other kinds
						     *         of evidence may be used at the implementor's discretion. I AM USING ONLY THIS SPECIFIED MENTIONS ONES
						     *         All the Twitter usernames in the returned social network must be
						     *         either authors or @-mentions in the list of tweets.
						     */
	public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        
        for (Tweet tweet : tweets) {/*set of tweets is being passed*/
            String author = tweet.getAuthor().toLowerCase(); //get everything in a single case
            Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet)); // extract mentioned users using Extract.java
            
            // ensure the author is a key in the map
            followsGraph.putIfAbsent(author, new HashSet<>());
            //someone eexplain the above
            
            // adding all mentioned users to the author connected set
            for (String mentionedUser : mentionedUsers) {
                if (!mentionedUser.equals(author)) { //if mentions one self then dont add in 
                    followsGraph.get(author).add(mentionedUser.toLowerCase());
                }
            }
        }
        
        return followsGraph;/*this will return the graph to the testcases*/
    }
						    /**
						     * Find the people in a social network who have the greatest influence, in
						     * the sense that they have the most followers.
						     * 
						     * @param followsGraph
						     *            a social network (as defined above)
						     * @return a list of all distinct Twitter usernames in followsGraph, in
						     *         descending order of follower count.
						     */ 
	public static List<String> influencers(Map<String, Set<String>> followsGraph) {
        Map<String, Integer> followerCount = new HashMap<>();

        // Count followers for each user
        for (Set<String> followedUsers : followsGraph.values())/*has followsGraph for input*/
        {
            for (String followedUser : followedUsers) {
                followerCount.put(followedUser, followerCount.getOrDefault(followedUser, 0) + 1);//increment for each new user that is following this user
            }
        }
        // sorting the list so that the most popular is on top
        List<String> influencers = new ArrayList<>(followerCount.keySet());//unique usernames from the map have been obtained
        influencers.sort((user1, user2) -> followerCount.get(user2).compareTo(followerCount.get(user1)));//similar to insertion sort
        
        return influencers;//returns a list of all influencers sorted
    }

}
