package org.fornever.java.test.school;

public class AnotherSchool {

    private String name;

    public AnotherSchool(String name, Integer capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public AnotherSchool() {

    }

    private Integer capacity = 100;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
