package com.wentong.mytest;

public class Grade {
    private String name;
    private Class[] classes = new Class[0];
    private final Object servicesLock = new Object();

    public void addClass(Class c) {
        synchronized (servicesLock) {
            Class[] results = new Class[classes.length + 1];
            System.arraycopy(classes, 0, results, 0, classes.length);
            results[classes.length] = c;
            classes = results;
        }
    }

    public Class[] getClasses() {
        return classes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
