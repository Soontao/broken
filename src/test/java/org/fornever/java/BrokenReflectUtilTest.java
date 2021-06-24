package org.fornever.java;

import org.fornever.java.test.People;
import org.fornever.java.test.school.AnotherSchool;
import org.fornever.java.test.school.AnotherTeachClass;
import org.fornever.java.test.school.School;
import org.fornever.java.test.school.TeachClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BrokenReflectUtilTest {

    BrokenReflectUtil util = new BrokenReflectUtil();

    @Test
    void testIsSoftAssignable() {

        Assertions.assertTrue(util.isSoftAssignable(School.class, AnotherSchool.class));
        Assertions.assertTrue(util.isSoftAssignable(TeachClass.class, AnotherTeachClass.class));

        Assertions.assertFalse(util.isSoftAssignable(School.class, AnotherTeachClass.class));
        Assertions.assertFalse(util.isSoftAssignable(TeachClass.class, People.class));

    }
}