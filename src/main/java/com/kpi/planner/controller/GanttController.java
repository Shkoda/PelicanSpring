package com.kpi.planner.controller;

import com.google.gson.Gson;
import com.kpi.planner.entity.Node;
import com.kpi.planner.entity.js.gantt.JGantt;
import com.kpi.planner.entity.js.graph.JGraph;
import com.kpi.planner.gantt.Gantt;
import com.kpi.planner.gantt.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by Artem
 * Date: 5/18/2014 4:46 PM.
 */
@Controller
@RequestMapping("/gantt")
public class GanttController {

    @Autowired
    private FileController fileController;

    @ResponseBody
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/last", method = RequestMethod.GET)
    public JGantt createGantt() {
        try {
            create(3);
            create(16);
            return create(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private JGantt create(int method) {
        Map<Integer, Node> nodes = new Gson().fromJson(fileController.getTasks("last"), JGraph.class).toNodes(true);
//        System.out.println(nodes);
        Map<Integer, Processor> processors = Processor.nodesToProcessors(
                new Gson().fromJson(fileController.getSystem("last"), JGraph.class).toNodes(false));
        Gantt gantt = new Gantt(nodes, processors);
        gantt.createRouting();
        long t = System.currentTimeMillis();
        gantt.create(method);
        System.out.println("time: "+ (System.currentTimeMillis() - t));
        gantt.calculateParameters();
        return gantt.getJGantt();
    }
}
