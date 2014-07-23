package com.kpi.planner.twitter;

import com.kpi.planner.utils.JsonTwitterUtils;
import twitter4j.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Nightingale on 03.07.2014.
 */
public class TwitterUtils {
    private static Twitter twitter;
    private static Pattern hasTagPattern;

    static {
        twitter = new TwitterFactory().getInstance();
        hasTagPattern = Pattern.compile("#\\w+");
    }

    public static Map<String, List<Status>> getHashTagMap(String userName) throws TwitterException {
        Map<String, List<Status>> tagMap = new ConcurrentHashMap<>();
        long cursor = -1;
        IDs ids;
        do {
            ids = twitter.getFriendsIDs(userName, cursor);
            for (long id : ids.getIDs()) {
                try {
                    twitter.getUserTimeline(id).parallelStream()
                            .forEach(s -> {
                                Matcher m = hasTagPattern.matcher(s.getText());
                                while (m.find()) {
                                    String tag = m.group();
                                    tagMap.putIfAbsent(tag, new CopyOnWriteArrayList<>());
                                    tagMap.get(tag).add(s);
                                    System.out.println(tag);
                                }

                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } while ((cursor = ids.getNextCursor()) != 0);
        return tagMap;
    }

    public static void main(String[] args) throws TwitterException {
        String name = "ShkodaAleceya";
        Map<String, List<Status>> map = getHashTagMap(name);
        System.out.println(map);


        System.out.println(JsonTwitterUtils.toJSON(name, map));
    }
}
