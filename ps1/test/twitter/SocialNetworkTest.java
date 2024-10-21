/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class SocialNetworkTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    /*test for empty, single, and multiple mentions*/
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());

        /*no tweets*/
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }
    @Test
    public void testGuessFollowsGraphNoMentions() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        List<Tweet> tweets = new ArrayList<>();
        /*dummy list has been made for test which has no mentions*/
        tweets.add(new Tweet(1, "alice", "Hello!", Instant.now())); 
        System.out.println("Follows graph: " + followsGraph);
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }
    @Test
    //failed once
    public void testGuessFollowsGraphOne() {
        List<Tweet> tweets = new ArrayList<>();
        tweets.add(new Tweet(1, "alice", "Hello @bob!", Instant.now())); /*dummy list has been made for test*/
        
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
        /*ensuring that users are linked*/
        assertTrue("expected one node", followsGraph.containsKey("alice"));
        /*validating that the users are linked by asserting the link*/
        //error in line below
        System.out.println("Follows graph: " + followsGraph);

        assertTrue("alice should follow bob", followsGraph.get("alice").contains("bob".toLowerCase()));  // alice follows bob

    }

    @Test
    //failed once
    public void testGuessFollowsGraphMultiple() {
        List<Tweet> tweets = new ArrayList<>();
        /*mapped on test case 5 in the lab manual where one author has multiple tweets and multiple mentions*/
        tweets.add(new Tweet(1, "alice", "Hello @bob", Instant.now()));
        tweets.add(new Tweet(2, "bob", "Hi @charlie and @david", Instant.now()));
        tweets.add(new Tweet(3, "bob", "Hi @nick and @ben", Instant.now()));
        
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
        /*confirmation of connections between nodes so that the linkage can be seen*/
      //error in line below
        //System.out.println("Follows graph: " + followsGraph);

        assertTrue("expected alice to follow bob", followsGraph.get("alice").contains("bob"));
        assertTrue("expected bob to follow charlie", followsGraph.get("bob").contains("charlie"));
        assertTrue("expected bob to follow david", followsGraph.get("bob").contains("david"));
        assertTrue("expected bob to follow nick", followsGraph.get("bob").contains("nick"));
        assertTrue("expected bob to follow ben", followsGraph.get("bob").contains("ben"));
    }
    
    @Test
    /*similar to the code above*/
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        /*when empty graph no influencers*/
        assertTrue("expected empty list", influencers.isEmpty());
    }
    @Test
    /*one user with no followers == empty list*/
    public void testInfluencersS() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("user1", new HashSet<>(Arrays.asList()));/*one user with 0 followers*/
        
        List<String> influencers = SocialNetwork.influencers(followsGraph);/*list of influencer sorted after running function influencer*/
        assertTrue("expected empty list", influencers.isEmpty());
    }
    @Test
    /*one influencer returned*/
    public void testInfluencersSingleReturn() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("user1", new HashSet<>(Arrays.asList("user2")));/*one user has some followers*/
        
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        assertEquals("user2", influencers.get(0));/*user 1 will be at the top*/
    }
    @Test
    /*multiple influencer and a sorted list with the most popular at the top*/
    public void testInfluencersMultiple() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        /*multiple users have different followers so one will be most popular*/
        followsGraph.put("user1", new HashSet<>(Arrays.asList("user2", "user3")));
        followsGraph.put("user2", new HashSet<>(Arrays.asList("user3")));
        
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        assertEquals("user3", influencers.get(0));
    }
    /*if equal number of followers than return them in any order*/
    @Test
    //failed once
    public void testInfluencersMultipleWithSameFollowers() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("user1", new HashSet<>(Arrays.asList("user4", "user3")));
        
        followsGraph.put("user2", new HashSet<>(Arrays.asList("user3", "user4")));
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        /*assert true contains as they can be returned in any order so using the get() method will not work*/
      //error in line below
        assertTrue(influencers.contains("user3"));
        assertTrue(influencers.contains("user4"));
        //both have to be returned as same number of users
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}
