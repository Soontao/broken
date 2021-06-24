package org.fornever.java;

import org.fornever.java.exceptions.MethodExecutionException;
import org.fornever.java.exceptions.NotImplementException;
import org.fornever.java.test.*;
import org.fornever.java.test.school.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


class BrokenTest {

    Broken broken = new Broken();

    @Test
    void testCall() {
        var people = new People("test1");
        Assertions.assertEquals("test1", people.getName());
        Object rt = broken.call(people, "setName", "test2");
        Assertions.assertNull(rt);
        Assertions.assertEquals("test2", people.getName());
    }

    @Test
    void testCallWithOverride() {
        var people = new AnotherPeople("sun");
        Assertions.assertEquals("another sun", people.getName());
        Assertions.assertEquals("another sun", broken.call(people, "getName"));
    }

    @Test
    void testCall_WhenThrowException() {
        var human = new Human();
        Assertions.assertThrows(MethodExecutionException.class, () -> {
            broken.call(human, "someOp");
        });
    }

    @Test
    void testProxy_WhenThrowException() {
        var human = broken.proxy(IHuman.class, new Human());

        Assertions.assertThrows(MethodExecutionException.class, () -> {
            broken.call(human, "someOp");
        });
    }

    @Test
    void testSetValue() {
        var people = new People("test3");
        Assertions.assertEquals("test3", people.getName());
        broken.setValue(people, "name", "test4");
        Assertions.assertEquals("test4", people.getName());
        broken.setValue(people, "name", null);
        Assertions.assertNull(people.getName());

    }

    @Test
    void testProxy() {
        var people = new People("test5");
        Assertions.assertEquals("test5", people.getName());
        var publicPeople = broken.proxy(IPublicPeople.class, people);
        publicPeople.setName("test6");
        Assertions.assertEquals("test6", people.getName());
    }

    @Test
    void testProxy_WhenNotImplThrow() {
        var human = new Human();
        Assertions.assertThrows(NotImplementException.class, () -> {
            Broken.getDefaultBroken().proxy(ISuperHuman.class, human);
        }, "method 'org.fornever.java.test.ISuperHuman'.'superOp'() is not implemented by type 'org.fornever.java.test.Human'");
        Assertions.assertThrows(NotImplementException.class, () -> {
            Broken.getDefaultBroken().proxy(ISuper2Human.class, human);
        }, "method 'org.fornever.java.test.ISuper2Human'.'s2People'(java.lang.String, int, org.fornever.java.test.People) is not implemented by type 'org.fornever.java.test.Human'");
    }

    @Test
    void testProxyClass() {

        var school = new School("s1", 100);
        var student = new Student("s1", 18, school);
        var teachClass = new TeachClass(1, "c1", school);
        teachClass.addStudent(student);
        var s2 = broken.proxy(AnotherSchool.class, school);

        Assertions.assertEquals(school.getName(), s2.getName());
        s2.setName("s2");
        Assertions.assertEquals("s2", student.getSchool().getName());

    }

    @Test
    void testToMap() {
        var people = new People("test7");
        Assertions.assertEquals("test7", people.getName());
        var peopleMap = Broken.getDefaultBroken().toMap(people);
        peopleMap.put("name", "test8");
        Assertions.assertEquals("test8", people.getName());
        Assertions.assertEquals("test8", peopleMap.get("name"));
        Assertions.assertEquals(1, peopleMap.size());
        Assertions.assertEquals(Set.of("name"), peopleMap.keySet());
        Assertions.assertTrue(peopleMap.containsKey("name"));
        Assertions.assertFalse(peopleMap.containsKey("name1"));
        Assertions.assertTrue(peopleMap.containsValue("test8"));
        Assertions.assertFalse(peopleMap.containsValue("test7"));
        peopleMap.remove("name");
        Assertions.assertNull(people.getName());
        Map<String, Object> t1 = new HashMap<>();
        t1.put("name", "test9");
        t1.put("name2", "not existed");
        peopleMap.putAll(t1);

        Assertions.assertEquals("test9", people.getName());
        Assertions.assertNull(peopleMap.get("name2"));
        peopleMap.clear();
        Assertions.assertNull(people.getName());

    }

}