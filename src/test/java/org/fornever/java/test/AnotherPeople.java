package org.fornever.java.test;

public class AnotherPeople extends People {

    public AnotherPeople(String name) {
        super(name);
    }

    @Override
    public String getName() {
        return "another " + super.getName();
    }

}
