package com.kpi.planner.utils;

/**
 * Created by Artem
 * Date: 2/5/14 7:41 PM.
 */
public final class Tuple<K, V> {

    public final K _1;
    public final V _2;

    public Tuple(K first, V second) {
        this._1 = first;
        this._2 = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tuple tuple = (Tuple) o;

        if (_1 != null ? !_1.equals(tuple._1) : tuple._1 != null) return false;
        if (_2 != null ? !_2.equals(tuple._2) : tuple._2 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = _1 != null ? _1.hashCode() : 0;
        result = 31 * result + (_2 != null ? _2.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "(" + _1 +", "+ _2 +")";
    }
}
