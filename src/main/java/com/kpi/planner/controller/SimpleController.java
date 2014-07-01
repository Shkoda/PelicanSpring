package com.kpi.planner.controller;

import com.kpi.planner.entity.response.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Nightingale on 14.06.2014.
 */
@Controller
@RequestMapping("/example")
public class SimpleController {

    @ResponseBody
    @RequestMapping(value = "/alloha", method = RequestMethod.GET)
    public Response testing() {

        System.out.println("i'm in alloha");
        if (Math.random()>0.5)
        return new Response(Response.Status.OK, "ababagalamaga");
        else
            return new Response(Response.Status.ERROR, "sad but true");
    }
}
