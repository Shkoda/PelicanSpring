package com.kpi.planner.logic.queue;

import com.kpi.planner.entity.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Artem
 * Date: 4/16/2014 7:41 PM.
 */
public abstract class QueueProvider {

    public static QueueProvider method(int number, Map<Integer, Node> nodes) {
        switch (number) {
            default:
            case 1:
                return new NormalizedQueueProvider(nodes);
            case 3:
                return new DescWeightToEndQueueProvider(nodes);
            case 16:
                return new AscWeightFromStartQueueProvider(nodes);
        }

    }

    private Map<Integer, Node> nodes;

    protected QueueProvider(Map<Integer, Node> nodes) {
        this.nodes = nodes;
    }

    abstract protected List<Node> sortNodes(List<Node> nodes, List<Node> startNodes, List<Node> endNodes);

//    public List<Node> getQueue() {
//        List<Node> result = new ArrayList<>();
//        List<Node> startNodes = nodes.values().stream().filter(n -> n.parents.isEmpty()).collect(Collectors.toList());
//        List<Node> endNodes = nodes.values().stream().filter(n -> n.neighbours.isEmpty()).collect(Collectors.toList());
//        List<Node> openNodes = new ArrayList<>(startNodes);
//        List<Node> closedNodes = new ArrayList<>();
//
//        while (!openNodes.isEmpty()) {
//            Node node = chooseNode(openNodes, startNodes, endNodes);
//            result.add(node);
//            openNodes.remove(node);
//            closedNodes.add(node);
//            node.neighbours.stream()
//                    .filter(link -> closedNodes.containsAll(link.parents))
//                    .forEach(openNodes::add);
//        }
//        return result;
//    }

    public List<Node> getQueueWithoutLinks() {
        List<Node> startNodes = nodes.values().stream().filter(n -> n.parents.isEmpty()).collect(Collectors.toList());
        List<Node> endNodes = nodes.values().stream().filter(n -> n.links.isEmpty()).collect(Collectors.toList());
        List<Node> openNodes = new ArrayList<>(nodes.values());
        return sortNodes(openNodes, startNodes, endNodes);
    }

    public static int getCriticalWayWeight(Map<Integer, Node> nodes){
        DescWeightToEndQueueProvider provider = new DescWeightToEndQueueProvider(nodes);
        List<Node> endNodes = nodes.values().stream().filter(n -> n.links.isEmpty()).collect(Collectors.toList());
        Node node = provider.getQueueWithoutLinks().get(0);
        return provider.maxWeightWay(node, endNodes);
    }
}
