package com.kpi.planner.entity.response;

/**
 * Created by Artem
 * Date: 4/12/2014 1:36 PM.
 */
public class Response {

    private final Status status;
    private final String description;

    public Response(Status status, String description) {
        this.status = status;
        this.description = description;
    }

    public Response(Status status) {
        this.status = status;
        this.description = null;
    }

    public enum Status {
        OK,
        ERROR,
        INTERNAL_ERROR;
    }
}
