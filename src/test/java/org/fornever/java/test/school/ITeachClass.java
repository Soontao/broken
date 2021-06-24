package org.fornever.java.test.school;

import java.util.List;

public interface ITeachClass {

    Integer getGrade();

    void setGrade(Integer grade);

    String getName();

    void setName(String name);

    School getSchool();

    void setSchool(School school);

    List<IStudent> getStudents();

    void addStudent(IStudent student);

}
