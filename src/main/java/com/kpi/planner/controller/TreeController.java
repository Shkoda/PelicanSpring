package com.kpi.planner.controller;

import com.kpi.planner.entity.response.Response;
import com.kpi.planner.twitter.TwitterUtils;
import com.kpi.planner.utils.JsonTwitterUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import twitter4j.Status;
import twitter4j.TwitterException;

import java.util.List;
import java.util.Map;

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

        String name = "ShkodaAleceya";
        String json = "\"name\":\"mybe smth is wrong\"";

        Map<String, List<Status>> map = null;
        try {
            map = TwitterUtils.getHashTagMap(name);
            json = JsonTwitterUtils.toJSON(name, map);

        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return new Response(Response.Status.OK, json);

//        try(InputStream inputStream =  getClass().getResourceAsStream("/json/test.json")) {
//            String everything = IOUtils.toString(inputStream);
//            everything=everything.replaceAll("\n", "");
//            everything=everything.replaceAll("\r", "");
////            everything="'"+everything+"'";
////
////            System.out.println(everything);
//            return new Response(Response.Status.OK, everything);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//      return null;
    }

}
