package org.fornever.java;

import org.fornever.java.exceptions.MethodExecutionException;
import org.fornever.java.test.Human;
import org.fornever.java.test.IHuman;
import org.fornever.java.test.IPublicPeople;
import org.fornever.java.test.People;
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
    void testToMap() {
        var people = new People("test7");
        Assertions.assertEquals("test7", people.getName());
        var peopleMap = Broken.defaultBroken.toMap(people);
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