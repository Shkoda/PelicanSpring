package com.kpi.planner.gantt;

import com.kpi.planner.entity.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Artem
 * Date: 5/24/2014 4:23 PM.
 */
public class Task {

    private final static Map<Node, Integer> finishTime = new HashMap<>();
    private final static Map<Node, Processor> dataStorage = new HashMap<>();

    public static void addNodeFinishTime(Node node, int time) {
        finishTime.put(node, time);
    }

    public static int getNodeFinishTime(Node node) {
        return finishTime.get(node);
    }

    public static void addDataStorage(Node node, Processor processor) {
        dataStorage.put(node, processor);
    }

    public static Processor getDataStorage(Node node) {
        return dataStorage.get(node);
    }

    public final int startTime;
    public int length;
    public int endTime;
    public final int nodeId;
    protected String text;

    public Task(int startTime, int length, int nodeId) {
        this.startTime = startTime;
        this.length = length;
        this.nodeId = nodeId;
        this.text = "T" + nodeId;
        this.endTime = startTime + length - 1;
    }

    public Task(int startTime, int length) {
        this.startTime = startTime;
        this.length = length;
        this.nodeId = -1;
        this.text = "-";
        this.endTime = startTime + length - 1;
    }

    public void prolong(int extraLength){
        this.endTime += extraLength;
        this.length += extraLength;
    }

    public boolean isEmpty(){
        return this.text.equals("-");
    }

    @Override
    public String toString() {
        return String.format("T[%d - %d, \'%s\']", startTime, endTime, text);
    }
}
