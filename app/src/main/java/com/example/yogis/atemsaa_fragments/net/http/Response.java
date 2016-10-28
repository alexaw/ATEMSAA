package com.example.yogis.atemsaa_fragments.net.http;

/**
 * Created by yogis on 10/10/2016.
 */
public class Response {

    public static final int NO_ERROR = -1;
    public static final int ERROR_NO_INTERNET = 0;
    public static final int ERROR_TIME_OUT = 1;
    public static final int ERROR_UNKOWN = 2;

    public String msg;
    public int error;
    public int code;
    public int request;



}
