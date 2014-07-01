package com.kpi.planner.entity.js.graph;

import com.kpi.planner.entity.Node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Artem
 * Date: 4/5/2014 1:45 PM.
 */
public class JGraph implements Serializable {

    public final List<JNode> nodes;
    public final List<JConnection> links;
    public final int last;

    public JGraph(List<JNode> nodes, List<JConnection> links, int last) {
        this.nodes = nodes;
        this.links = links;
        this.last = last;
    }

    public JGraph(List<Node> nodes){
        this.nodes = nodes.stream().map(Node::toJNode).collect(Collectors.toList());
        this.links = new ArrayList<>();
        this.last = nodes.stream().mapToInt(n -> n.id).max().getAsInt();
        for (int i = 0, nodesSize = this.nodes.size(); i < nodesSize; i++) {
            JNode node = this.nodes.get(i);
            node.index = i;
            node.x = 250;
            node.px = 250;
            node.y = 250;
            node.py = 250;
        }

        for (Node node : nodes) {
            for (Node link : node.links) {
                int weight = node.linkWeights.get(link.id);
                if (link.id > node.id){
                    links.add(new JConnection(node.toJNode(), link.toJNode(), false, true, weight));
                } else {
                    links.add(new JConnection(link.toJNode(), node.toJNode(), true, false, weight));
                }
            }
        }

    }

    public List<JNode> getNodes() {
        return nodes;
    }

    public List<JConnection> getLinks() {
        return links;
    }

    public int maxNodeIndex(){
      return nodes.stream().max( (k, l) -> Integer.compare(k.id, l.id)).get().id;
    }

    public Map<Integer, Node> toNodes(boolean oriented) {
        Map<Integer, Node> result = new HashMap<>();
        nodes.stream().forEach(n -> result.put(n.id, n.toNode()));
        for (JConnection link : links) {
            Node source = result.get(link.source.id);
            Node target = result.get(link.target.id);
            if (oriented){
                if (link.left) {
                    target.addLink(source, link.linkWeight);
//                    source.addParent(target);
                } else {
                    source.addLink(target, link.linkWeight);
//                    target.addParent(source);
                }
            } else {
                target.addLink(source, link.linkWeight);
                source.addLink(target, link.linkWeight);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "JGraph{" +
                "nodes=" + nodes +
                ", neighbours=" + links +
                '}';
    }
}
