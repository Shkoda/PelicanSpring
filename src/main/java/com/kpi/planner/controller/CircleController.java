package com.kpi.planner.controller;

import com.kpi.planner.entity.response.Response;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * Created by Nightingale on 23.07.2014.
 */
@Controller
@RequestMapping("/circle")
public class CircleController {
    @ResponseBody
    @RequestMapping(value = "/get_csv", method = RequestMethod.GET)
    public Response testing() {
        System.out.println("in circle controller");

        try {
            InputStream stream = CircleController.class.getResourceAsStream("/json/cv.json");
            StringWriter writer = new StringWriter();

            IOUtils.copy(stream, writer);
            String csv = writer.toString();

            System.out.println(csv);

            return new Response(Response.Status.OK, csv);
        } catch (IOException e) {
            e.printStackTrace();
            return new Response(Response.Status.ERROR, "epic fail :: " + e);
        }
    }

}
