package com.kpi.planner.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artem
 * Date: 4/5/2014 6:28 PM.
 */
public class CyclesFinder {

    private int time;
    private List<Integer>[] graph;
    private int[] lowlink;
    private boolean[] used;
    private List<Integer> stack;
    private List<List<Integer>> components;

    public List<List<Integer>> scc(List<Integer>[] graph) {
        int n = graph.length;
        this.graph = graph;
        lowlink = new int[n];
        used = new boolean[n];
        stack = new ArrayList<>();
        components = new ArrayList<>();
        for (int u = 0; u < n; u++)
            if (!used[u])
                dfs(u);

        return components;
    }

    void dfs(int u) {
        lowlink[u] = time++;
        used[u] = true;
        stack.add(u);
        boolean isComponentRoot = true;

        for (int v : graph[u]) {
            if (!used[v])
                dfs(v);
            if (lowlink[u] > lowlink[v]) {
                lowlink[u] = lowlink[v];
                isComponentRoot = false;
            }
        }

        if (isComponentRoot) {
            List<Integer> component = new ArrayList<>();
            while (true) {
                int k = stack.remove(stack.size() - 1);
                component.add(k);
                lowlink[k] = Integer.MAX_VALUE;
                if (k == u)
                    break;
            }
            components.add(component);
        }
    }

}
