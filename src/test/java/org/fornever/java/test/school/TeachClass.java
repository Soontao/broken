package org.fornever.java.test.school;

import java.util.LinkedList;
import java.util.List;

public class TeachClass {

    private Integer grade = 1;
    private String name;
    private School school;
    private List<Student> students = new LinkedList<>();

    public TeachClass(Integer grade, String name, School school) {
        this.grade = grade;
        this.name = name;
        this.school = school;
    }

    public TeachClass() {

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

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

}
