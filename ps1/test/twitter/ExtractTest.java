package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import org.junit.Test;

public class ExtractTest {

    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "mochimmochi", "lelelelle @mochi", d2);
    private static final Tweet tweet4 = new Tweet(4, "chuchu", "salar mahir jihan faris @mochi, @meowshi", d2);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testGetTimespan_OneTweet() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));
        
        assertEquals("Expected start == end", timespan.getStart(), timespan.getEnd());
        assertEquals("Expected start == d1", timespan.getStart(), d1);
    }

    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("Expected start", d1, timespan.getStart());
        assertEquals("Expected end", d2, timespan.getEnd());
    }

    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("Expected empty set", mentionedUsers.isEmpty());
    }

    @Test
    public void testGetMentionedUsers_OneTweetOneMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3)); // tweet3 has one mention (@mochi)

        Set<String> expected = new HashSet<>(Arrays.asList("mochi"));
        assertEquals("Expected one mention", expected, mentionedUsers);
    }

}
