package com.kpi.planner.gantt;

import com.kpi.planner.entity.Node;
import com.kpi.planner.entity.js.gantt.JGantt;
import com.kpi.planner.logic.AStarPathfinder;
import com.kpi.planner.logic.queue.QueueProvider;
import com.kpi.planner.utils.Tuple;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ARTEM on 5/25/2014.
 */
public class Gantt {

    private Map<Integer, Node> nodes;
    private Map<Integer, Processor> processors;

    private List<Node> openNodes = new ArrayList<>();
    private List<Node> closedNodes = new ArrayList<>();

    private Map<Tuple<Integer, Integer>, List<Processor>> routes = new HashMap<>();

    public Gantt(Map<Integer, Node> nodes, Map<Integer, Processor> processors) {
        this.nodes = nodes;
        this.processors = processors;
    }

    public void createRouting() {
        AStarPathfinder pathfinder = new AStarPathfinder();
        for (Processor src : processors.values()) {
            for (Processor dst : processors.values()) {
                routes.put(new Tuple<>(src.id, dst.id), pathfinder.findWay(src, dst));
            }
        }
    }

    public JGantt create(int queue) {
        List<Node> firstCircle = nodes.values().stream().filter(n -> n.parents.size() == 0).collect(Collectors.toList());
        List<Node> sortedFirstCircle = QueueProvider.method(queue, nodes).getQueueWithoutLinks().stream().filter(
                firstCircle::contains).collect(Collectors.toList());
        List<Processor> procQueue = sortProcessors(processors.values());
        // scheduling
        // work on first circle
        int pointer = 0;
        for (Node node : sortedFirstCircle) {
            processNode(node);
            Processor processor = procQueue.get((pointer++) % procQueue.size());
            processor.scheduleWhenReleased(node);
//            System.out.println("node #" + node.id + " scheduled to " + processor);
        }

        //work on other
        while (!openNodes.isEmpty()) {
            Node node = chooseNextNode(openNodes);
            System.out.println("///////////////////");
            System.out.println("next node: " + node);
            processNode(node);
            System.out.println("node min start time = " + node.getMinStartTime());
            //find free processors
            List<Processor> freeProcessors = procQueue.stream().filter(p -> p.cpuIsFreeAt(node.getMinStartTime())).collect(Collectors.toList());
            System.out.println("freeProcessors = " + freeProcessors);
            //choose src
            Processor chosenProcessor;
            if (freeProcessors.isEmpty()) {
                chosenProcessor = processors.values().stream().min(
                        (p1, p2) -> Integer.compare(p1.getNextReleaseTime(), p2.getNextReleaseTime())).get();
            } else {
                chosenProcessor = sortProcessors(freeProcessors).get(0); //choose src with maximum weight
            }
            System.out.println("chosen proc: " + chosenProcessor);
            //transfer data if needed
            List<Node> missingData = chosenProcessor.getNodesWithMissingData(node);
            if (missingData.isEmpty()) {
                System.out.println("missing data empty");
                chosenProcessor.scheduleWhenReleased(node);
            } else {
                System.out.println("missing data: " + missingData);
                List<Integer> dataArriveTime = new ArrayList<>();
                for (Node missingNode : missingData) {
                    Processor dataStorage = Task.getDataStorage(missingNode);
                    List<Processor> route = routes.get(new Tuple<>(dataStorage.id, chosenProcessor.id));
                    int finishTime = Task.getNodeFinishTime(missingNode) + 1;
                    for (int i = 0; i < route.size() - 1; i++) {
                        finishTime = route.get(i).sendToProcessor(missingNode, finishTime, route.get(i + 1), 5);
                        System.out.println(String.format("arrive time of %s to %s is %d", missingNode.toString(), chosenProcessor.toString(), finishTime));
                    }
//                    int arriveTime = dataStorage.sendToProcessor(missingNode, finishTime + 1, chosenProcessor);
//                    System.out.println(String.format("arrive time of %s to %s is %d", missingNode.toString(),chosenProcessor.toString(), arriveTime));
                    dataArriveTime.add(finishTime);
                }
                int time = dataArriveTime.stream().mapToInt(i -> i).max().getAsInt();
                System.out.println(String.format("node %s start time is %d", node.toString(), time));
                chosenProcessor.scheduleAt(time, node);
            }


        }
        //  convert to JGantt form
        return getJGantt();
    }

