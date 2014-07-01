package com.kpi.planner.entity.js.graph;

/**
 * Created by Artem
 * Date: 4/5/2014 4:08 PM.
 */
public class JConnection {

    public final JNode source;
    public final JNode target;
    public final boolean left;
    public final boolean right;
    public final int linkWeight;

    public JConnection(JNode source, JNode target, boolean left, boolean right, int linkWeight) {
        this.source = source;
        this.target = target;
        this.left = left;
        this.right = right;
        this.linkWeight = linkWeight;
    }

    @Override
    public String toString() {
        return String.format("[%d -> %d, left: %s]", source.id, target.id, left);
    }
}
