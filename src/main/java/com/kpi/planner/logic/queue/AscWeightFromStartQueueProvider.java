package com.kpi.planner.logic.queue;

import com.kpi.planner.entity.Node;
import com.kpi.planner.logic.AStarPathfinder;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Artem
 * Date: 4/17/2014 3:23 PM.
 */
public class AscWeightFromStartQueueProvider extends QueueProvider {

    private AStarPathfinder pathfinder = new AStarPathfinder();

    protected AscWeightFromStartQueueProvider(Map<Integer, Node> nodes) {
        super(nodes);
    }

    @Override
    protected List<Node> sortNodes(List<Node> nodes, List<Node> startNodes, List<Node> endNodes) {
        return nodes.stream().sorted((n1, n2) -> Integer.compare(minWeightWay(n1, startNodes), minWeightWay(n2, startNodes))).collect(Collectors.toList());
    }

    private int minWeightWay(Node node, List<Node> startNodes) {
        int result = startNodes.stream()
                .map(startNode -> pathfinder.findWay(startNode, node, (c, n) -> n.weight, true))
                .filter(way -> way != null)
                .mapToInt(l -> l.stream().mapToInt(n -> n.weight).sum() - l.get(l.size() - 1).weight) //sum weight except last node
                .max().getAsInt();
        node.setValue(result);
        return result;
    }
}
