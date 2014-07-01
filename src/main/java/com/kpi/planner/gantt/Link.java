package com.kpi.planner.gantt;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artem
 * Date: 5/29/2014 8:53 PM.
 */
public class Link {

    private List<Task> send = new ArrayList<>();
    private List<Task> receive = new ArrayList<>();

    private List<Task> sendSnapshot = new ArrayList<>();
    private List<Task> receiveSnapshot = new ArrayList<>();

    public void backup(){
        sendSnapshot.clear();
        receiveSnapshot.clear();
        sendSnapshot.addAll(send);
        receiveSnapshot.addAll(receive);
    }

    public void restore(){
        send.clear();
        receive.clear();
        send.addAll(sendSnapshot);
        receive.addAll(receiveSnapshot);
    }

    public int getNextSendingFrameAfterTime(int startTime, int length, int destination) {
        for (int i = startTime; i < startTime + 100; i++) {
            if (sendQueueIsFreeAtPeriod(i, length, destination)) return i;
        }
        return -1;
    }

    public boolean sendQueueIsFreeAtPeriod(int time, int duration, int dst) {
        for (int t = time; t < time + duration; t++) {
            for (Task task : send) {
                if (task.startTime <= t && task.endTime >= t) return false;
            }
            for (Task task : receive) {
                ReceiveTask receiveTask = (ReceiveTask) task;
                if ((receiveTask.startTime <= t && receiveTask.endTime >= t)&& receiveTask.from != dst) return false;
            }
        }
        return true;
    }

    public boolean receiveIsFreeAtPeriod(int time, int duration, int src) {
        for (int t = time; t < time + duration; t++) {
            for (Task task : receive) {
                if (task.startTime <= t && task.endTime >= t) return false;
            }
            for (Task task : send) {
                SendTask sendTask = (SendTask) task;
                if ((sendTask.startTime <= t && sendTask.endTime >= t)&& sendTask.to != src) return false;
            }
        }
        return true;
    }

    public void scheduleSendTask(Task task) {
        send.add(task);
        send.sort((t1, t2) -> Integer.compare(t1.startTime, t2.startTime));
    }

    public void scheduleReceiveTask(Task task) {
        receive.add(task);
        receive.sort((t1, t2) -> Integer.compare(t1.startTime, t2.startTime));
    }


    public List<Task> getSend() {
        return send;
    }

    public List<Task> getReceive() {
        return receive;
    }
}
