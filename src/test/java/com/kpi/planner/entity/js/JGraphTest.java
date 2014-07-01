package com.kpi.planner.entity.js;

import com.google.gson.Gson;
import com.kpi.planner.entity.Node;
import com.kpi.planner.entity.js.graph.JGraph;

import java.util.ArrayList;
import java.util.List;

public class JGraphTest {

    @org.junit.Test
    public void testNodes() throws Exception {
        List<Node> nodeList = new ArrayList<>();
        nodeList.add(new Node(0, 5));
        nodeList.add(new Node(1, 3));
        nodeList.add(new Node(2, 4));
        nodeList.add(new Node(3, 6));
        nodeList.add(new Node(4, 7));
        nodeList.add(new Node(5, 8));
        JGraph graph = new JGraph(nodeList);
        System.out.println(new Gson().toJson(graph));
    }
}