    public void calculateParameters(){
        List<List<Task>> tasks = new ArrayList<>();
        List<String> headers = new ArrayList<>();
        List<Processor> sortedProcessors = processors.values().stream().sorted(
                (p1, p2) -> Integer.compare(p1.id, p2.id)).collect(Collectors.toList());
        for (Processor sortedProcessor : sortedProcessors) {
            sortedProcessor.accumulateTasks(tasks, headers);
        }
        int length = tasks.stream().mapToInt(l -> l.isEmpty() ? 0 : l.get(l.size() - 1).endTime).max().getAsInt();
        System.out.println("length = " + length);
        int sum = tasks.stream().flatMap(t -> t.stream())
                .filter(t -> (!(t instanceof SendTask) && !(t instanceof ReceiveTask)&& !(t.isEmpty())))
                .mapToInt(t -> t.length).sum();
        System.out.println("sum = " + sum);
        double accelerationCoef = (double)sum / length;
        double efiCoef =  accelerationCoef / processors.size();
        double algEfiCoef =(double) QueueProvider.getCriticalWayWeight(nodes) / length;
        System.out.println(String.format("%.2f\t%.2f\t%.2f", accelerationCoef, efiCoef, algEfiCoef));
    }

    public void createOptimized(int queue) {
        List<Node> firstCircle = nodes.values().stream().filter(n -> n.parents.size() == 0).collect(Collectors.toList());
        List<Node> sortedFirstCircle = QueueProvider.method(queue, nodes).getQueueWithoutLinks().stream().filter(
                firstCircle::contains).collect(Collectors.toList());
        List<Processor> procQueue = sortProcessors(processors.values());
        // scheduling
        // work on first circle
        int pointer = 0;
        for (Node node : sortedFirstCircle) {
            processNode(node);
            Processor processor = procQueue.get((pointer++) % procQueue.size());
            processor.scheduleWhenReleased(node);
//            System.out.println("node #" + node.id + " scheduled to " + processor);
        }
        //work on other
        while (!openNodes.isEmpty()) {
            Node node = chooseNextNode(openNodes);
//            System.out.println("///////////////////");
//            System.out.println("next node: " + node);
            processNode(node);
            int startTime = node.getMinStartTime();
//            System.out.println("node min start time = " + startTime);
            //find free processors

            Processor chosenProcessor = procQueue.get(0);
            int tmin = getNodeFinishTimeOnProcessor(chosenProcessor, node, startTime);
            for (int j = 1, procQueueSize = procQueue.size(); j < procQueueSize; j++) {
                int tn = getNodeFinishTimeOnProcessor(procQueue.get(j), node, startTime);
                if (tn < tmin) {
                    tmin = tn;
                    chosenProcessor = procQueue.get(j);
//                    System.out.println("new chosen processor: " + chosenProcessor + " with t = " + tmin);
                }
            }

            //transfer data if needed
            List<Node> missingData = chosenProcessor.getNodesWithMissingData(node);
            if (missingData.isEmpty()) {
//                System.out.println("missing data empty");
                chosenProcessor.scheduleWhenReleased(node);
            } else {
//                System.out.println("missing data: " + missingData);
                List<Integer> dataArriveTime = new ArrayList<>();
                for (Node missingNode : missingData) {
                    Processor dataStorage = Task.getDataStorage(missingNode);
                    List<Processor> route = routes.get(new Tuple<>(dataStorage.id, chosenProcessor.id));
                    int finishTime = Task.getNodeFinishTime(missingNode) + 1;
                    for (int i = 0; i < route.size() - 1; i++) {
                        int packetSize = missingNode.getLinkWeight(node.id);
                        finishTime = route.get(i).sendToProcessor(missingNode, finishTime, route.get(i + 1), packetSize);
//                        System.out.println(String.format("arrive time of %s to %s is %d", missingNode.toString(), chosenProcessor.toString(), finishTime));
                    }
//                    int arriveTime = dataStorage.sendToProcessor(missingNode, finishTime + 1, chosenProcessor);
//                    System.out.println(String.format("arrive time of %s to %s is %d", missingNode.toString(),chosenProcessor.toString(), arriveTime));
                    dataArriveTime.add(finishTime);
                }
                int time = dataArriveTime.stream().mapToInt(i -> i).max().getAsInt();
//                System.out.println(String.format("node %s finish time is %d", node.toString(), time));
                chosenProcessor.scheduleAt(time, node);
            }


        }
        //  convert to JGantt form

    }

