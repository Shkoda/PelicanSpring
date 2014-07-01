package com.kpi.planner.controller;

import com.kpi.planner.entity.response.Response;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Nightingale on 30.06.2014.
 */
@Controller
@RequestMapping("/tree")
public class TreeController {

    @ResponseBody
    @RequestMapping(value = "/get_json", method = RequestMethod.GET)
    public Response testing() {
        System.out.println("in tree controller");

        try(InputStream inputStream =  getClass().getResourceAsStream("/json/test.json")) {
            String everything = IOUtils.toString(inputStream);
            everything=everything.replaceAll("\n", "");
            everything=everything.replaceAll("\r", "");
//            everything="'"+everything+"'";
//
//            System.out.println(everything);
            return new Response(Response.Status.OK, everything);

        } catch (IOException e) {
            e.printStackTrace();
        }

      return null;
    }

}
