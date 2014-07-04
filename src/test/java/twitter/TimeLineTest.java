package twitter;

import org.junit.Before;
import org.junit.Test;
import twitter4j.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Nightingale on 01.07.2014.
 */
public class TimeLineTest {
    private static final String USER = "ShkodaAleceya";
    private static final Twitter twitter = new TwitterFactory().getInstance();

    private static String HASH_TAG_PATTERN = "#\\w+";
    Pattern p;

    @Before
    public void setUp() throws Exception {
    p= Pattern.compile(HASH_TAG_PATTERN);


    }

    @Test
    public void testTwitter() throws Exception {
        try {

            List<Status> statuses = twitter.getUserTimeline(USER);
            statuses.get(0).getText();


            System.out.println("Showing @" + USER + "'s user timeline.");
            for (Status status : statuses) {
                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
                printTags(status.getText());
            }

            System.out.println();
            long cursor = -1;
            IDs ids;
            System.out.println("Listing following ids.");
            do {

                ids = twitter.getFriendsIDs(USER, cursor);

                for (long id : ids.getIDs()) {
                    try {
                        getTimeLine(id);
                    } catch (Exception ignored) {

                    }

                }
            } while ((cursor = ids.getNextCursor()) != 0);

        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }

    }

    private void printTags(String message){
        Matcher m = p.matcher(message);
        while (m.find()) {
            System.out.println(" -- tag -- "+ m.group());
        }
    }

    private void getTimeLine(long id) throws TwitterException {
        List<Status> statuses = twitter.getUserTimeline(id);

        System.out.println("Showing @" + USER + "'s user timeline.");
        for (Status status : statuses) {
            System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
            printTags(status.getText());
        }

    }
}
