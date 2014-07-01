package com.kpi.planner.entity.js.graph;

import com.kpi.planner.entity.Node;

import java.io.Serializable;

/**
 * Created by Artem
 * Date: 4/5/2014 1:58 PM.
 */
public class JNode implements Serializable {

    private transient Node node;

    public int id;
    public int fixed;
    public int index;
    public double px;
    public double x;
    public double py;
    public double y;
    public int nodeWeight;
    public int plinks;

    public JNode(int id, int fixed, int nodeWeight) {
        this.id = id;
        this.nodeWeight = nodeWeight;
        this.fixed = fixed;
    }

    @Override
    public String toString() {
        return "JNode{" +
                "node=" + node +
                ", id=" + id +
                ", fixed=" + fixed +
                ", index=" + index +
                ", px=" + px +
                ", x=" + x +
                ", py=" + py +
                ", y=" + y +
                ", nodeWeight=" + nodeWeight +
                ", plinks=" + plinks +
                '}';
    }

    public Node toNode(){
        if (node == null){
            node = new Node(id, nodeWeight, plinks);
        }
        return node;
    }
}
