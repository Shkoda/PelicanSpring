package com.kpi.planner.controller;

import com.kpi.planner.entity.js.graph.JGraph;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class WebController {

    @RequestMapping(value = {"/tasks"}, method = RequestMethod.GET)
	public String tasksPage(ModelMap model) {
		model.addAttribute("message", "Hello world!");
		return "tasks";
	}


    @RequestMapping(value = {"/example"}, method = RequestMethod.GET)
    public String examplePage(ModelMap model){
        return "example";
    }


    @RequestMapping(value = "/system", method = RequestMethod.GET)
    public String systemPage(ModelMap model) {
        model.addAttribute("message", "Hello world!");
        return "system";
    }


    @RequestMapping(value = "/gantt", method = RequestMethod.GET)
    public String ganttPage() {
        return "gantt";
    }

    @RequestMapping(value = {"/tree", "/"}, method = RequestMethod.GET)
    public String treePage(){
        System.out.println("in WebController.treePage");
        return "tree";
    }


    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public  Integer test(@RequestBody JGraph graph) {
        System.out.println(graph);
        return 1;
    }

}