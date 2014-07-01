package com.kpi.planner.entity;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kpi.planner.entity.js.graph.JNode;
import com.kpi.planner.gantt.Task;

/**
 * Created by Artem
 * Date: 4/5/2014 3:26 PM.
 */
public class Node {

    public final int id;
    public final int weight;
    public final List<Node> links = new ArrayList<>();
    public final Map<Integer, Integer> linkWeights = new HashMap<>();
    public final List<Node> parents = new ArrayList<>();

    private double value;
    public int plinks;

    public Node(int id, int weight) {
        this.id = id;
        this.weight = weight;
    }

    public Node(int id, int weight, int plinks) {
        this.id = id;
        this.weight = weight;
        this.plinks = plinks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;

        Node node = (Node) o;

        if (id != node.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public void addLink(Node node) {
        if (links.contains(node)){
            linkWeights.put(node.id, linkWeights.get(node.id) + 1);
        } else {
            links.add(node);
            linkWeights.put(node.id, 1);
            node.addParent(this);
        }
    }

    public int getLinkWeight(int id){
        return linkWeights.get(id);
    }

    public void addLink(Node node, int weight) {
        if (!links.contains(node)){
            links.add(node);
            linkWeights.put(node.id, weight);
            node.addParent(this);
        } else {
            links.add(node);
            linkWeights.put(node.id, 1);
            node.addParent(this);
        }
    }

    public void addParent(Node node) {
        parents.add(node);
    }

    public boolean hasParent(Node node){
        if (parents.isEmpty()) return false;
        if (parents.contains(node)) return true;
        for (Node parent : parents) {
            if (parent.hasParent(node)) return true;
        }
        return false;
    }


    //    @Override
//    public String toString() {
//        return "[Node #" + id + ", weight=" + weight + ", neighbours to:" +
//                neighbours.stream().map(l -> l.id).collect(Collectors.toList()).toString() + "]";
//    }


    public double getValue() {
        return value;
    }

    public int getPlinks() {
        return plinks;
    }

    public void setPlinks(int plinks) {
        this.plinks = plinks;
    }

    public JNode toJNode(){
        return new JNode(id, 0, weight);
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getMinStartTime(){

        return parents.isEmpty()? 0 : parents.stream().mapToInt(Task::getNodeFinishTime).max().getAsInt() + 1;
    }

    @Override
    public String toString() {
        return String.format("#%d(%5.2f, %d)", id, value, plinks);
    }

}
