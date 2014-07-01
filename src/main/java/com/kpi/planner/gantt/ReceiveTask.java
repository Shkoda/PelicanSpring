package com.kpi.planner.gantt;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artem
 * Date: 5/29/2014 5:28 PM.
 */
public class ReceiveTask extends Task {

    public final int from;
    public final int to;
    private List<Integer> what = new ArrayList<>();

    public ReceiveTask(int startTime, int length, int what, int from, int to) {
        super(startTime, length, 0);
        this.what.add(what);
        this.from = from;
        this.to = to;
        this.text = to + " <- " + from + ", " + what;
    }

}
