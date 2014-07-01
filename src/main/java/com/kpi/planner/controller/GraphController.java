package com.kpi.planner.controller;

import com.kpi.planner.entity.Node;
import com.kpi.planner.entity.js.graph.JGraph;
import com.kpi.planner.entity.response.Response;
import com.kpi.planner.utils.CyclesFinder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Artem
 * Date: 4/8/2014 3:18 PM.
 */
@Controller
@RequestMapping("/graph")
public class GraphController {

    private static Random random = new Random();

    @ResponseBody
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/cycles", method = RequestMethod.POST)
    public Response findCycles(@RequestBody JGraph graph) {
        Response response = null;
        try {
            System.out.println(graph);
            Map<Integer, Node> map = graph.toNodes(true);
            List<Integer>[] lists = new List[graph.maxNodeIndex() + 1];
            for (Map.Entry<Integer, Node> entry : map.entrySet()) {
                lists[entry.getKey()] = entry.getValue().links.stream().map(l -> l.id).collect(Collectors.toList());
            }
            for (int i = 0; i < lists.length; i++) if (lists[i] == null) lists[i] = new ArrayList<>();
            CyclesFinder finder = new CyclesFinder();
            List<List<Integer>> scc = finder.scc(lists);
            System.out.println("scc = " + scc);
            List<List<Integer>> cycles = scc.stream().filter(l -> l.size() > 1).collect(Collectors.toList());
            System.out.println("cycles = " + cycles);
            response = cycles.size() > 0 ? new Response(Response.Status.ERROR, cycles.toString()) : new Response(Response.Status.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @ResponseBody
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/connectivity", method = RequestMethod.POST)
    public Response findGroups(@RequestBody JGraph graph) {
        Response response = null;
        try {
            System.out.println(graph);
            Map<Integer, Node> map = graph.toNodes(false);
            List<List<Integer>> groups = new ArrayList<>();
            List<Node> nodes = new ArrayList<>(map.values());
            while (!nodes.isEmpty()) {
                List<Node> group = new ArrayList<>();
                addChildNodes(nodes.get(0), group);
                groups.add(group.stream().map(node -> node.id).collect(Collectors.toList()));
                nodes.removeAll(group);
            }
            response = groups.size() == 1 ?
                    new Response(Response.Status.OK) : new Response(Response.Status.ERROR, groups.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private void addChildNodes(Node node, List<Node> group) {
        if (!group.contains(node)) group.add(node);
        else return;
        for (Node link : node.links) {
            addChildNodes(link, group);
        }
    }

    @ResponseBody
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/generate", method = RequestMethod.GET)
    public JGraph generateGraph(@RequestParam("n") int n, @RequestParam("connectivity") double connectivity,
                                @RequestParam("min") int min, @RequestParam("max") int max) {
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            nodes.add(new Node(i, random.nextInt(max - min) + min));
        }
        double w = nodeWeightsSum(nodes);
        double l = (w - connectivity * w) / connectivity;
        System.out.println("target l = " + l);
        System.out.println("neighbours to add = " + Math.round(l));
        for (int i = 0; i < Math.round(l); i++) {
            Collections.shuffle(nodes);
            Node src = nodes.get(0);
            Node target = nodes.get(1);
            if (!src.hasParent(target)) {
                System.out.println(src+" has not parent "+ target);
                src.addLink(target);
            } else {
                System.out.println(src+" has parent "+ target);
                target.addLink(src);
            }
        }

        System.out.println("connectivity(nodes) = " + connectivity(nodes));
        return new JGraph(nodes);
    }

    private double nodeWeightsSum(List<Node> nodes) {
        return nodes.stream().mapToInt(n -> n.weight).sum();
    }

    private double connectivity(List<Node> nodes) {
        double w = nodes.stream().mapToInt(n -> n.weight).sum();
        double l = nodes.stream().flatMap(n -> n.linkWeights.values().stream()).mapToInt(n -> n).sum();
        System.out.println("w = " + w);
        System.out.println("l = " + l);
        return w / (w + l);
    }
}
