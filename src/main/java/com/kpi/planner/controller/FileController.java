package com.kpi.planner.controller;

import com.kpi.planner.entity.response.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Artem
 * Date: 4/10/2014 7:11 PM.
 */
@Controller
@RequestMapping("/file")
public class FileController {

    private static Map<String, String> tasks = new HashMap<>();
    private static Map<String, String> taskImages = new HashMap<>();
    private static Map<String, String> systems = new HashMap<>();
    private static Map<String, String> systemImages = new HashMap<>();

    public  String getTasks(String name){
           return tasks.get(name);
    }

    public  String getSystem(String name){
        return systems.get(name);
    }

    @ResponseBody
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String randomFileName() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/{type}/list", method = RequestMethod.GET)
    public Set<String> listFiles(@PathVariable String type) {
        if (type.equals("tasks")) {
            return tasks.keySet();
        } else
            return systems.keySet();
    }

    @ResponseBody
    @RequestMapping(value = "/{name}/{type}/save", method = RequestMethod.POST)
    public Response saveFile(@PathVariable String name, @PathVariable String type, @RequestBody String file) {
        if (type.equals("tasks")) {
            System.out.println("saving tasks "+name+"...");
            tasks.put(name, file);
        } else if (type.equals("systems")) {
            System.out.println("saving system "+name+"...");
            systems.put(name, file);
        }
        return new Response(Response.Status.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/{name}/{type}/save/image", method = RequestMethod.POST)
    public Response saveImage(@PathVariable String name, @PathVariable String type, @RequestBody String file) {
        if (type.equals("tasks")) {
            System.out.println("saving image tasks "+name+"...");
            taskImages.put(name, file);
        } else if (type.equals("systems")) {
            System.out.println("saving image system "+name+"...");
            systemImages.put(name, file);
        }
        return new Response(Response.Status.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/{name}/{type}/load", method = RequestMethod.GET)
    public String loadFile(@PathVariable String name, @PathVariable String type) {
        System.out.println("name = " + name + ", type = " + type);
        if (type.equals("tasks")) {
            return tasks.get(name);
        } else
            return systems.get(name);

    }

    @ResponseBody
    @RequestMapping(value = "/{name}/{type}/load/image", method = RequestMethod.GET)
    public String loadImage(@PathVariable String name, @PathVariable String type, @RequestBody String file) {
        System.out.println("name = " + name + ", type = " + type+", image = "+file);
        if (type.equals("tasks")) {
            return taskImages.get(name);
        } else
            return systemImages.get(name);

    }
}
