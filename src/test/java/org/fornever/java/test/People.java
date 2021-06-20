package org.fornever.java.test;

public class People {

    private String name;

    private void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public People(String name) {
        this.setName(name);
    }
}
