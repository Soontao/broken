package org.fornever.java.test.school;

import java.util.LinkedList;
import java.util.List;

public class AnotherTeachClass {

    private Integer grade = 1;
    private String name;
    private AnotherSchool school;
    private List<AnotherStudent> students = new LinkedList<>();

    public AnotherTeachClass(Integer grade, String name, AnotherSchool school) {
        this.grade = grade;
        this.name = name;
        this.school = school;
    }

    public AnotherTeachClass() {

    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AnotherSchool getSchool() {
        return school;
    }

    public void setSchool(AnotherSchool school) {
        this.school = school;
    }

    public List<AnotherStudent> getStudents() {
        return students;
    }

    public void addStudent(AnotherStudent student) {
        this.students.add(student);
    }

}
