package com.kpi.planner.utils;

import com.google.gson.Gson;
import twitter4j.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Nightingale on 04.07.2014.
 */
public class JsonTwitterUtils {
    public static String toJSONString(String rootUser, Map<String, List<Status>> tagMap) {
        List<TwitJSON> children = tagMap.entrySet()
                .parallelStream()
                .map(TwitJSON::new)
                .collect(Collectors.toList());
        return new Gson().toJson(new TwitJSON(rootUser, children));
    }

    public static class TwitJSON {
        public final String name;
        public final List<TwitJSON> children;

        public TwitJSON(String name, List<TwitJSON> children) {
            this.name = name;
            this.children = children;
        }

        public TwitJSON(Map.Entry<String, List<Status>> tagMapEntry) {
            this.name = tagMapEntry.getKey();
            children = tagMapEntry.getValue()
                    .parallelStream()
                    .map(TwitJSON::new)
                    .collect(Collectors.toList());
        }

        public TwitJSON(Status status) {
            this.name = "@" + status.getUser().getName();
            children = new ArrayList<>();
            children.add(new TwitJSON(status.getText()));
        }

        public TwitJSON(String name) {
            this.name = name;
            this.children =null;
        }

        @Override
        public String toString() {
            return "TwitJSON{" +
                    "name='" + name + '\'' +
                    ", children=" + children +
                    '}';
        }
    }


}
