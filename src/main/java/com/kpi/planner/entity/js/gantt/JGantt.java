package com.kpi.planner.entity.js.gantt;

import com.kpi.planner.gantt.Task;

import java.util.List;

/**
 * Created by Artem
 * Date: 5/24/2014 5:14 PM.
 */
public class JGantt {

    private int length;
    private List<List<Task>> tasks;
    private List<String> headers;


    public JGantt(int length, List<List<Task>> tasks, List<String> headers) {
        this.length = length;
        this.tasks = tasks;
        this.headers = headers;
    }
}
