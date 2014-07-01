package com.kpi.planner.logic.queue;

import com.kpi.planner.entity.Node;
import com.kpi.planner.logic.AStarPathfinder;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Artem
 * Date: 4/17/2014 3:23 PM.
 */
public class NormalizedQueueProvider extends QueueProvider {

    private AStarPathfinder pathfinder = new AStarPathfinder();
    private Map<Node, Double> maxWeights;
    private Map<Node, Double> maxLengths;
    private int maxWeight;
    private int maxLength;

    protected NormalizedQueueProvider(Map<Integer, Node> nodes) {
        super(nodes);
    }

    @Override
    protected List<Node> sortNodes(List<Node> nodes, List<Node> startNodes, List<Node> endNodes) {
        maxWeights = new HashMap<>();
        maxLengths = new HashMap<>();
        maxWeight = nodes.stream().mapToInt(n -> maxWeightWay(n, endNodes)).max().getAsInt();
        maxLength = nodes.stream().mapToInt(n -> longestWay(n, endNodes)).max().getAsInt();
        return nodes.stream().sorted((n1, n2) -> -Double.compare(normalize(n1), normalize(n2))).collect(Collectors.toList());
    }

    private double normalize(Node node) {
        double nodeWeight = maxWeights.get(node);
        double nodeLength = maxLengths.get(node);
        double value = nodeWeight / maxWeight + nodeLength / maxLength;
        System.out.println(String.format("node #%d: [weight=%d(%5.3f), length=%d(%5.3f)]", node.id, (int) nodeWeight,
                                         nodeWeight / maxWeight,(int) nodeLength,  nodeLength / maxLength));
        node.setValue(value);
        return value;
    }

    private int maxWeightWay(Node node, List<Node> endNodes) {
        int result = endNodes.stream()
                .map(endNode -> pathfinder.findWay(node, endNode,  (c, n) -> n.weight, true))
                .filter(way -> way != null)
                .mapToInt(l -> l.stream().mapToInt(n -> n.weight).sum())
                .max().getAsInt();
        maxWeights.put(node, (double) result);
        return result;
    }

    private int longestWay(Node node, List<Node> endNodes) {
        int result = endNodes.stream()
                .map(endNode -> pathfinder.findWay(node, endNode,  (c, n) -> 1, false))
                .filter(way -> way != null)
                .mapToInt(l -> l.stream().mapToInt(n -> 1).sum())
                .max().getAsInt();
        maxLengths.put(node, (double) result);
        return result;
    }

}
