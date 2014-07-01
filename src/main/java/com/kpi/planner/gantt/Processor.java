package com.kpi.planner.gantt;

import com.kpi.planner.entity.Node;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Artem
 * Date: 5/24/2014 4:21 PM.
 */
public class Processor {

    public final int id;
    public final int power;
    public final List<Task> tasksQueue = new ArrayList<>();
    public final List<Integer> hasDataFor = new ArrayList<>();
    public final List<Processor> neighbours = new ArrayList<>();
    public final Map<Processor, Integer> linkWeights = new HashMap<>();
    public final boolean duplex;

    private final List<Link> links;


    public Processor(int id, int power, int neighbours, boolean duplex, int physicalLinks) {
        this.id = id;
        this.power = power;
        this.duplex = duplex;
        this.links = new ArrayList<>();
        for (int i = 0; i < physicalLinks; i++) {
            links.add(new Link());
        }
    }

    public int getNextReleaseTime() {
        if (tasksQueue.isEmpty()) {
            return 0;
        } else {
            Task task = tasksQueue.get(tasksQueue.size() - 1);
            return task.endTime + 1;
        }
    }

    public void addNeighbour(Processor processor, int linkWight) {
        if (!neighbours.contains(processor)){
            this.neighbours.add(processor);
            this.linkWeights.put(processor, linkWight);
        }
    }

    public void backup(){
        links.forEach(Link::backup);
    }

    public void restore(){
        links.forEach(Link::restore);
    }

    public void scheduleAt(int time, Node node) {
        Task task = new Task(time, (int) Math.ceil( (double)node.weight/ power),  node.id);
        this.tasksQueue.add(task);
        this.hasDataFor.add(node.id);
        Task.addNodeFinishTime(node, task.endTime);
        Task.addDataStorage(node, this);
    }

    public void scheduleWhenReleased(Node node) {
        Task task = new Task(getNextReleaseTime(), (int) Math.ceil((double)node.weight/ power), node.id);
        this.tasksQueue.add(task);
        this.hasDataFor.add(node.id);
        Task.addNodeFinishTime(node, task.endTime);
        Task.addDataStorage(node, this);
    }

    public List<Processor> getNeighbours() {
        return neighbours;
    }

    public static Map<Integer, Processor> nodesToProcessors(Map<Integer, Node> nodes) {
        Map<Integer, Processor> result = new HashMap<>();
        for (Node node : nodes.values()) {
            result.put(node.id, new Processor(node.id, node.weight, 3, true, node.getPlinks()));
        }
        for (Node node : nodes.values()) {
            Processor p1 = result.get(node.id);
            for (Node link : node.links) {
                Processor p2 = result.get(link.id);
                p1.addNeighbour(p2, node.getLinkWeight(p2.id));
                p2.addNeighbour(p1, link.getLinkWeight(p1.id));
            }
        }
        return result;
    }

    public void accumulateTasks(List<List<Task>> lists, List<String> headers) {
        lists.add(tasksQueue);
        headers.add("P"+id);
        for (int i = 0, linksSize = links.size(); i < linksSize; i++) {
            Link link = links.get(i);
            lists.add(link.getSend());
            lists.add(link.getReceive());
            headers.add("s" + i);
            headers.add("r" + i);
        }

    }

    public List<Node> getNodesWithMissingData(Node node) {
        return node.parents.stream().filter(parent -> !hasDataFor.contains(parent.id)).collect(Collectors.toList());
    }

    public boolean cpuIsFreeAt(int time) {
        return getNextReleaseTime() <= time;
    }


    public Link getFreeLinkForReceiveAtPeriod(int time, int duration, int source) {
        for (Link link : links) {
            if (link.receiveIsFreeAtPeriod(time, duration, source)) return link;
        }
        return null;
    }


    /**
     * @return time of the transaction finish
     */
    public int sendToProcessor(Node node, int startTime, Processor dst, int packetSize) {
        int t = startTime - 1;
        Link sendLink, receiveLink;
        int length = (int) Math.ceil((double)packetSize/ linkWeights.get(dst));

        do {
            t++;
            int timestamp = t;
            Optional<Link> min = links.stream()
                    .min((l1, l2) -> Integer.compare(l1.getNextSendingFrameAfterTime(timestamp, length, dst.id),
                                                     l2.getNextSendingFrameAfterTime(timestamp, length, dst.id)));
            sendLink = min.isPresent() ? min.get() : links.get(0);
            receiveLink = dst.getFreeLinkForReceiveAtPeriod(t, length, id);
        } while (receiveLink == null);

            Task sendTask = new SendTask(t, length, node.id, id, dst.id);
            Task dstTask = new ReceiveTask(t, length, node.id, id, dst.id);
            sendLink.scheduleSendTask(sendTask);
            dst.scheduleReceiveTask(dstTask, id);

        return t + length;
    }

    public void scheduleReceiveTask(Task task, int src) {
        for (Link link : links) {
            if (link.receiveIsFreeAtPeriod(task.startTime, task.length, src)){
                link.scheduleReceiveTask(task);
                return;
            }
        }
    }


    @Override
    public String toString() {
        return "P#" + id;
    }
}
