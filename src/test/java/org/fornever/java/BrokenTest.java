package org.fornever.java;

import org.fornever.java.test.IPublicPeople;
import org.fornever.java.test.People;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


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
    void testToMap() {
        var people = new People("test7");
        Assertions.assertEquals("test7", people.getName());
        var peopleMap = Broken.defaultBroken.toMap(people);
        peopleMap.put("name", "test8");
        Assertions.assertEquals("test8", people.getName());
    }

}