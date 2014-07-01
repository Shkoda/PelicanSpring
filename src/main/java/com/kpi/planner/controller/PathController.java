package com.kpi.planner.controller;

import com.kpi.planner.entity.Node;
import com.kpi.planner.entity.js.graph.JGraph;
import com.kpi.planner.entity.response.Response;
import com.kpi.planner.logic.queue.QueueProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Artem
 * Date: 4/16/2014 5:00 PM.
 */
@Controller
@RequestMapping("/path")
public class PathController {

    @ResponseBody
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/method/{id}", method = RequestMethod.POST)
    public Response findCycles(@RequestBody JGraph graph, @PathVariable int id) {
        Response response;
        try {
            Map<Integer, Node> nodes = graph.toNodes(true);
            QueueProvider queueProvider = QueueProvider.method(id, nodes);
            List<Node> queue = queueProvider.getQueueWithoutLinks();
            System.out.println("queue = " + queue);
            response = new Response(Response.Status.OK, queue.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response(Response.Status.INTERNAL_ERROR);
        }
        return response;
    }

}
