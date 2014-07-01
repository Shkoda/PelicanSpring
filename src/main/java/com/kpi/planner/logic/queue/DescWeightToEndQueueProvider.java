package com.kpi.planner.logic.queue;

import com.kpi.planner.entity.Node;
import com.kpi.planner.logic.AStarPathfinder;
import com.kpi.planner.utils.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Artem
 * Date: 4/17/2014 3:23 PM.
 */
public class DescWeightToEndQueueProvider extends QueueProvider {

    private AStarPathfinder pathfinder = new AStarPathfinder();

    protected DescWeightToEndQueueProvider(Map<Integer, Node> nodes) {
        super(nodes);
    }

    @Override
    protected List<Node> sortNodes(List<Node> nodes, List<Node> startNodes, List<Node> endNodes) {
//        Optional<Node> max = nodes.stream().max((n1, n2) -> Integer.compare(maxWeightWay(n1, endNodes), maxWeightWay(n2, endNodes)));
        return nodes.stream().sorted((n1, n2) -> -Integer.compare(maxWeightWay(n1, endNodes), maxWeightWay(n2, endNodes))).collect(Collectors.toList());
    }

    public int maxWeightWay(Node node, List<Node> endNodes){
        int result = endNodes.stream()
                .map(endNode -> pathfinder.findWay(node, endNode,  (c, n)-> n.weight, true))
                .filter(way -> way != null)
                .mapToInt(l -> l.stream().mapToInt(n -> n.weight).sum())
                .max().getAsInt();
        node.setValue(result);
        return result;
    }
}
