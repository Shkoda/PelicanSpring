package com.kpi.planner.logic;


import com.kpi.planner.entity.Node;
import com.kpi.planner.gantt.Processor;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created with IntelliJ IDEA.
 * User: Artem Korotenko
 * Date: 29.01.13
 * Time: 12:50
 */
public class AStarPathfinder {

    private final int MAX_STEPS = 5000;

    public List<Node> findWay(Node s, final Node d, BiFunction<Node,Node, Integer> f, boolean useWeight) {
        int counter = MAX_STEPS;
        final HashMap<Node, Node> comeFrom = new HashMap<>();
        final HashMap<Node, Double> gMap = new HashMap<>();
        final HashSet<Node> closedList = new HashSet<>();
        PriorityQueue<Node> openList = new PriorityQueue<>(25, (o1, o2) -> Double.compare(gMap.get(o1), gMap.get(o2)));
        gMap.put(s, useWeight? (double) s.weight : 0);
        openList.add(s);
        while (!openList.isEmpty()) {
            if (--counter == 0) {
                return null;
            }
            Node current = openList.remove();
            if (current.equals(d)) {
                return reconstructPath(comeFrom, s, d);
            }
            closedList.add(current);
            for (Node neighbour : current.links) {
                double g = gMap.get(current) + f.apply(current, neighbour);
                if (closedList.contains(neighbour)) {
                    if (gMap.get(neighbour) > g) {
                        gMap.put(neighbour, g);
                        comeFrom.put(neighbour, current);
                    }
                } else {
                    comeFrom.put(neighbour, current);
                    gMap.put(neighbour, gMap.get(current) + f.apply(current, neighbour));
                    if (!openList.contains(neighbour)) {
                        openList.add(neighbour);
                    }
                }
            }

        }
        return null;
    }


    private static <T> ArrayList<T> reconstructPath(HashMap<T, T> comeFrom, T s, T d) {
        final ArrayList<T> result = new ArrayList<>();
        T next = d;
        while (!next.equals(s)) {
            result.add(next);
            next = comeFrom.get(next);
        }
        result.add(next);
        Collections.reverse(result);
        return result;

    }

    public List<Processor> findWay(Processor s, final Processor d) {
        int counter = MAX_STEPS;
        final HashMap<Processor, Processor> comeFrom = new HashMap<>();
        final HashMap<Processor, Double> gMap = new HashMap<>();
        final HashSet<Processor> closedList = new HashSet<>();
        PriorityQueue<Processor> openList = new PriorityQueue<>(25, (o1, o2) -> Double.compare(gMap.get(o1), gMap.get(o2)));
        gMap.put(s, 0.0);
        openList.add(s);
        while (!openList.isEmpty()) {
            if (--counter == 0) {
                return null;
            }
            Processor current = openList.remove();
            if (current.equals(d)) {
                return reconstructPath(comeFrom, s, d);
            }
            closedList.add(current);
            for (Processor neighbour : current.neighbours) {
                double g = gMap.get(current) + 1;
                if (closedList.contains(neighbour)) {
                    if (gMap.get(neighbour) > g) {
                        gMap.put(neighbour, g);
                        comeFrom.put(neighbour, current);
                    }
                } else {
                    if (gMap.containsKey(neighbour)){
                        if (gMap.get(neighbour) > g) {
                            gMap.put(neighbour, g);
                            comeFrom.put(neighbour, current);
                        }
                    } else {
                        comeFrom.put(neighbour, current);
                        gMap.put(neighbour, gMap.get(current) + 1);
                    }
                    if (!openList.contains(neighbour)) {
                        openList.add(neighbour);
                    }
                }
            }

        }
        return null;
    }

}
