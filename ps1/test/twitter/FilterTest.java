/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    //no new tweets have been added
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    //already provided
    @Test
    public void testWrittenByMultipleTweetsSingleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");
        
        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
    }
    
    //already provided
    @Test
    public void testInTimespanMultipleTweetsMultipleResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
    }
    
  //already provided
    @Test
    public void testContaining() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
    }
    // no author has matched
    @Test
    public void testWrittenByNoMatch() {
        List<Tweet> result = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "someoneelse");
        assertEquals("expected no tweets", 0, result.size()); }

    @Test
    //in timespan 
    public void testInTimespan() {
        Instant start = Instant.parse("2016-02-17T09:00:00Z");//less that d1
        Instant end = Instant.parse("2016-02-17T12:00:00Z");//more than d2
        Timespan timespan = new Timespan(start, end);

        List<Tweet> result = Filter.inTimespan(Arrays.asList(tweet1, tweet2), timespan);

        assertEquals("expected two tweets in the timespan", 2, result.size());
        assertTrue("expected result to contain tweet1", result.contains(tweet1));
        assertTrue("expected result to contain tweet2", result.contains(tweet2));
    }
    @Test
    
    //not in time span
    public void testInTimespanNoMatch() {
        Instant start = Instant.parse("2016-02-17T12:00:00Z");
        Instant end = Instant.parse("2016-02-17T13:00:00Z");
        Timespan timespan = new Timespan(start, end);
/// the tweets 1,2 are both from T10,T11 hence they both will not lie in range
        List<Tweet> result = Filter.inTimespan(Arrays.asList(tweet1, tweet2), timespan);

        assertEquals("expected no tweets in the timespan", 0, result.size());
    }

    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */

}
