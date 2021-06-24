package org.fornever.java.test.school;

public class AnotherStudent {

    private String name;
    private Integer age;
    private School school;

    public AnotherStudent(String name, Integer age, School school) {
        this.name = name;
        this.age = age;
        this.school = school;
    }

    public AnotherStudent() {

    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