    private int getNodeFinishTimeOnProcessor(Processor processor, Node node, int t0) {
        List<Node> missingData = processor.getNodesWithMissingData(node);
        if (missingData.isEmpty()) {
            int finishTime = processor.getNextReleaseTime() + (int) Math.ceil((double) node.weight / processor.power);
//            System.out.println("missing data empty, t = "+finishTime);
            return finishTime;

        } else {
//            System.out.println("missing data: " + missingData);
            List<Integer> dataArriveTime = new ArrayList<>();
//            System.out.println("missing data: " + missingData);
            processors.values().forEach(Processor::backup);
            for (Node missingNode : missingData) {

                Processor dataStorage = Task.getDataStorage(missingNode);
                List<Processor> route = routes.get(new Tuple<>(dataStorage.id, processor.id));

                int finishTime = Task.getNodeFinishTime(missingNode) + 1;
                int packetSize = missingNode.getLinkWeight(node.id);
                for (int i = 0; i < route.size() - 1; i++) {
                    finishTime = route.get(i).sendToProcessor(missingNode, finishTime, route.get(i + 1), packetSize);
//                    System.out.println(String.format("arrive time of %s to %s is %d", missingNode, processor, finishTime));
                }
                dataArriveTime.add(finishTime);

            }
            processors.values().forEach(Processor::restore);
            int time = dataArriveTime.stream().mapToInt(i -> i).max().getAsInt();
//            System.out.println(String.format("node %s on proc %s start time is %d", node, processor, time));
            return time + (int) Math.ceil((double) node.weight/ processor.power);
        }
    }


    private Node chooseNextNode(List<Node> openNodes) {
        return openNodes.stream().min((n1, n2) -> Integer.compare(n1.getMinStartTime(), n2.getMinStartTime())).get();
    }

    private void processNode(Node node) {
        openNodes.remove(node);
        closedNodes.add(node);
        node.links.stream()   // add child to open nodes if all parents are closed
                .filter(link -> closedNodes.containsAll(link.parents))
                .forEach(openNodes::add);
    }

    private List<Processor> sortProcessors(Collection<Processor> processors) {
        return processors.stream().sorted(
                (n1, n2) -> -Integer.compare(n1.getNeighbours().size(), n2.getNeighbours().size())).collect(
                Collectors.toList());
    }

    public JGantt getJGantt() {
        List<List<Task>> tasks = new ArrayList<>();
        List<String> headers = new ArrayList<>();
        List<Processor> sortedProcessors = processors.values().stream().sorted(
                (p1, p2) -> Integer.compare(p1.id, p2.id)).collect(Collectors.toList());
        for (Processor sortedProcessor : sortedProcessors) {
            sortedProcessor.accumulateTasks(tasks, headers);
        }
        int length = tasks.stream().mapToInt(l -> l.isEmpty() ? 0 : l.get(l.size() - 1).endTime).max().getAsInt();
        //fill spaces with empty tasks
        for (List<Task> taskList : tasks) {
            if (!taskList.isEmpty()) {
                List<Task> emptyTasks = new ArrayList<>();
                if (taskList.get(0).startTime > 0) {
                    emptyTasks.add(new Task(0, taskList.get(0).startTime));
                }
                if (taskList.size() > 1) {
                    for (int i = 1; i < taskList.size(); i++) {
                        Task task = taskList.get(i);
                        Task previousTask = taskList.get(i - 1);
                        if (task.startTime > previousTask.endTime + 1) {
                            int startTime = previousTask.endTime + 1;
                            emptyTasks.add(new Task(startTime, task.startTime - startTime));
                        }
                    }
                }
                taskList.addAll(emptyTasks);
                taskList.sort((t1, t2) -> Integer.compare(t1.startTime, t2.startTime));
            } else {
                taskList.add(new Task(0, length + 1));
            }
        }
//        System.out.println("before:" + tasks);
        return new JGantt(length, tasks, headers);
    }
}
