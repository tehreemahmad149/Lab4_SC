/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.time.Instant;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
        assert tweets != null;
        
        List<Tweet> sortedTweets = sortByTimestamp(tweets);
        
        
        Instant start = sortedTweets.get(0).getTimestamp();//the start of the time
        Instant end = sortedTweets.get(sortedTweets.size() -1).getTimestamp();//thius will be the laterst time
        
        return new Timespan(start, end);
    }
    
    private static List<Tweet> sortByTimestamp(List<Tweet> tweets) {
        assert tweets != null;
        
        
        
        return tweets.stream()
                .sorted(Comparator.comparing(Tweet::getTimestamp))
                .collect(Collectors.toList());
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */    
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        Set<String> mentionedUsers = new HashSet<>();
        
        for (Tweet tweet : tweets) {
        	
            String[] tokens = tweet.getText().split("\\s+");  // tweet is being split with the use of the whitespace so that each word is spearated
            
            
            
            for (String token : tokens) {
                if (token.startsWith("@") && token.length() > 1) {
                	
                    String username = token.substring(1);  
                    mentionedUsers.add(username);//the mentioned user is added to the set
                }
            }
        }
        
        
        
        
        return mentionedUsers;
    }

}