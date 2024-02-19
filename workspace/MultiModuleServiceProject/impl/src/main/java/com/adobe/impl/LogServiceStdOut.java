package com.adobe.impl;

import com.adobe.api.LogService;

public class LogServiceStdOut implements LogService {

    @Override
    public void log(String s) {
        System.out.println("STDOUT: " + s);
    }
}
